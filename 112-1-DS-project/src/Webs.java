
public class Webs {

    private String title;
    private String url;
    private int score;

    public Webs(String title, String url) {
        this.title = title;
        this.url = url;
        this.score = 0;
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

    public void addScore(int i) {
        this.score += i;
    }

    public int getScore() {
        return score;
    }
}