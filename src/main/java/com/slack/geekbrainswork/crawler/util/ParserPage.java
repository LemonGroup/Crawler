package com.slack.geekbrainswork.crawler.util;

import com.slack.geekbrainswork.crawler.model.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nikolay on 09.11.2016.
 */

public class ParserPage {
    private static final int JSOUP_READ_TIMEOUT = 0; //значение задаётся в МИЛИСЕКУНДАХ, 0 - значит нет таймаута
    private static final String JSOUP_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0";
    private static final boolean JSOUP_FOLLOW_REDIRECTIONS = true;
    private static final int RETRIES_AMOUNT = 10; //кол-во повторных попыток

    /**
     * метод получает текст страницы c адресом url и сохранеят в перемнную text объекта класса ParserPage
     * @param url адрес страницы
     * @return текст на странице
     * @throws IOException
     */
    public static String getTextFromPage (String url) throws IOException {

        Document doc;
        // получаем содержиое страницы с адресом url и передаем переменной doc
        doc = Jsoup.connect(url).userAgent(JSOUP_USER_AGENT).followRedirects(JSOUP_FOLLOW_REDIRECTIONS).timeout(JSOUP_READ_TIMEOUT).get();
        // из переменной doc из влекаем текст и возвращаем его как результат
        return doc.text().toString();

        }

    /**
     * Метод получает текст страницы и возвращает его в качестве результата
     * @param page страница
     * @return текст на странице
     * @throws IOException
     */
    public static String getTextFromPage (Page page) throws IOException {
        int retriesCounter = 0; //счётчик повторных попыток
        Document doc = null; //DOM
        IOException exception = null; //исключение, возникшее при получении содержимого страницы
        //пытаемся получить содержимое страницы до тех пор, пока не превысим счётчик повторных попыток или пока не удастся получить содержимое сайта
        while(retriesCounter < RETRIES_AMOUNT && doc == null) {
            try{
                // получаем содержиое страницы с адресом url и передаем переменной doc
                doc = Jsoup.connect(page.getUrl()).userAgent(JSOUP_USER_AGENT).followRedirects(JSOUP_FOLLOW_REDIRECTIONS).timeout(JSOUP_READ_TIMEOUT).get();
            }
            catch(IOException e) {
                //сохраняем текущее исключение в переменную exception для того, чтобы кинуть его в случае неудачи по окончании цикла
                exception = e;
            }
            try {
                //спим одну секунду перед следующей попыткой
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
            retriesCounter++;
        }
        if (doc == null) {
            throw exception;
        }
        else {
            // из переменной doc из влекаем текст и возвращаем его как результат
            return doc.text();
        }
    }


    /**
     * Метод получает ссылки страницы и возвращает их в качестве результата
     * @param page страница
     * @return сслыки на странице
     * @throws IOException
     */
        public static ArrayList<String> getLinksFromPage(Page page) throws IOException {
            ArrayList<String> links = null;
            int retriesCounter = 0; //счётчик повторных попыток
            Document doc = null; //DOM
            IOException exception = null; //исключение, возникшее при получении содержимого страницы
            //пытаемся получить содержимое страницы до тех пор, пока не превысим счётчик повторных попыток или пока не удастся получить содержимое сайта
            while (retriesCounter < RETRIES_AMOUNT && doc == null) {
                try {
                    doc = Jsoup.connect(page.getUrl()).userAgent(JSOUP_USER_AGENT).followRedirects(JSOUP_FOLLOW_REDIRECTIONS).timeout(JSOUP_READ_TIMEOUT).get();
                } catch (IOException e) {
                    //сохраняем текущее исключение в переменную exception для того, чтобы кинуть его в случае неудачи по окончании цикла
                    exception = e;
                }
                try {
                    //спим одну секунду перед следующей попыткой
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
                retriesCounter++;
            }
            if (doc == null) {
                throw exception;
            } else {

                Elements elements = doc.select("a");
                // из переменной doc извлекаем ссылки и возвращаем их как результат
                for (Element element : elements) {
                    String link = element.attr("href");
                    links.add(link);
                }
                return links;

            }
        }

    /**
     * Метод получает получает robots.txt и возвращает список исключений в качестве результата
     * @param site сайт
     * @return список исключений
     * @throws IOException
     */

    public static ArrayList<String> getRobotsTxtFromSite(Page page) throws IOException {
        return null;
    }

}