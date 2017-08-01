import java.util.List;

/**
 * Created by fornia on 6/29/17.
 */
public class AdMatcher {
    private static final TrieNode tree = new TrieNode();


    private AdMatcher(){

    }

    public static AdMatcher getInstance(){
        return AdMatcherBuilder.INSTANCE;
    }
    private static class AdMatcherBuilder{
        private static final AdMatcher INSTANCE = new AdMatcher();
    }

    public void addWord(String word){
        TreeUtil.addNode(tree,word);
    }

    public boolean isAd(String text){
        WordMatcher wordMatcher = new WordMatcher(tree,text);
        return wordMatcher.isMatch();
    }


    public String filter(String text){
        WordMatcher wordMatcher = new WordMatcher(tree,text);
        return wordMatcher.replaceSensetiveWords("*");
    }


    public static void main(String[] args){
        TreeUtil.addNode(tree,"中-国");
        TreeUtil.addNode(tree,"中国人");
        TreeUtil.addNode(tree,"中华人民");
        TreeUtil.addNode(tree,"傻逼");
        //WordMatcher wordMatcher = new WordMatcher(tree,"我是傻-，逼中-国人的中,华-人_--民de");
        WordMatcher wordMatcher = new WordMatcher(tree,null);
        String s = wordMatcher.replaceSensetiveWords("*");
        System.out.println(wordMatcher.isMatch());
        List<String> words = wordMatcher.getMatchWords();
        System.out.println(s);
    }

}
