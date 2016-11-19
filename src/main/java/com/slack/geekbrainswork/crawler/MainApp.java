package com.slack.geekbrainswork.crawler;

import com.slack.geekbrainswork.crawler.model.*;
import com.slack.geekbrainswork.crawler.util.*;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by Andrey on 09.11.2016.
 */
public class MainApp {
    private static int THREADS = 20; //количество потоков. По-умолчанию: 20
    private static ForkJoinPool threadPool; //пул потоков

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
    public static void main(String[] args) {
        /**
         * В качестве аргумента приложению можно передать кол-во потоков
         */
        //проверяем, были ли переданы аргументы
        if (args.length > 0) {
            //если были, то
            try{
                //пытаемся распарсить из первого аргумента число
                int argThreads = Integer.parseInt(args[0]);
                //если полученное число больше 0
                if (argThreads > 0) {
                    //то используем его в качестве кол-ва потоков
                    THREADS = argThreads;
                }
                else {
                    //если меньше 0, то кидаем исключение
                    throw new NumberFormatException();
                }
            }
            catch(NumberFormatException e) {
                logger.error("Неверный формат аргумента! Должно быть положительное число число меньше " + Integer.MAX_VALUE);
            }
        }

        threadPool = new ForkJoinPool(THREADS); //создаём пул для потоков

        try{
            logger.info("Начинаем работу...");
            logger.info("Получаем список сайтов...");
            final List<Site> sites = DAOFactory.getDAO(Site.class).get(() -> null);
            logger.info("Список сайтов получен.");
            logger.info("Получаем список личностей...");
            final List<Person> persons = DAOFactory.getDAO(Person.class).get(() -> null);
            logger.info("Список личностей получен.");

            /**
             * добавляем в пул задачу. Благодаря особенностям работы Stream API в java 8 задача будет выполняться многопоточно,
             * а максимальным кол-вом потоков будет значение переменной THREADS
             */
            threadPool.submit(() -> {
                sites.parallelStream().forEach(site -> {
                    logger.info("Получаем страницы сайта: " + site.getName());
                    try {
                        final List<Page> pages = ParserSiteMap.getPagesForSite(site);
                        final List<PersonPageRank> ranks = new ArrayList<>();
                        logger.info("Ищем упоминания личностей на сайте: " + site.getName());
                        pages.parallelStream().forEach(page -> {
                            logger.trace("Ищем упоминания личностей на странице: " + page.getUrl());
                            try {
                                ranks.addAll(Rating.getPersonPageRank(persons, page));
                            } catch (IOException e) {
                                logger.error("Не удалось получить содержимое страницы: " + page.getUrl());
                                logger.debug("Не удалось получить содержимое страницы: " + page.getUrl(), e);
                            }
                        });
                        logger.info("Сохраняем в базу результаты для сайта: " + site.getName());
                        //сначала сохраняем в базу страницы
                        DAOFactory.getDAO(Page.class).saveOrUpdate(pages);
                        //потом сохраняем рейтинги
                        if (!ranks.isEmpty()) {
                            DAOFactory.getDAO(PersonPageRank.class).merge(ranks);
                        }
                    } catch (IOException e) {
                        logger.error("Не удалось получить страницы сайта: " + site.getName(),e);
                    }
                    logger.info("Работа с сайтом: " + site.getName() + " завершена.");
                });
            }).get();

            logger.info("Работа завершена.");
        } catch (HibernateException e) {
            logger.error("Ошибка работы с БД", e);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Непредвиденная ошибка в работе приложения", e);
        } finally {
            SFactory.get().getCurrentSession().close();
            SFactory.get().close();
        }
    }
}
