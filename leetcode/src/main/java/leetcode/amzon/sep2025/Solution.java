package leetcode.amzon.sep2025;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    public static boolean isBreaching(int[] arr, int threshold, int m, int n) { // [1, 2, 100, 1, 1, 0, 100, 0, 0, 100, 2]
        if(arr == null || arr.length < n) {
            return false;
        }
        Queue<Integer> indices = new LinkedList<>();
        for(int i = 0; i < arr.length; i++) { // [1, 2, 100, 1, 1, 100, 2] i = 6 indices = 2,6
            if(arr[i] < threshold) {
                continue;
            }
            indices.offer(i);
            while(!indices.isEmpty() && i - indices.peek() + 1 > n) { // 5 < 4
                indices.poll();
            }

            if(indices.size() >= m) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(isBreaching(new int[]{1, 2, 100, 1, 1, 100, 2}, 50, 2, 4));
        System.out.println(isBreaching(new int[]{100, 100, 100}, 50, 1, 4));
        System.out.println(isBreaching(new int[]{1, 2, 100, 1, 1, 1, 100, 1, 1, 100, 2}, 50, 2, 4));
        System.out.println(isBreaching(new int[]{1, 2, 100, 1, 1, 1, 100, 1, 1, 0, 100, 2}, 50, 2, 4));
    }
}
