package com.slack.geekbrainswork.crawler.util;
import com.slack.geekbrainswork.crawler.model.Page;
import com.slack.geekbrainswork.crawler.model.Site;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Nikolay on 12.11.2016.
 */

public class ParserSiteMap {
    private static final int     JSOUP_READ_TIMEOUT = 0; //значение задаётся в МИЛИСЕКУНДАХ, 0 - значит нет таймаута
    private static final String  JSOUP_USER_AGENT   = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0";
    private static final boolean JSOUP_FOLLOW_REDIRECTIONS = true;

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
        doc = Jsoup.connect(url).userAgent(JSOUP_USER_AGENT).followRedirects(JSOUP_FOLLOW_REDIRECTIONS).timeout(JSOUP_READ_TIMEOUT).get();

        Elements elements = doc.select("loc");

        for ( Element element: elements){
            links.add(element.text());
        }

        return links;
    }

    /**
     * метод получает список ссылок из url sitemap.xml и возвращает список страниц с этими ссылками в качестве результата
     * @param url ссылка на sitemap.xml
     * @param site сайт, которому принадлежит этот sitemap
     * @return список страниц со ссылками из sitemap'а
     * @throws IOException
     */
    public static void getPagesFromUrl (Map<String, Page> pages, String url, Site site) throws IOException {
        Calendar curDate = Calendar.getInstance(); //текущая дата, которая будет использоваться как дата обнаружения страницы

        Document doc = Jsoup.connect(url).userAgent(JSOUP_USER_AGENT).followRedirects(JSOUP_FOLLOW_REDIRECTIONS).timeout(JSOUP_READ_TIMEOUT).get(); //получаем содержимое sitemap.xml в виде Document Object Model (DOM)

        Elements sitemapLocs = doc.select("sitemap").select("loc"); //получаем список loc для всех элементов с тегом sitemap
        Elements urlLocs     = doc.select("url").select("loc"); //получаем список loc для всех элементов с тегом url

        //для каждой ссылки на sitemap
        for (Element sitemapLoc: sitemapLocs){
            String sitemapUrl = sitemapLoc.text();
            //проверяем, что ссылка не является ссылкой на архив
            if (!sitemapUrl.matches(".*sitemap[0-9]*\\.xml\\..*")) {
                //если не является, то рекурсивно вызываем для этой ссылки метод getPagesFromUrl
                getPagesFromUrl(pages, sitemapLoc.text(), site);
            }
            else {
                //здесь будет обработка архивов с sitemap'ами
            }
        }

        //для каждой ссылки на страницу с контентом
        for (Element urlLoc : urlLocs) {
            String pageUrl = urlLoc.text(); //ссылка на страницу

            /**
             * Если страницы с такой ссылкой ещё нет в карте, то метод putIfAbsent положит туда новую страницу
             * и в качестве результата вернёт null (!!!). Если страница с такой ссылкой уже есть, то метод putIfAbsent
             * в качестве результата вернёт существующую страницу.
             * Мы используем именно этот метод потому что он потенциально потокобезопасен. Т.е. если мы в переменной pages
             * будем использовать ConcurrentHashMap вместо простого HashMap, то следующий код менять не придётся.
             */
            //проверяем, что ссылка - не на xml
            if (!pageUrl.matches(".*\\.xml$")) {
                pages.putIfAbsent(pageUrl, new Page(pageUrl, site, curDate, null));
            }
        }
    }

    /**
     * Метод получает список ссылок на sitemap.xml из robots.txt .<br>
     * Возвращает пустую коллекцию, если ссылок нет
     * @param robotsUrl ссылка на robots.txt
     * @return ссылки на sitemap.xml
     * @throws IOException
     */
    public static @Nonnull List<String> getSitemapUrlFromRobots(@Nonnull String robotsUrl) throws IOException {
        //получаем содержимое по ссылке robotsUrl и разбиваем его на массив по строкам
        return Arrays.stream(Jsoup.connect(robotsUrl).userAgent(JSOUP_USER_AGENT).followRedirects(JSOUP_FOLLOW_REDIRECTIONS).timeout(JSOUP_READ_TIMEOUT).execute().body().split("\n"))
                .filter(str -> str.matches("^Sitemap:.*")) //из полученного массива получчаем все строки, начинающиеся на Sitemap:
                .map(str -> str.replaceAll("^Sitemap:[ ]*","")) //во всех полученных строках заменяем "Sitemap: " на "", т.е. удаляем эту часть строки
                .distinct() //оставляем только уникальные ссылки
                .collect(Collectors.toList());
    }

    /**
     * Получает список страниц для сайта
     * @param site сайт
     * @return список страниц
     * @throws IOException
     */
    public static @Nonnull List<Page> getPagesForSite(@Nullable Site site) throws IOException {
        List<Page> pages = new ArrayList<>();

        /**
         * На всякий случай проверяем, что site не null, и что в свойстве name - тоже не null.
         * Если null, то возвращаем пустой список страниц.
         */
        if (site == null || site.getName() == null) return pages;

        /**
         * Поскольку нет соглашения о том, в каком виде мы храним ссылку на сайт в таблице sites,
         * здесь нам придётся добавить немного магии :)
         */
        String robotsUrl = site.getName() + "/robots.txt"; //ссылка на robots.txt для сайта
        List<String> sitemapUrls; //список ссылок на sitemap'ы
        //в случае, если ссылка на сайт не начинается с http
        if (!robotsUrl.toLowerCase().startsWith("http")) {
            //то мы не знаем, какой протокол использовать для получения robots.txt с сайта - HTTP или HTTPS
            //сначала пробуем http
            sitemapUrls = getSitemapUrlFromRobots("http://" + robotsUrl);
            //если список sitemapUrls после этого всё ещё пустой
            if (sitemapUrls.isEmpty()) {
                //то пробуем по https
                sitemapUrls = getSitemapUrlFromRobots("https://" + robotsUrl);
            }
        }
        else {
            //если ссылка на сайт уже содержит http, то пытаемся получить список ссылок на sitemap'ы для неё
            sitemapUrls = getSitemapUrlFromRobots(robotsUrl);
        }

        /**
         * Cтроим карту ссылка-страница из тех страниц, что уже добавлены для сайта.
         * Это понадобится нам, чтобы в дальнейшем исключить повторное добавление уже имеющихся страниц.
         */
        Map<String, Page> urlToPageMap = site.getPages().stream().collect(Collectors.toMap(Page::getUrl, page -> page));

        //перебираем ссылки на sitemap'ы из robots.txt
        for (String sitemapUrl : sitemapUrls) {
            getPagesFromUrl(urlToPageMap, sitemapUrl, site);
        }

        //добавляем все полученные страницы из карты urlToPageMap в список pages
        pages.addAll(urlToPageMap.values());

        return pages;
    }
}

