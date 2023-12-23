import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please input the car you want to search.");
        String car = sc.nextLine();
        System.out.println("Please input the type you want to search. 1 for specs, 2 for the cost, 3 for history");
        int type = sc.nextInt();

        HTMLHandler html = new HTMLHandler(car);

        Keyword engine = new Keyword("engine", 5);
        Keyword power = new Keyword("power", 5);
        Keyword torque = new Keyword("torque", 5);
        Keyword transmission = new Keyword("transmission", 5);

        Keyword productionBegan = new Keyword("production began", 5);
        Keyword timeline = new Keyword("timeline", 5);

        Keyword $ = new Keyword("$", 5);
        Keyword usd = new Keyword("usd", 5);
        Keyword dollar = new Keyword("dollar", 5);
        Keyword eur = new Keyword("eur", 5);

        // Keywords trying to avoid due to unrealated content
        Keyword attack = new Keyword("attack", -1000);
        Keyword terroist = new Keyword("terrorist", -1000);
        Keyword government = new Keyword("government", -1000);
        Keyword national = new Keyword("national", -1000);

        try {
            html.query();
        } catch (IOException e) {

        }

        switch (type) {
            case (1):
                for (Webs i : html.webs) {
                    WordCounter count = new WordCounter(i.getUrl());
                    count.countKeyword(engine);
                    i.addScore(count.countKeyword(engine));
                    i.addScore(count.countKeyword(power));
                    i.addScore(count.countKeyword(torque));
                    i.addScore(count.countKeyword(transmission));
                }
                break;
            case (3):
                for (Webs i : html.webs) {
                    WordCounter count = new WordCounter(i.getUrl());
                    i.addScore(count.countKeyword(productionBegan));
                    i.addScore(count.countKeyword(timeline));
                    i.addScore(count.countKeyword(terroist));
                    i.addScore(count.countKeyword(attack));
                    i.addScore(count.countKeyword(national));
                    i.addScore(count.countKeyword(government));
                }
                break;
            case (2):
                for (Webs i : html.webs) {
                    WordCounter count = new WordCounter(i.getUrl());
                    count.countKeyword($);
                    i.addScore(count.countKeyword($));
                    // count.countKeyword(usd);
                    // count.countKeyword(dollar);
                    // count.countKeyword(eur);
                }

        }
        html.sortWebs(html.webs);
        for (Webs w : html.sortedWebs) {
            System.out.printf("Title:  %s,URL:  %s,Score: %d", w.getTitle(), w.getUrl(), w.getScore());
        }
        sc.close();

    }
}