package leetcode.microsoft.discuss;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class PartitionArrayIntoNSubsets {
    public static void main(String[] args) {
        System.out.println(getPartitions(new int[]{1,2,3,4,5,6,7,8,9,10}, 3));
    }
    private static List<List<Integer>> getPartitions(int[] T, int n){
        List<List<Integer>> res = new ArrayList<>();
        int[] sum = new int[n];
        Queue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(i -> sum[i]));
        for(int i = 0; i < n; i++){
            res.add(new ArrayList<>());
            pq.offer(i);
        }
        for(int i = T.length-1; i >= 0 ; i--){
            int c = pq.poll();
            res.get(c).add(T[i]);
            sum[c] += T[i];
            pq.offer(c);
        }
        return res;
    }
}
