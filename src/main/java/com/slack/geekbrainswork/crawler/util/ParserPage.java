package com.slack.geekbrainswork.crawler.util;

import com.slack.geekbrainswork.crawler.model.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Nikolay on 09.11.2016.
 */

public class ParserPage {
    private static final int JSOUP_READ_TIMEOUT = 0; //значение задаётся в МИЛИСЕКУНДАХ, 0 - значит нет таймаута
    private static final String JSOUP_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0";
    private static final boolean JSOUP_FOLLOW_REDIRECTIONS = true;

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
        // получаем содержиое страницы с адресом url и передаем переменной doc
        Document doc = Jsoup.connect(page.getUrl()).userAgent(JSOUP_USER_AGENT).followRedirects(JSOUP_FOLLOW_REDIRECTIONS).timeout(JSOUP_READ_TIMEOUT).get();
        // из переменной doc из влекаем текст и возвращаем его как результат
        return doc.text();
    }
}