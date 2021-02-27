package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 extends Challenge {
    Map<Integer, List<Integer>> memory = new HashMap<>();
    protected Day15() {
        super(15, 2020);
    }

    public static void main(String[] args) {
        new Day15().executeTasks();
    }
    @Override
    protected Object task1() {
        return runGame(2020);
    }
    @Override
    protected Object task2() {
        return runGame(30000000);
    }

    private int runGame(int n){
        int[] nums = new int[n];
        memory.clear();
        int i = loadInput(nums);
        while(i < n){
            if(memory.get(nums[i-1]).size() > 1){
                List<Integer> indexes = memory.get(nums[i-1]);
                if(indexes.size() > 1){
                    nums[i] = indexes.get(indexes.size()-1)-indexes.get(indexes.size()-2);
                } else{
                    nums[i] = i-indexes.get(0)-1;
                }
                memory.computeIfAbsent(nums[i], a->new ArrayList<>()).add(i);
            } else{
                memory.computeIfAbsent(0, a->new ArrayList<>()).add(i);
            }
            i++;
        }
        return nums[n-1];
    }
    private int loadInput(int[] nums) {
        int i = 0;
        for(String s: inputList.get(0).split(",")){
            nums[i] = Integer.parseInt(s);
            memory.computeIfAbsent(nums[i], a->new ArrayList<>()).add(i);
            i++;
        }
        return i;
    }
}
