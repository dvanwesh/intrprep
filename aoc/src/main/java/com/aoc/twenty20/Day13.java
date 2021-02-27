package com.aoc.twenty20;

import com.aoc.common.Challenge;
import com.aoc.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class Day13 extends Challenge {
    int[] busIds;
    long earliestTime;

    public Day13() {
        super(13, 2020);
        earliestTime = Long.parseLong(inputList.get(0));
        busIds = Arrays.stream(inputList.get(1).split(",")).filter(s -> !s.equals("x")).mapToInt(Integer::parseInt).toArray();
    }

    public static void main(String[] args) {
        new Day13().executeTasks();
    }

    @Override
    protected Object task1() {
        long curr = earliestTime;
        while (curr > 0) {
            for (int bus : busIds) {
                if (curr % bus == 0) {
                    return bus * (curr - earliestTime);
                }
            }
            curr++;
        }
        return -1;
    }

    @Override
    protected Object task2() {
        long[] nums = Arrays.stream(inputList.get(1).split(",")).map(s -> s = !s.equals("x") ? s : "0").mapToLong(Long::parseLong).toArray();
        List<Pair<Integer, Long>> mods = range(0, nums.length).filter(i -> nums[i] != 0).mapToObj(i -> new Pair<Integer, Long>(i, nums[i])).collect(Collectors.toList());
        long product = mods.stream().mapToLong(x -> x.getSecond()).reduce(1, Math::multiplyExact);
        long sum = mods.stream().mapToLong(a -> a.getFirst() * (product / a.getSecond()) * inverseModulo(product / a.getSecond(), a.getSecond())).sum();
        return product - sum % product;
    }

    // Extended Euclidean Algorithm / Inverse Modulo
    long inverseModulo(long x, long y) {
        if (x != 0) {
            long modulo = y % x;
            return modulo == 0 ? 1 : y - inverseModulo(modulo, x) * y / x;
        }
        return 0;
    }
}
