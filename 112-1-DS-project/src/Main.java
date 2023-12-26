import java.io.IOException;
import java.security.Key;
import java.util.Scanner;
import java.util.ArrayList;

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
        ArrayList<Keyword> specsKeywords = new ArrayList<>();
        specsKeywords.add(engine);
        specsKeywords.add(power);
        specsKeywords.add(torque);
        specsKeywords.add(transmission);

        Keyword productionBegan = new Keyword("production began", 5);
        Keyword timeline = new Keyword("timeline", 5);
        ArrayList<Keyword> historyKeywords = new ArrayList<>();
        historyKeywords.add(timeline);
        historyKeywords.add(productionBegan);

        Keyword $ = new Keyword("$", 5);
        Keyword usd = new Keyword("usd", 5);
        Keyword dollar = new Keyword("dollar", 5);
        Keyword eur = new Keyword("eur", 5);
        ArrayList<Keyword> priceKeywords = new ArrayList<>();
        priceKeywords.add(eur);
        priceKeywords.add($);
        priceKeywords.add(usd);
        priceKeywords.add(dollar);

        // Keywords trying to avoid due to unrelated content
        Keyword attack = new Keyword("attack", -1000);
        Keyword terroist = new Keyword("terrorist", -1000);
        Keyword government = new Keyword("government", -1000);
        Keyword national = new Keyword("national", -1000);
        Keyword software = new Keyword("software", -500);
        ArrayList<Keyword> unwantedKeywords = new ArrayList<>();
        unwantedKeywords.add(attack);
        unwantedKeywords.add(terroist);
        unwantedKeywords.add(government);
        unwantedKeywords.add(national);
        unwantedKeywords.add(software);
        unwantedKeywords.add(new Keyword("emergency number", -300));
        unwantedKeywords.add(new Keyword("pistol", -100));
        unwantedKeywords.add(new Keyword("medic", -100));
        unwantedKeywords.add(new Keyword("job", -100));
        unwantedKeywords.add(new Keyword("interview", -100));

        ArrayList<Keyword> generalKeywords = new ArrayList<>();
        generalKeywords.add(new Keyword("car", 10));
        generalKeywords.add(new Keyword("sedan", 10));
        generalKeywords.add(new Keyword("wagen", 10));
        generalKeywords.add(new Keyword("automobil", 10));
        generalKeywords.add(new Keyword("Toyota", 10));
        generalKeywords.add(new Keyword("Nissan", 10));
        generalKeywords.add(new Keyword("Honda", 10));
        generalKeywords.add(new Keyword("bmw", 10));
        generalKeywords.add(new Keyword("mercedes", 10));
        generalKeywords.add(new Keyword("porsche", 10));
        generalKeywords.add(new Keyword("volkswagen", 10));
        generalKeywords.add(new Keyword("renault", 10));

        try {
            html.query();
        } catch (IOException e) {

        }

        switch (type) {
            case (1):
                for (Webs i : html.webs) {

                    WordCounter count = new WordCounter(i.getUrl());

                    for (Keyword keywords : specsKeywords) {
                        i.addScore(count.countKeyword(keywords));
                    }
                    for (Keyword keyword : generalKeywords) {
                        i.addScore(count.countKeyword(keyword));
                    }
                    for (Keyword keyword : unwantedKeywords) {
                        i.addScore(count.countKeyword(keyword));
                    }

                }
                break;

            case (3):
                for (Webs i : html.webs) {
                    WordCounter count = new WordCounter(i.getUrl());
                    for (Keyword keywords : historyKeywords) {
                        i.addScore(count.countKeyword(keywords));
                    }
                    for (Keyword keyword : generalKeywords) {
                        i.addScore(count.countKeyword(keyword));
                    }
                    for (Keyword keyword : unwantedKeywords) {
                        i.addScore(count.countKeyword(keyword));
                    }
                }
                break;
            case (2):
                for (Webs i : html.webs) {
                    WordCounter count = new WordCounter(i.getUrl());
                    for (Keyword keywords : priceKeywords) {
                        i.addScore(count.countKeyword(keywords));
                    }
                    for (Keyword keyword : generalKeywords) {
                        i.addScore(count.countKeyword(keyword));
                    }
                    for (Keyword keyword : unwantedKeywords) {
                        i.addScore(count.countKeyword(keyword));
                    }
                }

        }
        // general keywords
        /*
         * for (Webs i : html.webs) {
         * WordCounter count = new WordCounter(i.getUrl());
         * for (Keyword keyword : generalKeywords) {
         * i.addScore(count.countKeyword(keyword));
         * }
         * }
         */

        // filter unwanted result
        /*
         * for (Webs i : html.webs) {
         * WordCounter count = new WordCounter(i.getUrl());
         * for (Keyword keyword : unwantedKeywords) {
         * i.addScore(count.countKeyword(keyword));
         * }
         * }
         */

        html.sortWebs();
        for (int i = 0; i <= html.sortedWebs.size() - 1; i++) {
            Webs w = html.sortedWebs.get(i);
            System.out.printf("Title:  %s , URL:  %s , Score: %.2f\n", w.getTitle(), w.getUrl(), w.getScore());
        }
        sc.close();

    }
}