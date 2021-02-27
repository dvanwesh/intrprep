package datastructures.twopointer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * https://www.interviewbit.com/problems/max-continuous-series-of-1s/
 * You are given with an array of 1s and 0s. And you are given with an integer M, which signifies number of flips allowed.
 * Find the position of zeros which when flipped will produce maximum continuous series of 1s
 * <p>
 * example: Input :
 * Array = {1 1 0 1 1 0 0 1 1 1 }
 * M = 1
 * <p>
 * Output :
 * [0, 1, 2, 3, 4]
 */
public class MaxContinuousSeriesof1s {
    public static void main(String[] args) {
        TestType t = TestType.SUBSCRIPTION_CANCELED;
        System.out.println(t.toString());
        ArrayList<Integer> res = maxone(new ArrayList<>(Arrays.asList(1, 1, 0, 1, 1, 0, 0, 1, 1, 1)), 2);
        for (int i : res) {
            System.out.print(i + ",");
        }
    }

    public static ArrayList<Integer> maxone(ArrayList<Integer> A, int B) {
        int lw = 0, rw = 0, leftIndex = 0, bestWindow = -1;
        int zeroCount = 0;
        while (rw < A.size()) {
            if (zeroCount <= B) {
                if (A.get(rw) == 0) {
                    zeroCount++;
                }
                rw++;
            }
            if (zeroCount > B) {
                if (A.get(lw) == 0) {
                    zeroCount--;
                }
                lw++;
            }
            if (rw - lw > bestWindow) {
                bestWindow = rw - lw;
                leftIndex = lw;
            }
        }
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < bestWindow; i++) {
            res.add(leftIndex++);
        }
        return res;
    }

    public enum TestType {
        SUBSCRIPTION_RECOVERED(1),
        SUBSCRIPTION_RENEWED(2),
        SUBSCRIPTION_CANCELED(3),
        ;
        private final int value;

        TestType(int i) {
            this.value = i;
        }
    }
}
