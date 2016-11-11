import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;



/**
 * Created by Nikolay on 09.11.2016.
 */

public class Downloader {

    String url;

/**
    public static void main(String[] args) throws IOException {
        String url = "https://lenta.ru/lib/14160711/";

        Downloader downloader = new Downloader(url);

        String html = downloader.download();
        System.out.println(html);
        ArrayList<String> keywords = new ArrayList<>();
        keywords.add("Путин");

        RatingCount ratingCount = new RatingCount(keywords, html);
        ratingCount.calculate();
        System.out.println(ratingCount.getCount());

    }

*/

    public Downloader(String url) throws MalformedURLException {
            this.url = url;
    }

    public String download () throws IOException {

        Document doc;
        doc = Jsoup.connect(url).userAgent("Mozilla").get();
        String text = doc.text().toString();

        return text;
        }
    }