
import java.util.ArrayList;
import java.io.IOException;

public class WebTree {
	public WebNode root;
	public ArrayList<WebNode> members = new ArrayList<>();

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

	public String eularPrintTree() {
		return eularPrintTree(root);
	}

	private String eularPrintTree(WebNode startNode) {
		int nodeDepth = startNode.getDepth();

		if (nodeDepth > 1)
			System.out.print("\n" + repeat("\t", nodeDepth - 1));

		String data = "";
		data += "(";
		data += startNode.web.getTitle() + "," + startNode.web.getUrl() + "," + startNode.nodeScore;

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

	// ! wrong, don't care
	/*
	 * private void toList() {
	 * toList(root);
	 * }
	 * 
	 * private void toList(WebNode startNode) {
	 * members.add(startNode);
	 * 
	 * for (WebNode child : startNode.children) {
	 * toList(child);
	 * }
	 * 
	 * }
	 * 
	 * public void presentOutput() {
	 * this.toList();
	 * mergeSort(members, 0, members.size() - 1);
	 * 
	 * for (WebNode node : members) {
	 * System.out.println(node.web.getTitle() + " , " + node.web.getUrl() + " , " +
	 * node.nodeScore);
	 * }
	 * 
	 * // sort the list
	 * 
	 * }
	 * 
	 * private void mergeSort(ArrayList<WebNode> nodes, int left, int right) {
	 * if (left < right) {
	 * int mid = (left + right) / 2;
	 * 
	 * mergeSort(nodes, left, mid);
	 * mergeSort(nodes, mid + 1, right);
	 * 
	 * merge(nodes, left, mid, right);
	 * }
	 * }
	 * 
	 * private void merge(ArrayList<WebNode> nodes, int left, int mid, int right) {
	 * int n1 = mid - left + 1;
	 * int n2 = right - mid;
	 * 
	 * ArrayList<WebNode> leftArr = new ArrayList<>();
	 * ArrayList<WebNode> rightArr = new ArrayList<>();
	 * 
	 * for (int i = 0; i < n1; ++i) {
	 * leftArr.add(nodes.get(left + i));
	 * }
	 * for (int j = 0; j < n2; ++j) {
	 * rightArr.add(nodes.get(mid + 1 + j));
	 * }
	 * 
	 * int i = 0, j = 0;
	 * int k = left;
	 * 
	 * while (i < n1 && j < n2) {
	 * if (leftArr.get(i).nodeScore >= rightArr.get(j).nodeScore) {
	 * nodes.set(k, leftArr.get(i));
	 * i++;
	 * } else {
	 * nodes.set(k, rightArr.get(j));
	 * j++;
	 * }
	 * k++;
	 * }
	 * 
	 * while (i < n1) {
	 * nodes.set(k, leftArr.get(i));
	 * i++;
	 * k++;
	 * }
	 * while (j < n2) {
	 * nodes.set(k, rightArr.get(j));
	 * j++;
	 * k++;
	 * }
	 * 
	 * }
	 */

}
