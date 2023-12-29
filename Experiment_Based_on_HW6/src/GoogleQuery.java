import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleQuery 
{
	public String searchKeyword;
	public String url;
	public String content;
    public ArrayList<WebTree> webTrees = new ArrayList<>();
	
	public GoogleQuery(String searchKeyword)
	{
		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
	}
	
	private String fetchContent() throws IOException
	{
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null)
		{
			retVal += line;
		}
		return retVal;
	}
	
    private String fetchContent(String url) throws IOException {
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
        InputStream in = conn.getInputStream();
        InputStreamReader inReader = new InputStreamReader(in, "utf-8");
        BufferedReader bufReader = new BufferedReader(inReader);
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = bufReader.readLine()) != null) {
            content.append(line);
        }
        return content.toString();
    }

	public HashMap<String, String> query() throws IOException
	{
		if(content == null)
		{
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		
		/* 
		 * some Jsoup source
		 * https://jsoup.org/apidocs/org/jsoup/nodes/package-summary.html
		 * https://www.1ju.org/jsoup/jsoup-quick-start
 		 */
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
		System.out.println(doc.select("a").get(0).attr("href"));
		lis = lis.select(".kCrYT");
		
		for(Element li : lis)
		{
			try 
			{
				String citeUrl = li.select("a").get(0).attr("href");
				String title = li.select("a").get(0).select(".vvjwJb").text();
				
				if(title.equals("")) 
				{
					continue;
				}
				
				System.out.println("Title: "+title + " , url: " + citeUrl);
				
				//put title and pair into HashMap
				retVal.put(title, citeUrl);

			} catch (IndexOutOfBoundsException e) 
			{
//				e.printStackTrace();
			}
		}
		return retVal;
	}

    public void buildTrees(String keyword){
        try {
            GoogleQuery googleQuery = new GoogleQuery(keyword);
            HashMap<String, String> searchResults = googleQuery.query();


            for (String title : searchResults.keySet()) {
                String url = searchResults.get(title);
                WebPage mainPage = new WebPage(url, title); // 主網頁以搜索結果的標題命名
                WebTree tree = new WebTree(mainPage); // 主網頁作為根
                
                // 可以進一步對主網頁的子網頁進行操作
                // 這裡可能需要再次發送請求以獲取子網頁的URL列表
                // 假設以下的子網頁URL列表是從主網頁中獲得的
                ArrayList<String> subPageURLs = new ArrayList<>(); // 假設這是子網頁的URL列表

                for (String subURL : subPageURLs) {
                    WebPage subPage = new WebPage(subURL, "子網頁名稱"); // 子網頁
                    tree.root.addChild(new WebNode(subPage)); // 子網頁作為主網頁的子節點
                }

                // add the new webTree into webTrees
                webTrees.add(tree);

                /*
                 * // 執行計分或進一步操作
                ArrayList<Keyword> keywords = new ArrayList<>(); // 定義您的關鍵詞
                tree.setPostOrderScore(keywords); // 計算得分

                 */


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}