import java.io.IOException;
import java.util.ArrayList;

public class Webs {

    private String title;
    private String url;
    private double score;
    private WordCounter counter;

    public Webs(String title, String url) {
        this.title = title;
        this.url = url;
        this.score = 0;
        this.counter = new WordCounter(url);
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        try {
            return url;
        } catch (Exception e) {
            return "about:blank";
        }
    }

    // ! obsolete method
    public void addScore(int i) {
        this.score += i;
    }

    public double getScore() {
        return score;
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