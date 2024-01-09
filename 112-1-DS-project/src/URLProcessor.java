

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

public class URLProcessor {

	public ArrayList<Web> searchResult = new ArrayList<>();

	public String fetchContent(Web w) throws IOException {
		String retVal = "";

		try {
			URL u = new URL(w.getUrl());
			URLConnection conn = u.openConnection();
			conn.setReadTimeout(3000); // set timeout(milliseconds)

			conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
			InputStream in = conn.getInputStream();

			InputStreamReader inReader = new InputStreamReader(in, "utf-8");
			BufferedReader bufReader = new BufferedReader(inReader);
			String line = null;

			while ((line = bufReader.readLine()) != null) {
				retVal += line + "\n";
			}
			return retVal;

		} catch (IOException e) {

			// System.out.println("403");
			return ""; // Error 403
		}
	}

	public HashMap<String, String> query(Web w) throws IOException {
		String content = fetchContent(w);

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

				w.getSubWebsList().add(new Web(title, citeUrl)); // adding the URLs found to the list

				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) {

			}
		}
		return retVal;
	}

	public ArrayList<Web> getChildUrls(Web w) throws IOException {
		String content = fetchContent(w);
		String format = w.getUrl();

		try {
			while (content.length() > format.length()) {
				content = content.substring(content.indexOf("href=\"") + 6, content.length());
				String potentialUrl = content.substring(0, content.indexOf("\""));
				String title = "title";

				if (potentialUrl.indexOf(format) == 0 && !potentialUrl.equals(format)) {
					w.getSubWebsList().add(new Web(title, potentialUrl));
				} else {
					continue;
				}

			}
		} catch (Exception e) {

		}

		// w.printUrlsList();
		return w.getSubWebsList();
	}

}