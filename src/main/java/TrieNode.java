import java.util.HashMap;
import java.util.Map;

/**
 * Created by fornia on 6/28/17.
 */
public class TrieNode {
    private boolean isWord;
    private boolean isLeaf;
    private int height=-1;
    private char content;
    private Map<Character,TrieNode> children;
    TrieNode parent;

    public TrieNode(char c){
        this.content = c;
        children = new HashMap<>();
    }
    public TrieNode(){
        children = new HashMap<>();
    }

    public static class Builder{
        TrieNode node;
        public Builder(){
            this.node = new TrieNode();
        }
        public Builder(char c){
            this.node = new TrieNode(c);
        }
        public Builder setIsLeaf(boolean isLf){
            node.isLeaf = isLf;
            node.isWord = true;
            return this;
        }
        public Builder setParent(TrieNode p){
            node.parent = p;
            return this;
        }
        public Builder setHeight(int h){
            node.height = h;
            return this;
        }
        /*public Builder setIsWord(boolean isWord){
            node.isWord = isWord;
            return this;
        }*/
        public TrieNode build(){
            return node;
        }
    }

    public void addChild(TrieNode child){
        children.put(child.getContent(),child);
    }
    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(Map<Character, TrieNode> children) {
        this.children = children;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public TrieNode getParent() {
        return parent;
    }

    public void setParent(TrieNode parent) {
        this.parent = parent;
    }
}
