import java.util.ArrayList;

class Node extends Webs{
    
    public Node parent;
    public ArrayList<Node> children;

    public Node(String title, String url) {
        super(title, url);
        this.children = new ArrayList<>();
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public ArrayList<Node> getChildren(){
        return children;
    }
}
