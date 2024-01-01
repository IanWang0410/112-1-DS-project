
import java.util.ArrayList;
import java.io.IOException;

public class WebTree {
	public WebNode root;
	public ArrayList<WebNode> treeMembers = new ArrayList<WebNode>();

	public WebTree(Web rootPage) {
		this.root = new WebNode(rootPage);
	}

	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException {
		setPostOrderScore(root, keywords);
	}

	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException {

		for (WebNode n : startNode.children) {
			setPostOrderScore(n, keywords);
			n.setNodeScore(keywords);
		}
		startNode.setNodeScore(keywords);

	}

	private void toList() {
		treeMembers.clear();
		dfsToList(root);
	}

	private void dfsToList(WebNode node) {
		if (node == null) {
			return;
		}

		treeMembers.add(node);

		for (WebNode child : node.children) {
			dfsToList(child);
		}
	}

	public String eularPrintTree() {
		return eularPrintTree(root);
	}

	// * result outprint is here
	private String eularPrintTree(WebNode startNode) {
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));

		String data = "";
		data += "(";
		data += startNode.web.getTitle() + "," + startNode.nodeScore;

		for (WebNode n : startNode.children) {
			eularPrintTree(n);
		}

		data += ")";

		if (startNode.isTheLastChild()) {
			data += "\n" + repeat("\t", nodeDepth - 2);
		}

		return data;
	}

	private String repeat(String str, int repeat) {
		String retVal = "";
		for (int i = 0; i < repeat; i++) {
			retVal += str;
		}
		return retVal;
	}

	// ! depricated moved to Web
	public void presentSortedResult() {
		toList();
		mergeSort(treeMembers);
		for (WebNode node : treeMembers) {
			System.out.println(node.web.getTitle() + " " + node.web.getUrl() + " " + node.nodeScore);
		}

	}

	// ! depricated moved to Web
	private void mergeSort(ArrayList<WebNode> arr) {
		if (arr.size() <= 1) {
			return;
		}

		int middle = arr.size() / 2;
		ArrayList<WebNode> left = new ArrayList<>(arr.subList(0, middle));
		ArrayList<WebNode> right = new ArrayList<>(arr.subList(middle, arr.size()));

		mergeSort(left);
		mergeSort(right);

		merge(arr, left, right);

	}

	private void merge(ArrayList<WebNode> arr, ArrayList<WebNode> left, ArrayList<WebNode> right) {
		int i = 0, j = 0, k = 0;

		while (i < left.size() && j < right.size()) {
			if (left.get(i).nodeScore <= right.get(j).nodeScore) {
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
