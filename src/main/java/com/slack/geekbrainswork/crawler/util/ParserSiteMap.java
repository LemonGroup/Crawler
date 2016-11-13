package com.slack.geekbrainswork.crawler.util;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nikolay on 12.11.2016.
 */

public class ParserSiteMap {
    /**
     * метод получает список ссылок из файла sitemap.xml и возвращает его в качестве результата
     * @param fileName путь к файлу sitemap.xml
     * @return список ссылок из sitemap
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static List<String> getLinksFromFile (String fileName) throws IOException, SAXException, ParserConfigurationException {
        File sitemap = new File(fileName);
        List<String> links = new ArrayList<>();
        Document doc;
        doc = Jsoup.parse(sitemap, "UTF-8");

        Elements elements = doc.select("loc");

        for ( Element element: elements){
            links.add(element.text());
        }

        return links;
    }

    /**
     * метод получает список ссылок из url sitemap.xml и возвращает его в качестве результата
     * @param url адрес sitemap.xml
     * @return список ссылок из sitemap
     * @throws IOException
     */
    public static List<String> getLinksFromUrl (String url) throws IOException {

        List<String> links = new ArrayList<>();
        Document doc;
        doc = Jsoup.connect(url).userAgent("Mozilla").get();

        Elements elements = doc.select("loc");

        for ( Element element: elements){
            links.add(element.text());
        }

        return links;
    }
}

