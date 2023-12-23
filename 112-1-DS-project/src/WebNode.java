import java.io.IOException;
import java.util.ArrayList;

public class WebNode {
    public WebNode parent;
    public ArrayList<WebNode> children;
    public Webs webs;
    public double nodeScore;// This node's score += all its children's nodeScore

    public WebNode(Webs webs) {
        this.webs = webs;
        this.children = new ArrayList<WebNode>();
    }

    public void setNodeScore(ArrayList<Keyword> keywords) throws IOException {
        // YOUR TURN
        // 2. calculate the score of this node
        // this method should be called in post-order mode

        // You should do something like:
        // 1.compute the score of this webs
        // 2.set this score to initialize nodeScore
        // 3.nodeScore must be the score of this webs
        // plus all children's nodeScore
        double sum = 0;
        for (int i = 0; i < children.size(); i++) {
            sum += children.get(i).nodeScore;
        }
        this.webs.setScore(keywords);
        this.nodeScore = sum + this.webs.getScore();
    }

    public void addChild(WebNode child) {
        // add the WebNode to its children list
        this.children.add(child);
        child.parent = this;
    }

    public boolean isTheLastChild() {
        if (this.parent == null)
            return true;
        ArrayList<WebNode> siblings = this.parent.children;

        return this.equals(siblings.get(siblings.size() - 1));
    }

    public int getDepth() {
        int retVal = 1;
        WebNode currNode = this;
        while (currNode.parent != null) {
            retVal++;
            currNode = currNode.parent;
        }
        return retVal;
    }

}