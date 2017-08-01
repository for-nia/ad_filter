
/**
 * Created by fornia on 8/1/17.
 */
public class SensitiveWordMatcher {

    private static TrieNode sensitiveWords = new TrieNode();


    private SensitiveWordMatcher(){

    }
    public static class SensitiveWordMatcherBuilder{
        private static final SensitiveWordMatcher sensitiveWordMatcher = new SensitiveWordMatcher();
        public static SensitiveWordMatcher getSensitiveWordMatcher(){
            return sensitiveWordMatcher;
        }
    }

    public void addWord(String word){
        TreeUtil.addNode(sensitiveWords,word);
    }

    public boolean isInvalidWord(String text){
        WordMatcher wordMatcher = new WordMatcher(sensitiveWords,text);
        return wordMatcher.isMatch();
    }

    public String filter(String text){
        WordMatcher wordMatcher = new WordMatcher(sensitiveWords,text);
        return wordMatcher.replaceSensetiveWords("*");
    }

    //如果有些操作需要获取敏感词，可以获取trie树，通过wordMatcher 获取捕捉到的敏感词
    public TrieNode getTree(){
        return sensitiveWords;
    }

}
