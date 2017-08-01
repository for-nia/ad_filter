import java.util.HashSet;
import java.util.Set;

/**
 * Created by fornia on 6/28/17.
 */
public class TreeUtil {
    private static String INVALID_CHARS = "#!%\":-,，.。~$`^=|<>～｀＄＾＝｜＜＞￥× ]⇦【】_";
    public static Set<Character> specialChars= new HashSet<>();
    static {
        for(char ch : INVALID_CHARS.toCharArray()){
            specialChars.add(ch);
        }
    }

    public static void addNode(TrieNode tree, String word){
        word = removeSpecialChars(word);
        if(word == null || word.length()<=0){
            throw new IllegalArgumentException();
        }
        TrieNode root = tree;
        int size = word.length();
        for(int i = 0;i < size;i++){
            Character c = word.charAt(i);
            TrieNode node = root.getChildren().get(c);
            if(node == null){
                node = i<size-1 ? buildCommonNode(c,i,root):buildLeafNode(c,i,root);
                root.getChildren().put(c,node);
            }else {
                if(i>=size-1){
                    node.setWord(true);   // 到达词的终点,但是不是叶子节点,说明匹配到词语
                }
            }
            root = node;
        }
    }

    private static String removeSpecialChars(String str){
        if(str == null || str.length() ==0){
            throw new IllegalArgumentException();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(char ch:str.toCharArray()){
            if(specialChars.contains(ch)){
                continue;
            }
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }
    public static TrieNode buildLeafNode(Character c,int height,TrieNode parent){
        return new TrieNode.Builder(c).setHeight(height).setIsLeaf(true).setParent(parent).build();
    }
    public static TrieNode buildCommonNode(Character c,int height,TrieNode parent){
        return new TrieNode.Builder(c).setHeight(height).setIsLeaf(false).setParent(parent).build();
    }
}
