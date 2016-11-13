package com.slack.geekbrainswork.crawler.util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by Nikolay on 12.11.2016.
 */

public class ParserSiteMap {

    // список ссылок из sitemap
    ArrayList<String> links;

    // метод получает список ссылок из файла sitemap.xml и передает в переменную links
    public void getLinksFromFile (String fileName) throws IOException, SAXException, ParserConfigurationException {
        File sitemap = new File(fileName);
        links = new ArrayList<>();
        Document doc;
        doc = Jsoup.parse(sitemap, "UTF-8");

        Elements elements = doc.select("loc");

        for ( Element element: elements){
            links.add(element.text());
        }

    }

    // метод получает список ссылок из url sitemap.xml и передает в переменную links
    public void getLinksFromUrl (String url) throws IOException {

        links = new ArrayList<>();
        Document doc;
        doc = Jsoup.connect(url).userAgent("Mozilla").get();

        Elements elements = doc.select("loc");

        for ( Element element: elements){
            links.add(element.text());
        }

    }


    public ArrayList<String> getLinks() {
        return links;
    }
}

