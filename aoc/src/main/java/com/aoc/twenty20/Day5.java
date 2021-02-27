package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * See <a href="https://adventofcode.com/2020/day/5">Day 5 question</a>
 */
public class Day5 extends Challenge {
    protected Day5() {
        super(5, 2020);
    }

    public static void main(String[] args) {
        new Day5().executeTasks();
    }

    @Override
    protected Object task1() {
        return inputList.stream().mapToInt(this::calculateSeatId).max().getAsInt();
    }

    @Override
    protected Object task2() {
        Set<Integer> seats = inputList.stream().map(this::calculateSeatId).collect(Collectors.toSet());
        int firstOccupied = seats.stream().min(Comparator.naturalOrder()).get();
        int lastOccupied = seats.stream().max(Comparator.naturalOrder()).get();
        for (int seatId = firstOccupied; seatId <= lastOccupied; seatId++) {
            if (!seats.contains(seatId)) {
                return seatId;
            }
        }
        return -1;
    }

    private int calculateSeatId(String boardingPass) {
        int lo = 0, hi = 127, l = 0, h = 7, row = 0, seat = 0, i = 0;
        for (char ch : boardingPass.toCharArray()) {
            i++;
            if (ch == 'F') {
                hi = lo + (hi - lo) / 2;
                if (i == 7) {
                    row = hi;
                }
            } else if (ch == 'B') {
                lo = hi - (hi - lo) / 2;
                if (i == 7) {
                    row = lo;
                }
            } else if (ch == 'L') {
                h = l + (h - l) / 2;
                if (i == boardingPass.length()) {
                    seat = h;
                }
            } else if (ch == 'R') {
                l = h - (h - l) / 2;
                if (i == boardingPass.length()) {
                    seat = l;
                }
            }
        }
        return 8 * row + seat;
    }
}
