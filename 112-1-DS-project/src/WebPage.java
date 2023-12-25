import java.io.IOException;
import java.util.ArrayList;

public class WebPage {
    public String url;
    public String name;
    public WordCounter counter;
    public double score;

    public WebPage(String url, String name) {
        this.url = url;
        this.name = name;
        this.counter = new WordCounter(url);
    }

    public void setScore(ArrayList<Keyword> keywords) throws IOException {
        double sum = 0;
        // YOUR TURN
        // 1. calculate the score of this webPage
        for (int i = 0; i < keywords.size(); i++) {
            sum += keywords.get(i).weight * this.counter.countKeyword(keywords.get(i));
        }
        this.score = sum;

    }
}