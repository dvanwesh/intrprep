package com.aoc.twenty20;

import com.aoc.common.Challenge;
import lombok.Data;
import lombok.Value;

import java.util.HashSet;
import java.util.Set;

public class Day17 extends Challenge{
    Set<Cube> cubes = new HashSet<>();
    Set<Pos> points = new HashSet<>();
    public Day17() {
        super(17, 2020);
        for(int i=0; i<inputList.size(); i++){
            for(int j=0; j<inputList.get(i).length(); j++){
                if(inputList.get(i).charAt(j)=='#'){
                    cubes.add(new Cube(i, j, 0));
                    points.add(new Pos(i, j, 0, 0));
                }
            }
        }
    }

    public static void main(String[] args) {
        new Day17().executeTasks();
    }
    @Override
    protected Object task1() {
        int i = 6;
        while (i-- > 0) {
            Set<Cube> newCubes = new HashSet<>();
            for(Cube c : cubes) {
                addNeighbors(newCubes, new HashSet(), c, true);
            }
            cubes = newCubes;
        }
        return cubes.size();
    }
    @Override
    protected Object task2() {
        int i = 6;
        while (i-- > 0) {
            Set<Pos> newPos = new HashSet<>();
            for (Pos p : points){
                addNeighbors(newPos, new HashSet(), p, true);
            }
            points = newPos;
        }
        return points.size();
    }
    public void addNeighbors(Set<Cube> newPos, Set<Cube> seen, Cube cube, boolean active){
        if(!seen.contains(cube)) {
            long neighbours = active ? -1 : 0;
            seen.add(cube);
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    for (int c = -1; c <= 1; c++) {
                        Cube x = new Cube(cube.getX() + a, cube.getY() + b, cube.getZ() + c);
                        if (cubes.contains(x)) {
                            neighbours++;
                        } else if(active) {
                            addNeighbors(newPos, seen, x, false);
                        }
                    }
                }
            }
            if((active && (neighbours == 2 || neighbours == 3)) ||
                (!active && neighbours == 3)){
                newPos.add(cube);
            }
        }
    }
    public void addNeighbors(Set<Pos> newPos, Set<Pos> seen, Pos p, boolean active){
        if(!seen.contains(p)) {
            long neighbours = active ? -1 : 0;
            seen.add(p);
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    for (int c = -1; c <= 1; c++) {
                        for (int d = -1; d <= 1; d++) {
                            Pos x = new Pos(p.getX() + a, p.getY() + b, p.getZ() + c, p.getW() + d);
                            if (points.contains(x)) {
                                neighbours++;
                            } else if(active) {
                                addNeighbors(newPos, seen, x, false);
                            }
                        }
                    }
                }
            }
            if((active && (neighbours == 2 || neighbours == 3)) ||
                (!active && neighbours == 3)){
                newPos.add(p);
            }
        }
    }
    @Data
    @Value
    class Pos{
        int x;
        int y;
        int z;
        int w;
    }
    @Data
    @Value
    class Cube{
        int x;
        int y;
        int z;
    }
}
