package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * See <a href="https://adventofcode.com/2020/day/18">Day 18 question</a>
 */
public class Day18 extends Challenge {
    public Day18() {
        super(18, 2020);
    }

    public static void main(String[] args) {
        new Day18().executeTasks();
    }

    @Override
    protected Object task1() {
        return solveEquations(false);
    }

    @Override
    protected Object task2() {
        return solveEquations(true);
    }

    private Object solveEquations(boolean plusPrecedence) {
        List<Long> res = new ArrayList<>();
        Stack<Integer> indices = new Stack<>();
        for (String eq : inputList) {
            eq = eq.replaceAll(" ", "");
            String tmp = eq;
            while (eq.contains("(")) {
                int i = eq.indexOf('(');
                while (i < eq.length()) {
                    if (eq.charAt(i) == '(') {
                        indices.push(i);
                    } else if (eq.charAt(i) == ')') {
                        int braceStart = indices.pop();
                        long val = executeEquation(eq.substring(braceStart + 1, i), plusPrecedence);
                        tmp = eq.substring(0, braceStart) + val + eq.substring(i + 1);
                        break;
                    }
                    i++;
                }
                eq = tmp;
            }
            res.add(executeEquation(tmp, plusPrecedence));
        }
        return res.stream().reduce((a, b) -> (a + b)).get().longValue();
    }

    private long executeEquation(String eq, boolean plusPrecedence) {
        String numStr = eq.replaceAll("\\+", ",").replaceAll("\\*", ",");
        Queue<Long> nums = Arrays.stream(numStr.split(",")).map(Long::parseLong)
            .collect(Collectors.toCollection(LinkedList::new));
        Queue<Character> ops = eq.chars().mapToObj(ch -> (char) ch).filter(ch -> !Character.isDigit(ch))
            .collect(Collectors.toCollection(LinkedList::new));
        Stack<Long> mulStack = new Stack<>();
        long ans = nums.poll();
        while (!ops.isEmpty()) {
            switch (ops.poll()) {
                case '+':
                    ans += nums.poll();
                    break;
                case '*':
                    if (plusPrecedence) {
                        mulStack.push(ans);
                        ans = nums.poll();
                    } else {
                        ans *= nums.poll();
                    }
                    break;
            }
        }
        if (plusPrecedence) {
            while (!mulStack.isEmpty()) {
                ans *= mulStack.pop();
            }
        }
        return ans;
    }
}
