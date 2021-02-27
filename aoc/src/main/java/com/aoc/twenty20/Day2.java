package com.aoc.twenty20;

import com.aoc.common.Challenge;

/**
 * See <a href="https://adventofcode.com/2020/day/2">Day 2 question</a>
 */
public class Day2 extends Challenge {
    int min, max;
    char match;
    String password;
    String policy;

    public Day2() {
        super(2, 2020);
    }

    public static void main(String[] args) {
        new Day2().executeTasks();
    }

    @Override
    protected Object task1() {
        return inputList.stream().filter(this::isValidForPolicy1).count();
    }

    @Override
    protected Object task2() {
        return inputList.stream().filter(this::isValidForPolicy2).count();
    }

    private void loadPolicy(String input) {
        policy = input;
        String[] str = input.split(":");
        password = str[1].trim();
        String policy = str[0];
        match = policy.split(" ")[1].charAt(0);
        String range = policy.split(" ")[0];
        min = Integer.parseInt(range.split("-")[0]);
        max = Integer.parseInt(range.split("-")[1]);
    }

    public boolean isValidForPolicy2(String policy) {
        loadPolicy(policy);
        return (password.length() >= min && password.charAt(min - 1) == match) ^
            (password.length() >= max && password.charAt(max - 1) == match);
    }

    public boolean isValidForPolicy1(String policy) {
        loadPolicy(policy);
        long count = password.chars().filter(ch -> ch == match).count();
        return count >= min && count <= max;
    }

}
