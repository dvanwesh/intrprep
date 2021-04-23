package leetcode.microsoft.mar24twentyone;

import java.util.Arrays;

// you can also use imports, for example:
// import java.util.*;
/*  17 16 15 14 13
    18 5  4  3  12
    19 6  1  2  11
    20 7  8  9  10
    21 22 23 24 25
    i, j=n/2
    {{0, 0}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}}
    i,j i,j+1, i-1,j+1, i-1,j i-1,j-1, i,j-1, i+1,j-1, i+1,j i+1,j+1, i+1, j+2
*/
public class Populate2DArray {

    public static void main(String [] args) {
        // you can write to stdout for debugging purposes, e.g.
        int[][] ar = buildGrid(3);
        for(int[] a: ar){
            System.out.println(Arrays.toString(a));
        }
    }

    private static int[][] buildGrid(int n){
        if(n == 1){
            return new int[][]{{1}};
        }
        int[][] dir = new int[][]{{0, 0}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}};
        int[][] res = new int[n][n];
        //int i = n/2, j = n/2;
        int c = 1;
        for(int r = 0; r < n/2; r++){
            //for(int i = )
        }
        while(c < n*n){
            //for(int k = 0; k < n; k++){
                for(int[] dr : dir){
                    //int newI = dr[0]+i;
                    //int newJ = dr[1]+j;
                    //res[newI][newJ] = c++;
                }
            //}
        }
        return res;
    }
}

