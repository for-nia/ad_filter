import java.lang.management.ThreadInfo;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * Created by fornia on 6/28/17.
 */
public class TreeUtil {

    public static void addNode(TrieNode tree, String word){
        if(word == null || word.length()<=0){
            throw new IllegalArgumentException();
        }
        TrieNode root = tree;
        int size = word.length();
        for(int i = 0;i < size;i++){
            Character c = word.charAt(i);
            TrieNode node = root.getChildren().get(c);
            if(node == null){
                node = i<size-1 ? buildCommonNode(c,i):buildLeafNode(c,i);
                root.getChildren().put(c,node);
            }else {
                if(i>=size-1){
                    node.setLeaf(true);
                }
            }
            root = node;
        }
    }
    public static TrieNode buildLeafNode(Character c,int height){
        return new TrieNode.Builder(c).setHeight(height).setIsLeaf(true).build();
    }
    public static TrieNode buildCommonNode(Character c,int height){
        return new TrieNode.Builder(c).setHeight(height).setIsLeaf(false).build();
    }
}
