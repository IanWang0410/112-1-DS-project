import java.util.ArrayList;

class Node extends Webs{
    
    public Node parent;
    public ArrayList<Node> children;

    public Node(String title, String url) {
        super(title, url);
        this.children = new ArrayList<>();
    }
}
