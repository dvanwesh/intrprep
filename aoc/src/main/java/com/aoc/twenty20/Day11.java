package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.ArrayList;
import java.util.List;

public class Day11 extends Challenge {
    int[][] dirs = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}, {1,1}, {-1,-1}, {1,-1},{-1,1}};
    public Day11() {
        super(11, 2020);
    }

    public static void main(String[] args) {
        new Day11().executeTasks();
    }
    @Override
    protected Object task1() {
        List<String> prev = new ArrayList<>(inputList);
        List<String> curr = rearrangeSeats(new ArrayList<>(prev), false);
        while(!prev.equals(curr)){
            prev = curr;
            curr = rearrangeSeats(new ArrayList<>(curr), false);
        }
        return curr.stream().mapToInt(s-> (int) s.chars().filter(ch->ch=='#').count()).sum();
    }

    private List<String> rearrangeSeats(ArrayList<String> grid, boolean isFirstSeen) {
        List<String> res = new ArrayList<>();
        for(int i=0; i<grid.size(); i++){
            StringBuilder curr = new StringBuilder();
            for(int j=0; j<grid.get(i).length(); j++){
                switch (grid.get(i).charAt(j)){
                    case '.' : curr.append('.');
                    break;
                    case 'L' : curr.append(getNeighbourCount(i, j, grid, isFirstSeen) == 0 ? '#' : 'L');
                    break;
                    case '#' : curr.append(getNeighbourCount(i, j, grid, isFirstSeen) >= (isFirstSeen ? 5 : 4) ? 'L' : '#');
                    break;
                }
            }
            res.add(curr.toString());
        }
        return res;
    }

    private int getNeighbourCount(int i, int j, ArrayList<String> grid, boolean isFirstSeen) {
        int c = 0;
        boolean onceMore = true;
        for(int[] dir: dirs){
            int x = i+dir[0], y = j+dir[1];
            while (onceMore && isValid(x,y, grid)){
                if(grid.get(x).charAt(y) == '#'){
                    c++; break;
                } else if(grid.get(x).charAt(y) == 'L'){
                    break;
                }
                onceMore = isFirstSeen;
                x += dir[0];
                y += dir[1];
            }
            onceMore = true;
        }
        return c;
    }

    @Override
    protected Object task2() {
        List<String> prev = new ArrayList<>(inputList);
        List<String> curr = rearrangeSeats(new ArrayList<>(prev), true);
        while(!prev.equals(curr)){
            prev = curr;
            curr = rearrangeSeats(new ArrayList<>(curr), true);
        }
        return curr.stream().mapToInt(s-> (int) s.chars().filter(ch->ch=='#').count()).sum();
    }

    private boolean isValid(int x, int y, ArrayList<String> grid) {
        return x >= 0 && x < grid.size() && y >= 0 && y < grid.get(0).length();
    }
}
