package leetcode.microsoft.mar24twentyone;

import java.util.Arrays;

public class MergeInPlace {
    public static void main(String [] args) {
        // you can write to stdout for debugging purposes, e.g.
        int[] A = new int[] {1, 3, 4, 5, 8};
        int[] B = new int[] {2, 3, 6, 9, 0, 0, 0, 0, 0};
        mergeInPlace(A, B, 5, 4);
        System.out.println(Arrays.toString(B));
    }

    public static void mergeInPlace(int[] A, int[] B, int n, int m){
        int p1 = n-1;
        int p2 = m-1;
        for(int i = B.length - 1; i >= 0; i--){
            if(p1 >= 0 && p2 >= 0){
                if(B[p2] >= A[p1]){
                    B[i] = B[p2--];
                } else{
                    B[i] = A[p1--];
                }
            } else if(p1 >= 0){
                B[i] = A[p1--];
            }
        }
    }
}
