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
        for (int i = 20; i > 0; i--) {
            int k = rn.nextInt(100);
            System.out.print(k + ",");
            list.add(k);
        }
        System.out.println();
        for (int i = 0; i < 20; i++) {
            System.out.print(list.get(i) + ",");
        }
    }
}

class SqArrayList {
    private int[] ar;
    int currIdx;

    public SqArrayList() {
        currIdx = 0;
        ar = new int[10];
    }

    /*
      n
      resizing it with constant size: (n/K) (k+2K+3K+...n.K)=k n.n
      resizing by doubling size:O(1)) (k+(k+K)+(2k+2k)+(3k+3K)+....) = n
    */
    public void add(int n) {
        if (currIdx == ar.length) {
            ar = Arrays.copyOf(ar, ar.length * 2);
        }
        int insertIndex = Arrays.binarySearch(ar, 0, currIdx, n);
        if (insertIndex < 0) {
            insertIndex = -insertIndex - 1;
        }
        System.arraycopy(ar, insertIndex, ar, insertIndex + 1, currIdx - insertIndex);
        ar[insertIndex] = n;
        currIdx++;
    }

    public int get(int index) throws IndexOutOfBoundsException {
        if (index >= 0 && index < currIdx) {
            return ar[index];
        }
        throw new IndexOutOfBoundsException();
    }

}

