package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * See <a href="https://adventofcode.com/2020/day/6">Day 6 question</a>
 */
public class Day6 extends Challenge {
    protected Day6() {
        super(6, 2020);
    }

    public static void main(String[] args) {
        new Day6().executeTasks();
    }

    @Override
    protected Object task1() {
        String input = getResourceAsString();
        String[] groupResponses = input.split("\n\n");
        return Arrays.stream(groupResponses).map(group -> group.replace("\n", "")).
            mapToLong(group -> group.chars().distinct().count()).sum();
    }

    @Override
    protected Object task2() {
        String input = getResourceAsString();
        String[] groupResponses = input.split("\n\n");
        return Arrays.stream(groupResponses).mapToLong(this::yesCount).sum();
    }

    private long yesCount(String groupResponse) {
        Map<Character, Integer> map = new HashMap<>();
        int usrCount = groupResponse.split("\n").length;
        for (String usrResponse : groupResponse.split("\n")) {
            for (char ch : usrResponse.toCharArray()) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
        }
        return map.values().stream().filter(repeats -> repeats == usrCount).count();
    }
}
