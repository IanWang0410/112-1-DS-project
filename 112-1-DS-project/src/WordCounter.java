import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

public class WordCounter {
    private String urlStr;
    private String content;

    public WordCounter(String urlStr) {
        this.urlStr = urlStr;
    }

    public WordCounter() {
    }

    private String fetchContent() throws IOException {
        String retVal = "";

        try {
            URL u = new URL(urlStr);
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
        } catch (IOException e) {
            // webpage server refuses authorize
            System.out.println("ERROR 403");
            return "";
        }
    }

    // ? Possible issue in the BoyerMoore method, scores for some website are questionablly low
    public int BoyerMoore(String T, String P) {
        int i = P.length() - 1;
        int j = P.length() - 1;

        while (i <= T.length() - 1) {
            if (T.charAt(i) == P.charAt(j)) {
                if (j == 0) {
                    return i;
                } else {
                    i = i - 1;
                    j = j - 1;
                }
            } else {
                int l = last(T.charAt(i), P);
                i = i + P.length() - min(j, 1 + l);
                j = P.length() - 1;
            }
        }

        return -1;
    }

    public int last(char c, String P) {
        int last = -1;
        for (int p = 0; p < P.length(); p++) {
            if (P.charAt(p) == c) {
                last = p;
            }
        }

        return last;
    }

    public int min(int a, int b) {
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else
            return a;
    }

    public int countKeyword(Keyword keyword) throws IOException {
        if (content == null) {
            content = fetchContent();
        }

        String contentModify = new String(content);

        contentModify = contentModify.toUpperCase();

        int retVal = 0;
        int count = 0;

        
        
        while (contentModify.length() > 0) {
            if (BoyerMoore(contentModify, keyword.getName().toUpperCase()) != -1) {
                count++;
                if (count == 1) {
                    retVal += keyword.getWeight() * 10;
                } else {
                    retVal += keyword.getWeight();
                }

                contentModify = contentModify.substring(
                        BoyerMoore(contentModify, keyword.getName().toUpperCase()) + keyword.getName().length() - 1);
            } else {
                contentModify = "";
            }
        }

        return retVal;
    }
}