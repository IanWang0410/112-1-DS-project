
import java.util.ArrayList;


public class Web {
	private String title;
	private String url;
	
	private ArrayList<Web> subWebs = new ArrayList<>();
	
	public Web(String searchKeyword) {
		this.title = "Google Search";
		try {
            String encodeKeyword = java.net.URLEncoder.encode(searchKeyword, "utf-8");
            this.url = "https://www.google.com/search?q=" + encodeKeyword + "&gl=us&oe=utf8&num=20";

        } catch (Exception e) {

        }
	}
	
	public Web(String title, String url) {
		this.title = title;
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getTitle() {
		return title;
	}
	
	public ArrayList<Web> getSubWebsList() {
		return subWebs;
	}
	
	public void printWebsList() {
		String info = "";
		for (Web s : subWebs) {
			info += s.getTitle() + "," + s.getUrl() +"\n";
		}
		if (!subWebs.isEmpty()) {
			System.out.println("\n" + info);
		}
		else {
			System.out.println("Sub-webs not found");
		}
	}
}