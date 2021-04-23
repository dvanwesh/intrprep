package leetcode.square;

/*
 * Click `Run` to execute the snippet below!
 */

import java.io.*;
import java.util.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class CustomArrayList {
    public static void main(String[] args) {
        SqArrayList list = new SqArrayList();
        Random rn = new Random();
        for(int i = 10; i > 0; i--){
            int k = rn.nextInt(100);
            System.out.print(k+",");
            list.add(k);
        }
        System.out.println("-----------------------");
        for(int i = 0; i < 10; i++){
            System.out.print(list.get(i)+",");
        }
    }
}

class SqArrayList{
    private int[] ar;
    int currIdx;
    public SqArrayList(){
        currIdx = 0;
        ar = new int[10];
    }
    /*
      n
      resizing it with constant size: (n/K) (k+2K+3K+...n.K)=k n.n
      resizing by doubling size:O(1)) (k+(k+K)+(2k+2k)+(3k+3K)+....) = n
    */
    public void add(int n){

        if(currIdx >= ar.length){
            int[] tmp = ar;
            ar = new int[tmp.length*2];
            for(int k = 0; k < tmp.length; k++){
                ar[k] = tmp[k];
            }
        }
        rearrange(ar, n, currIdx);
        currIdx++;
    }

    public int get(int index) throws IndexOutOfBoundsException{
        if(index >= 0 && index < currIdx){
            return ar[index];
        }
        throw new IndexOutOfBoundsException();
    }

    private void rearrange(int[] ar, int n, int hi){
        if(currIdx == 0){
            ar[currIdx] = n;
            return;
        }
        for(int i = 0; i < hi; i++){
            if(ar[i] > n){
                rearrangeFromRight(ar, hi, i);
                ar[i] = n;
                return;
            }
        }
        ar[hi] = n;
    }

    private void rearrangeFromRight(int[] ar, int hi, int idx){
        while(hi > idx){
            ar[hi] = ar[--hi];
        }
    }
    private void swap(int[] ar, int i, int j){
        int tmp = ar[i];
        ar[i] = ar[j];
        ar[j] = tmp;
    }
}

