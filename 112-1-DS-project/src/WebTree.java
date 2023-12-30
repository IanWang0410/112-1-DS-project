
import java.util.ArrayList;
import java.io.IOException;

public class WebTree {
	public WebNode root;
	
	public WebTree(Web rootPage){
		this.root = new WebNode(rootPage);
	}
	
	public void setPostOrderScore(ArrayList<Keyword> keywords) throws IOException{
		setPostOrderScore(root, keywords);
	}
	
	private void setPostOrderScore(WebNode startNode, ArrayList<Keyword> keywords) throws IOException{
		
		for(WebNode n: startNode.children) {
			setPostOrderScore(n, keywords);
			n.setNodeScore(keywords);
		}
		startNode.setNodeScore(keywords);
		
	}
	
	public String eularPrintTree(){
		return eularPrintTree(root);
	}
	
	private String eularPrintTree(WebNode startNode){
		int nodeDepth = startNode.getDepth();
		
		if(nodeDepth > 1) System.out.print("\n" + repeat("\t", nodeDepth-1));
		
		String data = "";
		data += "(";
		data += startNode.web.getTitle() + "," + startNode.nodeScore;
		
		for(WebNode n: startNode.children) {
			eularPrintTree(n);
		}
		
		
		data += ")";
				
		if(startNode.isTheLastChild()) {
			data += "\n" + repeat("\t", nodeDepth-2);	
		}
		
		return data;
	}
	
	private String repeat(String str, int repeat){
		String retVal = "";
		for(int i = 0; i < repeat; i++){
			retVal += str;
		}
		return retVal;
	}
}
