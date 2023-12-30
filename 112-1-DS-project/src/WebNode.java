
import java.util.ArrayList;
import java.io.IOException;

public class WebNode {
	public WebNode parent;
	public ArrayList<WebNode> children;
	public Web web;	
	public double nodeScore;//This node's score += all its children's nodeScore
	
	WordCounter wc = new WordCounter();
	
	public WebNode(Web web){
		this.web = web;
		this.children = new ArrayList<WebNode>();
	}
	
	public void setNodeScore(ArrayList<Keyword> keywords) throws IOException{
		//nodeScore += all children's nodeScore 
		for (Keyword k : keywords) {
			nodeScore += wc.countKeyword(web, k.getName()) * k.getWeight();
		}
		for(WebNode child : children){
			nodeScore += child.nodeScore;
		}		
	}
	
	public void addChild(WebNode child){
		//add the WebNode to its children list
		this.children.add(child);
		child.parent = this;
	}
	
	public boolean isTheLastChild(){
		if(this.parent == null) return true;
		ArrayList<WebNode> siblings = this.parent.children;
		
		return this.equals(siblings.get(siblings.size() - 1));
	}
	
	public int getDepth(){
		int retVal = 1;
		WebNode currNode = this;
		while(currNode.parent != null){
			retVal++;
			currNode = currNode.parent;
		}
		return retVal;
	}
}