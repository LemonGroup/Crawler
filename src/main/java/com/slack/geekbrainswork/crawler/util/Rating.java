package com.slack.geekbrainswork.crawler.util;
import java.util.ArrayList;

/**
 * Created by Nikolay on 09.11.2016.
 */

public class Rating {
    // список ключевых слов
    private ArrayList<String> keywords;
    // текст страницы
    private String text;
    // количество упоминаний
    private int count;


    // конструкто на входе принимеат список ключевых слов и текст страницы
    public Rating(ArrayList<String> keywords, String text) {
        this.keywords = keywords;
        this.text = text;
    }

    // метод считает количество всех слов из списка на данной странице и передает в переменню count
    public void calculate(){
        String[] words = text.split("[\\s|\\p{Punct}]");

        for (String w : words){
            System.out.println(w);
        }

        for (String k : keywords){
            for (String w : words){
                if (w.toLowerCase().equals(k.toLowerCase())) {
                    count++;
                }
            }
        }


    }

    public int getCount() {
        return count;
    }
}
