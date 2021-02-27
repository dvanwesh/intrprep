package com.aoc.twenty20;

import com.aoc.common.Challenge;
import com.aoc.util.Pair;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day8 extends Challenge {
    Pair<String, Integer>[] instructions;

    public Day8() {
        super(8, 2020);
        instructions = Arrays.stream(fetchInputAsArry()).map(s -> new Pair(s.split(" ")[0],
            Integer.parseInt(s.split(" ")[1]))).toArray(Pair[]::new);
    }

    public static void main(String[] args) {
        new Day8().executeTasks();
    }

    @Override
    protected Object task1() {
        return checkForLoopAndFetchAcc(instructions).getFirst();
    }

    @Override
    protected Object task2() {
        for (Pair<String, Integer> instruction : instructions) {
            String cmd = instruction.getFirst();
            if (cmd.equals("jmp") || cmd.equals("noc")) {
                instruction.first = cmd.equals("jmp") ? "noc" : "jmp";
                Pair<Integer, Boolean> res = checkForLoopAndFetchAcc(instructions);
                if (res.getSecond()) {
                    return res.getFirst();
                } else {
                    instruction.first = cmd;
                }
            }
        }
        return 0;
    }

    private Pair<Integer, Boolean> checkForLoopAndFetchAcc(Pair<String, Integer>[] instructions) {
        int accumulator = 0;
        int op = 0;
        Set<Integer> seen = new HashSet<>();
        while (op < instructions.length) {
            if (seen.contains(op)) {
                return new Pair<>(accumulator, false);
            }
            seen.add(op);
            if (instructions[op].getFirst().equals("acc")) {
                accumulator += instructions[op++].getSecond();
            } else if (instructions[op].getFirst().equals("jmp")) {
                op = op + instructions[op].getSecond();
            } else {
                op++;
            }
        }
        return new Pair<>(accumulator, true);
    }


}
