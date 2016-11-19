package com.slack.geekbrainswork.crawler.util;
import com.slack.geekbrainswork.crawler.model.Keyword;
import com.slack.geekbrainswork.crawler.model.Page;
import com.slack.geekbrainswork.crawler.model.Person;
import com.slack.geekbrainswork.crawler.model.PersonPageRank;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Получает кол-во упоминаний личности на странице
     * @param person {@link Person} личность
     * @param page {@link Page} страница
     * @return {@link PersonPageRank}
     * @throws IOException
     */
    public static PersonPageRank getPersonPageRank(Person person, Page page) throws IOException {
        //создаём объект рейтинга и задаём в нём ссылки на страницу и личность
        PersonPageRank ppr = new PersonPageRank();
        ppr.setPerson(person);
        ppr.setPage(page);

        int pageMentionCounter = 0; //счётчик упоминаний
        String pageText = ParserPage.getTextFromPage(page).toLowerCase(); //получаем текст страницы в нижнем регистре (для удобства)
        //для каждого ключевого слова связанного с личностью
        for (Keyword k : person.getKeywords()) {
            /**
             * Создаём шаблон регулярного выражения для поиска упоминаний.
             * Шаблон означает: искать только слово целиком.
             * Ключевое слово в шаблон передаётся в нижнем регистре.
             */
            Pattern matchPattern = Pattern.compile("\\b(" + k.getName().toLowerCase() + ")\\b");
            // Создаём matcher, который будет выполнять поиск совпадений с шаблоном
            Matcher matcher = matchPattern.matcher(pageText);
            //до тех пор, пока в строке есть совпадения
            while (matcher.find()) {
                //увеличиваем значение счётчика
                pageMentionCounter++;
            }
        }
        //сохраняем полученный результат в объект рейтинга
        ppr.setRank(pageMentionCounter);

        //в объекте страницы обновляем дату последнего сканирования
        page.setLastScanDate(Calendar.getInstance());

        return ppr;
    }
}
