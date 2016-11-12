package com.slack.geekbrainswork.crawler.util;

import java.util.ArrayList;

/**
 * Created by Nikolay on 09.11.2016.
 */
public class Rating {
    private ArrayList<String> keywords;
    private String text;
    private int count;


    public Rating(ArrayList<String> keywords, String text) {
        this.keywords = keywords;
        this.text = text;
    }

    public int calculate(){
        String[] words = text.split("\\s");

        for (String k : keywords){
            for (String w : words){
                if (w.toLowerCase().contains(k.toLowerCase())) {
                    count++;
                }
            }
        }

        return count;
    }

    public int getCount() {
        return count;
    }
}
