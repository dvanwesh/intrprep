package leetcode.microsoft.mar24twentyone;

import java.util.Stack;

public class LargestNumAfterRemovingNChars {

    public static void main(String[] args) {
        System.out.println(greatestNumAfterRemovingNChars("4325023", 4));
    }

    private static String greatestNumAfterRemovingNChars(String str, int n){
        Stack<Character> stk = new Stack<>();
        for(int i = 0; i < str.length(); i++){
            while (!stk.isEmpty() && stk.peek() < str.charAt(i) && stk.size()+str.length()-i > str.length()-n){
                stk.pop();
            }
            stk.push(str.charAt(i));
        }
        String res = "";
        for (char s : stk){
            res += s;
        }
        return res;
    }
}
