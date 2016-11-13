package com.slack.geekbrainswork.crawler.util;
import com.slack.geekbrainswork.crawler.model.Keyword;

import java.util.List;

/**
 * Created by Nikolay on 09.11.2016.
 */

public class Rating {
    // список ключевых слов
    private List<Keyword> keywords;
    // текст страницы
    private String text;
    // количество упоминаний
    private int count;


    // конструкто на входе принимеат список ключевых слов и текст страницы
    public Rating(List<Keyword> keywords, String text) {
        this.keywords = keywords;
        this.text = text;
    }

    // метод считает количество всех слов из списка на данной странице и передает в переменню count
    public void calculate(){
        String[] words = text.split("[\\s|\\p{Punct}]");

        for (Keyword k : keywords){
            for (String w : words){
                if (w.toLowerCase().equals(k.getName().toLowerCase())) {
                    count++;
                }
            }
        }
    }

    public int getCount() {
        return count;
    }
}
