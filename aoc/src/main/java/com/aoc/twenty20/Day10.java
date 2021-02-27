package com.aoc.twenty20;


import com.aoc.common.Challenge;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import static org.apache.commons.lang3.ArrayUtils;

public class Day10 extends Challenge {
    long[] adapters;
    long buildInAdapterRating;
    public Day10() {
        super(10, 2020);
        adapters = Arrays.stream(inputArry).mapToLong(Integer::valueOf).toArray();
        Arrays.sort(adapters);
        adapters = ArrayUtils.add(adapters, adapters[adapters.length-1]+3);
        adapters = ArrayUtils.addFirst(adapters, 0L);
    }

    public static void main(String[] args) {
        new Day10().executeTasks();
    }
    @Override
    protected Object task1() {
        Map<Long, Long> map = new HashMap<>();
        long prev = 0;
        for(long adapter : adapters){
            map.put(adapter-prev, map.getOrDefault(adapter-prev, 0L)+1);
            prev = adapter;
        }
        return map.get(1L) * map.get(3L);
    }

    @Override
    protected Object task2() {
        Map<Long, Long> mem = new HashMap<>();
        mem.put(adapters[adapters.length - 1], 1L);
        for (int i = adapters.length - 2; i >= 0; i--) {
            for (int j = i + 1; j < adapters.length && j <= i + 3; j++) {
                if (adapters[j] - adapters[i] <= 3) {
                    mem.put(adapters[i], mem.getOrDefault(adapters[i], 0L)+mem.get(adapters[j]));
                }
            }
        }
        return mem.get(0L);
    }
}
