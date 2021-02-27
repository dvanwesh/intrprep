package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day9 extends Challenge {
    long[] data;
    private static final int LENGTH = 25;

    protected Day9() {
        super(9, 2020);
        data = Stream.of(inputArry).mapToLong(Long::parseLong).toArray();
    }

    public static void main(String[] args) {
        new Day9().executeTasks();
    }

    @Override
    protected Object task1() {
        for (int i = LENGTH; i < data.length; i++) {
            if (!isSumOfTwoNumbers(Arrays.copyOfRange(data, (i - LENGTH), i), data[i])) {
                return data[i];
            }
        }
        return -1;
    }

    // time : nlog(n)
    private boolean isSumOfTwoNumbers(long[] ar, long target) {
        int i = 0, j = ar.length - 1;
        Arrays.sort(ar);
        while (i < j) {
            if (ar[i] + ar[j] == target) {
                return true;
            } else if (ar[i] + ar[j] < target) {
                i++;
            } else {
                j--;
            }
        }
        return false;
    }

    @Override
    protected Object task2() {
        long num = (long) task1();
        return calculateEncryptionWeakness(num);
    }

    // time: O(n)
    private long calculateEncryptionWeakness(long num) {
        long sum = 0;
        int lo = 0;
        for (int i = 0; i < data.length; i++) {
            sum += data[i];
            while (sum > num && lo <= i) {
                sum -= data[lo++];
            }
            if (sum == num && lo < i) {
                long[] sumArry = Arrays.copyOfRange(data, lo, (i + 1));
                return Arrays.stream(sumArry).max().getAsLong() + Arrays.stream(sumArry).min().getAsLong();
            }
        }
        return sum;
    }
}
