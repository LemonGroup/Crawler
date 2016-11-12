package com.slack.geekbrainswork.crawler.util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nikolay on 12.11.2016.
 */

public class ParserSiteMap {

    // список ссылок из sitemap
    ArrayList<String> links;

    // метод получает список ссылок из файла sitemap.xml и передает в переменную links
    public void getLinksFromFile (String fileName) throws IOException {
        File sitemap = new File(fileName);
        links = new ArrayList<>();
        Document doc;
        doc = Jsoup.parse(sitemap, "UTF-8");
        String text = doc.html().toString();
        Elements elements = doc.select("loc");
        String[] arr = elements.text().split(" ");

        for (String s : arr){
            links.add(s);
        }

    }

    // метод получает список ссылок из url дареса sitemap.xml и передает в переменную links
    public void getLinksFromUrl (String url) throws IOException {

        links = new ArrayList<>();
        Document doc;
        doc = Jsoup.connect(url).userAgent("Mozilla").get();
        String text = doc.html().toString();
        Elements elements = doc.select("loc");
        String[] arr = elements.text().split(" ");

        for (String s : arr){
            links.add(s);
        }

    }

    public ArrayList<String> getLinks() {
        return links;
    }
}

