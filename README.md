# 过滤系统使用

## 介绍
> 该过滤系统基于trie树实现，能够对敏感词进行过滤，也可以用于广告的检测

## WordMatcher
> `WordMatcher`是核心类，提供了一些核心方法， `AdMatcher` 和 `SensitiveWordMatcher` 是对`WordMatcher` 的封装，使用方法如下：

```java
        TrieNode tree = new TrieNode();
        TreeUtil.addNode(tree,"中-国");
        TreeUtil.addNode(tree,"中国人");
        TreeUtil.addNode(tree,"中华人民");
        TreeUtil.addNode(tree,"傻逼");
        WordMatcher wordMatcher = new WordMatcher(tree,"我是傻-，逼中-国人的中,华-人_--民de");
        String s = wordMatcher.replaceSensetiveWords("*");//将敏感词替换为*
        assert true == wordMatcher.isMatch());//是否匹配到敏感词
        List<String> words = wordMatcher.getMatchWords(); //获取匹配到的敏感词
```

