
import java.util.ArrayList;

public class Web {
	private String title;
	private String url;
	public double score;

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
			info += s.getTitle() + "," + s.getUrl() + "\n";
		}
		if (!subWebs.isEmpty()) {
			System.out.println("\n" + info);
		} else {
			System.out.println("Sub-webs not found");
		}
	}

	public void presentResult() {
		for (Web web : subWebs) {
			System.out.println(web.title + " " + web.url + " " + web.score);
		}
	}

	public void mergeSort() {
		mergeSort(subWebs);
	}

	private void mergeSort(ArrayList<Web> arr) {
		if (arr.size() <= 1) {
			return;
		}

		int middle = arr.size() / 2;
		ArrayList<Web> left = new ArrayList<>(arr.subList(0, middle));
		ArrayList<Web> right = new ArrayList<>(arr.subList(middle, arr.size()));

		mergeSort(left);
		mergeSort(right);

		merge(arr, left, right);

	}

	private void merge(ArrayList<Web> arr, ArrayList<Web> left, ArrayList<Web> right) {
		int i = 0, j = 0, k = 0;

		while (i < left.size() && j < right.size()) {
			if (left.get(i).score <= right.get(j).score) {
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
