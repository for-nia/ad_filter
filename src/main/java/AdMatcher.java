/**
 * Created by fornia on 6/29/17.
 */
public class AdMatcher {
    private static final TrieNode tree = new TrieNode();



    public static void main(String[] args){
   //     String a="a中国cde";
 //       System.out.println(a.codePointAt(0));
        TreeUtil.addNode(tree,"中国");
        TreeUtil.addNode(tree,"中国人");
        TreeUtil.addNode(tree,"中华人民");
        WordMatcher wordMatcher = new WordMatcher(tree,"我是中国人的中华人民");
        String s = wordMatcher.replaceSensetiveWords("*");
        System.out.println(s);
    }

}
