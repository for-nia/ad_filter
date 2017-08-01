import org.junit.Before;
import org.junit.Test;

/**
 * Created by fornia on 8/1/17.
 */
public class AdMatcherTest {

    AdMatcher adMatcher = AdMatcher.AdMatcherBuilder.getAdMatcher();
    @Before
    public void initTree(){
        adMatcher.addWord("中-国");
        adMatcher.addWord("中国人");
        adMatcher.addWord("中华人民");
        adMatcher.addWord("傻逼");
    }


    @Test
    public void testAdMatchSpeed(){
        long start = System.currentTimeMillis();
        for(int i = 0;i<1000000;i++){
            adMatcher.filter("我是傻-，逼中-国人的中,华-人_--民de");
        }
        System.out.println(System.currentTimeMillis()-start);
    }

    @Test
    public void testMatch(){
        assert true == adMatcher.isAd("我是傻-，逼中-国人的中,华-人_--民de");
    }





}
