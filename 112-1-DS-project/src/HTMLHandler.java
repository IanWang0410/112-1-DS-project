import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLHandler {

    public String searchKeyword;
    public String url;
    public String content;
    public ArrayList<Webs> webs = new ArrayList<>();
    public ArrayList<Webs> sortedWebs = new ArrayList<>();

    public HTMLHandler(String searchKeyword) {
        this.searchKeyword = searchKeyword;
        try {
            String encodeKeyword = java.net.URLEncoder.encode(searchKeyword, "utf-8");
            this.url = "https://www.google.com/search?q=" + encodeKeyword + "&gl=us&oe=utf8&num=20";

        } catch (Exception e) {

        }
    }

    public HTMLHandler() {
    }

    private String fetchContent() throws IOException {
        String retVal = "";

        URL u = new URL(url);
        URLConnection conn = u.openConnection();

        conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
        InputStream in = conn.getInputStream();

        InputStreamReader inReader = new InputStreamReader(in, "utf-8");
        BufferedReader bufReader = new BufferedReader(inReader);
        String line = null;

        while ((line = bufReader.readLine()) != null) {
            retVal += line;
        }
        return retVal;
    }

    public HashMap<String, String> query() throws IOException {
        if (content == null) {
            content = fetchContent();
        }

        HashMap<String, String> retVal = new HashMap<String, String>();

        Document doc = Jsoup.parse(content);

        Elements lis = doc.select("div");
        lis = lis.select(".kCrYT");

        for (Element li : lis) {
            try {
                String citeUrl = li.select("a").get(0).attr("href").replace("/url?q=", "");
                String title = li.select("a").get(0).select(".vvjwJb").text();
                citeUrl = citeUrl.substring(0, citeUrl.indexOf("&sa"));
                if (title.equals("")) {
                    continue;
                }

                System.out.println("Title: " + title + " , url: " + citeUrl);
                webs.add(new Webs(title, citeUrl)); // adding the URLs found to the list

                retVal.put(title, citeUrl);

            } catch (IndexOutOfBoundsException e) {

            }
        }
        return retVal;
    }

    public void sortWebs(ArrayList<Webs> wlst) {
        for (Webs w : wlst) {
            if (sortedWebs.isEmpty()) {
                sortedWebs.add(w);
            } else {
                for (Webs sw : sortedWebs) {
                    if (w.getScore() <= sw.getScore()) {
                        sortedWebs.add(sortedWebs.indexOf(sw), w);
                        break;
                    } else if (sortedWebs.size() == sortedWebs.indexOf(sw)) {
                        sortedWebs.add(sortedWebs.size() - 1, w);
                    } else {
                        continue;
                    }
                }
            }
        }
    }
}