package datastructures.linkedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// CLASS BEGINS, THIS CLASS IS REQUIRED
public class Solution {
    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    static List<Integer> lengthEachScene(List<Character> inputList) {
        List<Integer> result = new ArrayList<>();
        int[] mem = new int[26];
        for (int i = 0; i < inputList.size(); i++) {
            mem[inputList.get(i) - 'a'] = i;
        }
        int maxSoFar = 0;
        int startIndex = 0;
        for (int i = 0; i < inputList.size(); i++) {
            maxSoFar = Math.max(maxSoFar, mem[inputList.get(i) - 'a']);
            if (i == maxSoFar) {
                result.add(maxSoFar - startIndex + 1);
                startIndex = i + 1;
            }
        }
        return result;
    }

    // METHOD SIGNATURE ENDS
    public static void main(String[] args) {
        List<Character> l1 = Arrays.asList('a', 'b', 'a', 'b', 'c', 'b', 'a', 'c', 'a', 'd', 'e', 'f', 'e', 'g', 'd', 'e', 'h', 'i', 'j', 'h', 'k', 'l', 'i', 'j');
        List<Character> l2 = Arrays.asList('a', 'b', 'c', 'a');
        List<Integer> res = lengthEachScene(l2);
        System.out.println(Arrays.toString(res.toArray()));
    }
}
