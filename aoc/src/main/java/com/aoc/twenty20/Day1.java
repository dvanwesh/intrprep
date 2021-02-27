package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * See <a href="https://adventofcode.com/2020/day/1">Day 1 question</a>
 */
public class Day1 extends Challenge {
    List<Integer> entries;

    protected Day1() {
        super(1, 2020);
        entries = inputList.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        new Day1().executeTasks();
    }

    @Override
    protected Object task1() {
        return productOfTwoEntries();
    }

    @Override
    protected Object task2() {
        return productOfThreeEntries();
    }

    private long productOfTwoEntries() {
        Set<Integer> seen = new HashSet<>();
        for (Integer entry : entries) {
            if (seen.contains(2020 - entry)) {
                return entry * (2020 - entry);
            }
            seen.add(entry);
        }
        return 0L;
    }

    private long productOfThreeEntries() {
        Collections.sort(entries);
        int i = 0;
        while (i < entries.size() - 2) {
            int j = i + 1;
            int k = entries.size() - 1;
            while (j < k) {
                if (entries.get(i) + entries.get(j) + entries.get(k) == 2020) {
                    return entries.get(i) * entries.get(j) * entries.get(k);
                }
                if (entries.get(i) + entries.get(j) + entries.get(k) < 2020) {
                    while (entries.get(j).equals(entries.get(++j))) {
                    }
                } else {
                    while (entries.get(k).equals(entries.get(--k))) {
                    }
                }
            }
            while (entries.get(i).equals(entries.get(++i))) {
            }
        }
        return 0L;
    }
}
