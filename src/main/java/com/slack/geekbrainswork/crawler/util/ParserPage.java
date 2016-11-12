package com.slack.geekbrainswork.crawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by Nikolay on 09.11.2016.
 */

public class ParserPage {
    // адрес страницы
    String url;
    // текст на странице
    String text;

    // конструктор на вход принимает параметр url - старницы
    public ParserPage(String url) throws MalformedURLException {
            this.url = url;
            this.text = "";
    }


    // метод получает текст страницы c адресом url и сохранеят в перемнную text объекта класса ParserPage
    public void getTextFromPage () throws IOException {

        Document doc;
        // получаем содержиое страницы с адресом url и передаем переменной doc
        doc = Jsoup.connect(url).userAgent("Mozilla").get();
        // из переменной doc из влекаем текст в переменную text
        text = doc.text().toString();

        }

    public String getText() {
        return text;
    }
}