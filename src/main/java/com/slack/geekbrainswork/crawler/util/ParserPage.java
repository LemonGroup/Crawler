package com.slack.geekbrainswork.crawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;



/**
 * Created by Nikolay on 09.11.2016.
 */

public class ParserPage {

    String url;


    public ParserPage(String url) throws MalformedURLException {
            this.url = url;
    }

    public String getText () throws IOException {

        Document doc;
        doc = Jsoup.connect(url).userAgent("Mozilla").get();
        String text = doc.text().toString();

        return text;
        }
    }