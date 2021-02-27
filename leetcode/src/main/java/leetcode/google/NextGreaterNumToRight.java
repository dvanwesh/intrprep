package leetcode.google;


import leetcode.utils.Pair;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/*
* Find distance of next greater number to the right in an array
[1, 4, 2, 3, 5]    ans: ((0,1), (1,3), (2, 1), (3,1), (4, 0))
* */
public class NextGreaterNumToRight {
    private static int[] nextGreaterNumToRight(int[] ar) {
        System.out.println(Arrays.toString(ar));
        int[] ans = new int[ar.length];
        if (ar.length == 0) {
            return ans;
        }
        Stack<Pair<Integer, Integer>> stk = new Stack<>();
        stk.push(new Pair<>(ar[ar.length - 1], ar.length - 1));
        ans[ar.length - 1] = 0;
        for (int i = ar.length - 2; i >= 0; i--) {
            while (!stk.isEmpty() && ar[i] >= stk.peek().getKey()) {
                stk.pop();
            }
            if (!stk.isEmpty() && ar[i] < stk.peek().getKey()) {
                ans[i] = stk.peek().getValue() - i;
            } else {
                ans[i] = 0;
            }
            stk.push(new Pair<>(ar[i], i));
        }
        return ans;
    }

    public static void main(String[] args) {
        Random rn = new Random();
        int n = rn.nextInt(15);
        int[] ar = new int[n];
        for (int i = 0; i < n; i++) {
            ar[i] = rn.nextInt(20);
        }
        System.out.println(Arrays.toString(nextGreaterNumToRight(ar)));
    }

    private static int[] nextGreaterNumInCircularArray(int[] ar) {
        int[] res = new int[ar.length];
        if (ar.length == 0) {
            return res;
        }
        for (int i = ar.length - 2; i >= 0; i--) {
            //todo write logic
        }
        return res;
    }
}
