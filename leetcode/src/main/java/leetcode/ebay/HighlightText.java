package leetcode.ebay;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class HighlightText {
    /*
Text Highlight

Text: “This is a great gre book!”

1,Search  “great” “eat”
A: “This is a <h1>great</h1>  gre book!”


2,Search  “great” “gre”
A: “This is a <h1>great</h1>  <h1>gre</h1> book!”



3,Search  “grea” “eat”

A: “This is a <h1>great</h1> gre book!”


4, Search  “gr” “eat”
A: “This is a <h1>gr</h1><h1>eat</h1>  <h1>gre</h1> book!”





*/
    public static void main(String[] args) {
        String text = "This is a great gre book!";
        System.out.println(highLightText(text, new String[]{"great", "eat"}));
        System.out.println(highLightText(text, new String[]{"great", "gre"}));
        System.out.println(highLightText(text, new String[]{"grea", "eat"}));
        System.out.println(highLightText(text, new String[]{"gr", "eat"}));
    }

    public static String highLightText(String text, String[] query) {
        String res = "";
        Queue<int[]> indexes = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for(String search: query){
            int i = 0;
            while(i != -1){
                i = text.indexOf(search, i == 0 ? 0 : i+1);
                if(i != -1){
                    indexes.offer(new int[]{i, i+search.length()-1});
                }
            }
        }
        if(indexes.isEmpty()){
            return text;
        }
        int start = indexes.peek()[0];
        int end = indexes.poll()[1];
        int idx = 0;
        int[] interval = null;
        while (!indexes.isEmpty()){
            interval = indexes.poll();
            if(end < interval[0]){
                res += text.substring(idx, start) + "<h1>" + text.substring(start, end+1) + "</h1>";
                idx = end+1;
                start = interval[0];
                end = interval[1];
            } else if(end >= interval[1]){
                continue;
            } else{
                end = interval[1];
            }
        }
        if(idx < end){
            res += text.substring(idx, start) + "<h1>" + text.substring(start, end+1) + "</h1>";
            idx = end+1;
        }
        if(idx < interval[1]){
            res += text.substring(idx, interval[0]) + "<h1>" + text.substring(interval[0], interval[1]+1) + "</h1>";
            end = interval[1];
        }
        res += text.substring(end+1, text.length());
        return res;
    }
}
