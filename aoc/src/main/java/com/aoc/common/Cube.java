package com.aoc.common;

import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


//@Value
@Data
public class Cube {
    private final long x;
    private final long y;
    private final long z;
    //private boolean active;
    public Cube[] getNeighbors() {
        List<List<Integer>> combinations = new ArrayList<>();
        backtrack(-1, 1, 3, combinations, new ArrayList<>());
        return combinations.stream().map(l -> new Cube(x + l.get(0), y + l.get(1), z + l.get(2)))
            .filter(cube -> !cube.equals(this)).toArray(Cube[]::new);
    }

    private void backtrack(int start, int n, int k, List<List<Integer>> res, List<Integer> curr) {
        if (curr.size() == k) {
            res.add(new ArrayList<>(curr));
            return;
        }
        for (int i = start; i <= n; i++) {
            curr.add(i);
            backtrack(start, n, k, res, curr);
            curr.remove(curr.size() - 1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return x == cube.x &&
            y == cube.y &&
            z == cube.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public static int compareByCoordinates(Cube c1, Cube c2){
        return (int) ((c1.getX()+c1.getY()+c1.getZ())-(c2.getX()+c2.getY()+c2.getZ()));
    }
    public static void main(String[] args) {
        Cube[] neighbors = new Cube(2l, 3l, 1l).getNeighbors();
        System.out.println(Arrays.toString(neighbors));
    }
}
