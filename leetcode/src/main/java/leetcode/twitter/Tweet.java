package leetcode.twitter;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public static void main(String[] args) {
        Tweet t = new Tweet();
        String[] inputs = new String[]{"some #body", "some#body", "@some#body", "@hhh @hhh "};
        for(String s : inputs){
            System.out.println(s+"-->"+t.tweetSize(s));
            System.out.println(s+"-------->"+getCharacters(s));
        }
    }
    /**
    * Write a method tweetSize that accepts a string and returns an integer.
     * Handles (@username) don't count towards the total
     * Hashtags (#wow) don't count towards the total
     * Punctuation doesn't count towards the total
     * There’s a whitelist of valid handle/hashtag characters: a-z, A-Z, 0-9, _
     * A hashtag/handle will therefore be a # or @ character, followed by 1 or more characters from the whitelist
     * The hashtag/handle ends at the first non-whitelisted character
     * Punctuation is defined as:!"#$%&'()*+,-./:;<=>?@[]^_`{|}~
     * There's a blacklist of characters that cannot come before a handle/hashtag
     * (i.e. if one of these characters is directly before a # or @, that # or @ does NOT signify the start of a handle/hashtag):
     * blacklist: a-z, A-Z, 0-9, _, &
     * ex: "some #body" (5 characters)
     * ex: "some#body" (#body is not a hashtag... 8 characters)
     * ex: "@some#body" (@some IS a handle, #body still not a hashtag... 4 characters)
     * ex: "@hhh @hhh " (1)
    * */
    private static final String punctuationsStr = "!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    private long tweetSize(String tweet){
        /*
        * handleOrHashTag
        * punctuationsStr
        *
        * */
        boolean handleOrHashTag = false;
        long count = 0;
        for(int i = 0; i < tweet.length(); i++){
            if(tweet.charAt(i) == '#' || tweet.charAt(i) == '@'){
                handleOrHashTag = i > 0 && isCharInBlackList(tweet.charAt(i-1)) ? false : true;
            } else if(handleOrHashTag && !isHashTagOrHandleWhitelist(tweet.charAt(i))){
                handleOrHashTag = false;
            }
            if(!handleOrHashTag && punctuationsStr.indexOf(tweet.charAt(i)) == -1){
                count++;
            }
        }
        return count;
    }

    private static boolean isCharInBlackList(char ch) {
        return  isHashTagOrHandleWhitelist(ch) || ch == '&';
    }

    private static boolean isHashTagOrHandleWhitelist(char ch){
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || Character.isDigit(ch) || ch == '_';
    }

    public static int getCharacters(String str) {
        int i = 0;
        String text = "";
        List<String> hashTags = new ArrayList<>();
        List<String> userNames = new ArrayList<>();
        char letter;
        while (i < str.length()) {
            letter = str.charAt(i);
            if ((letter == '#' || letter == '@') && (i == 0 || !isCharInBlackList(str.charAt(i - 1)))) {
                int b = i;
                while (i+1 < str.length() && isHashTagOrHandleWhitelist(str.charAt(i+1))) {i++;}
                if(letter == '#'){
                    hashTags.add(str.substring(b, i+1));
                } else {
                    userNames.add(str.substring(b, i+1));
                }
            } else if(punctuationsStr.indexOf(str.charAt(i)) == -1){
                text += str.charAt(i);
            }
            i++;
        }
        return text.length();
    }
}
