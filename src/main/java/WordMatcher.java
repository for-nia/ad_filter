import java.util.ArrayList;
import java.util.List;

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
    }
    List<Pair> words = new ArrayList<>();


    public String replaceSensetiveWords(String replace){
        doFilter();
        words.stream().forEach(pair->{
            System.out.println("start :"+pair.start+"end:"+pair.end);
        });
        StringBuilder sb = new StringBuilder();
        split().stream().forEach(s->sb.append(s).append(replace));
        return sb.substring(0,sb.length()-replace.length());
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
        position += pair.end - pair.start;
        words.add(pair);
    }
    private Pair matchPair(TrieNode trieNode ,int index){
        TrieNode node = trieNode.getChildren().get(str.charAt(index));
        if(index < str.length() && node != null) {
            if (node.isLeaf()) {
                return new Pair(position, index);
            }else {
                return matchPair(node,index + 1);
            }
        }else {
            return null;
        }
    }

}
