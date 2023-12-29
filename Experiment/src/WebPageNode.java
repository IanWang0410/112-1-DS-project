import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebPageNode {
    private String title;
    private String url;
    private List<WebPageNode> children;

    public WebPageNode(String title, String url) {
        this.title = title;
        this.url = url;
        this.children = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public List<WebPageNode> getChildren() {
        return children;
    }

    public void addChild(WebPageNode node) {
        children.add(node);
    }

    


}
