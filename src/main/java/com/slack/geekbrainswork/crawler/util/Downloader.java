import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nikolay on 09.11.2016.
 */

public class Downloader {

    URL url;

/**

    public static void main(String[] args) throws IOException {
        Downloader downloader = new Downloader("https://lenta.ru/");
        String html = downloader.download();
        System.out.println(html);

    }

 */


    public Downloader(String address) throws MalformedURLException {
            this.url = new URL(address);
    }

    public String download () throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();

        while (bufferedReader.ready()) {
                    sb.append(bufferedReader.readLine()).append("\n");
                }
        bufferedReader.close();

        return sb.toString();
        }
    }

