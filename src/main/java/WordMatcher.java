import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by fornia on 6/29/17.
 */
public class WordMatcher {
    private final TrieNode tree;
    private String str;
    int position = 0;

    public WordMatcher(TrieNode tree,String str){
        this.tree = tree;
        this.str = str;
        doFilter();
    }
    List<Pair> words = new ArrayList<>();


    public String replaceSensetiveWords(String replace){
        //doFilter();
        StringBuilder sb = new StringBuilder();
        List<String> split = split();
        int size = words.size();
        for(int i=0 ;i <= size;i++){
            sb.append(split.get(i));
            if(i < size){
                sb.append(getReplace(words.get(i),replace));
            }
        }
        return sb.toString();
    }

    public boolean isMatch(){
        return words.size()>0;
    }

    private String getReplace(Pair pair,String replace){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = pair.start;i<=pair.end;i++){
            stringBuilder.append(replace);
        }
        return stringBuilder.toString();
    }

    List<String> split(){
        List<String> splits = new ArrayList<>();
        int start = 0;
        int size = words.size();
        for(int i=0;i<=size;i++){
            if(i==size){
               splits.add(str.substring(start,str.length()));
            }else {
                Pair pair = words.get(i);
                splits.add(str.substring(start, pair.start));
                start = pair.end+1;
            }
        }
        return splits;
    }
    class Pair{
        int start;
        int end;
        Pair(int start,int end){
            this.start = start;
            this.end = end;
        }
    }
    private void doFilter(){
        for(;position < str.length();){
            Pair pair = matchPair(tree,position);
            if(pair == null){
                position++ ;
            } else{
                handlePair(pair);
            }
        }
    }
    private void handlePair(Pair pair){
        position += pair.end - pair.start + 1;
        words.add(pair);
    }

    private Pair matchPair(TrieNode trienode,int index){
        List<Character> chars = new ArrayList<>();
        TrieNode node = findNode(trienode, index,chars);
        while (node != null && !node.isWord()){
            node = node.getParent();
        }
        if(node != null){
            return new Pair(position,position+node.getHeight()+chars.size());
        }
        return null;
    }
    private TrieNode findNode(TrieNode trieNode ,int index,List chars){
        while (TreeUtil.specialChars.contains(str.charAt(index))){//去掉特殊字符
            chars.add(str.charAt(index));
            index ++;
        }
        TrieNode node = trieNode.getChildren().get(str.charAt(index));
        if(index < str.length() - 1 && node != null) {
            return findNode(node,index + 1,chars);
        }else {
            return node == null?trieNode:node;
        }
    }

}
