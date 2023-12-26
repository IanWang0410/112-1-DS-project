import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

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
    public WebTree webTree;

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

    // using in-place quick sort
    public void sortWebs() {
        if (webs.size() == 0) {
            System.out.println("InvalidOperation");
        } else {
            mergeSort(webs);
        }
    }

    // ! Possible defect in inPlaceQuickSort, scores did not sort as expected
    // ! inPlaceQuickSort deprecated
    public void inPlaceQuickSort(int l, int r) {
        if (l >= r) {
            return;
        }

        int piv = r;
        int j = l;
        int k = r - 1;
        while (j <= k) {
            while (j <= r && webs.get(j).getScore() < webs.get(piv).getScore()) {
                j++;
            }
            while (k >= l && webs.get(k).getScore() > webs.get(piv).getScore()) {
                k--;
            }
            if (j <= k) {
                swap(j, k);
                j++;
                k--;
            }
        }
        swap(r, j);
        inPlaceQuickSort(l, j - 1);
        inPlaceQuickSort(j + 1, r);

        /*
         * for (int i = webs.size() - 1; i >= 0; i--) {
         * sortedWebs.add(webs.get(i));
         * }
         */

    }

    // ! swap deprecated

    private void swap(int aIndex, int bIndex) {
        Webs temp = webs.get(aIndex);
        webs.set(aIndex, webs.get(bIndex));
        webs.set(bIndex, temp);
    }

    public void mergeSort(ArrayList<Webs> arr) {
        if (arr.size() <= 1) {
            return;
        }

        int middle = arr.size() / 2;
        ArrayList<Webs> left = new ArrayList<>(arr.subList(0, middle));
        ArrayList<Webs> right = new ArrayList<>(arr.subList(middle, arr.size()));

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
        // Collections.reverse(arr); // to large to small
    }

    private void merge(ArrayList<Webs> arr, ArrayList<Webs> left, ArrayList<Webs> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getScore() <= right.get(j).getScore()) {
                arr.set(k++, left.get(i++));
            } else {
                arr.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            arr.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            arr.set(k++, right.get(j++));
        }
    }

}