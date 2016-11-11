import java.util.ArrayList;

/**
 * Created by Nikolay on 09.11.2016.
 */
public class RatingCount {
    private ArrayList<String> keywords;
    private String html;
    private int count;


    public RatingCount(ArrayList<String> keywords, String html) {
        this.keywords = keywords;
        this.html = html;
    }

    public int calculate(){
        String[] words = html.split("\\s");

        for (String k : keywords){
            for (String w : words){
                if (w.toLowerCase().contains(k.toLowerCase())) {
                    count++;
                }
            }
        }

        return count;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public String getHtml() {
        return html;
    }

    public int getCount() {
        return count;
    }
}
