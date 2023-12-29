import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

    public HashMap<String, WebPageNode> buildWebPageTrees() throws IOException {
        HashMap<String, String> pageMap = query();
        HashMap<String, WebPageNode> pageTrees = new HashMap<>();

        for (Map.Entry<String, String> entry : pageMap.entrySet()) {
            String title = entry.getKey();
            String url = entry.getValue();
            WebPageNode root = new WebPageNode(title, url);
            pageTrees.put(url, root); // 将每个页面的URL与其对应的根节点关联起来

            // 构建树形结构
            buildTree(root);
        }

        return pageTrees;
    }

    private void buildTree(WebPageNode root) throws IOException {
        String url = root.getUrl();
        String content = fetchContent(url);

        Document doc = Jsoup.parse(content);
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String childUrl = link.attr("abs:href");
            WebPageNode child = new WebPageNode("", childUrl); // 子节点的标题暂时为空
            root.addChild(child);
            buildTree(child); // 递归构建子树
        }
    }
}