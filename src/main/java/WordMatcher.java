import java.util.ArrayList;
import java.util.List;

/**
 * Created by fornia on 6/29/17.
 */
public class WordMatcher {
    private final TrieNode tree;
    private String str;
    int position = 0;
    List<Pair> pairs = new ArrayList<>(); //匹配到的关键词列表
    List<TrieNode> wordNodes = new ArrayList<>();

    public WordMatcher(TrieNode tree,String str){
        this.tree = tree;
        this.str = str;
        if(str == null){
            throw new IllegalArgumentException("cannot pass null to match");
        }
        doFilter();
    }


    public String replaceSensetiveWords(String replace){
        //doFilter();
        StringBuilder sb = new StringBuilder();
        List<String> split = split();
        int size = pairs.size();
        for(int i=0 ;i <= size;i++){
            sb.append(split.get(i));
            if(i < size){
                sb.append(getReplace(pairs.get(i),replace));
            }
        }
        return sb.toString();
    }

    public List<String> getMatchWords(){
        List<String> words = new ArrayList<>();
        for(TrieNode node:wordNodes){
            words.add(getWord(node));
        }
        return words;
    }

    private String getWord(TrieNode node){
        StringBuilder stringBuilder = new StringBuilder();
        while (null!=node&&node.getHeight()>=0){
            stringBuilder.append(node.getContent());
            node = node.getParent();
        }
        return stringBuilder.reverse().toString();
    }

    public boolean isMatch(){
        return pairs.size()>0;
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
        int size = pairs.size();
        for(int i=0;i<=size;i++){
            if(i==size){
               splits.add(str.substring(start,str.length()));
            }else {
                Pair pair = pairs.get(i);
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
        pairs.add(pair);
    }

    private Pair matchPair(TrieNode trienode,int index){
        List<Character> chars = new ArrayList<>();
        TrieNode node = findNode(trienode, index,chars);
        while (node != null && !node.isWord()){
            node = node.getParent();
        }
        if(node != null){
            wordNodes.add(node);
            return new Pair(position,position+node.getHeight()+chars.size());
        }
        return null;
    }
    private TrieNode findNode(TrieNode trieNode ,int index,List chars){
        while (TreeUtil.specialChars.contains(str.charAt(index))){//去掉特殊字符
            chars.add(str.charAt(index));//记录特殊字符，主要目的是获取过滤词长度
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
