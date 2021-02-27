package com.aoc.twenty20;

import com.aoc.common.Challenge;
import com.aoc.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day7 extends Challenge {
    Map<String, List<Pair<String, Integer>>> map = new HashMap<>();
    Set<String> goldParents = new HashSet<>();

    public Day7() {
        super(7, 2020);
        setRules();
    }

    public static void main(String[] args) {
        new Day7().executeTasks();
    }

    @Override
    protected Object task1() {
        for (String parent : map.keySet()) {
            if (!parent.equals("shiny gold")) {
                dfs(parent, new HashSet<>(), "shiny gold");
            }
        }
        return goldParents.size();
    }

    private boolean dfs(String parent, Set<String> seen, String target) {
        if (parent.equals(target)) {
            return true;
        }
        if (goldParents.contains(parent)) {
            return true;
        }
        for (Pair<String, Integer> pair : map.get(parent)) {
            if (pair != null && !seen.contains(pair.getFirst())) {
                seen.add(pair.getFirst());
                if (dfs(pair.getFirst(), seen, target)) {
                    goldParents.add(parent);
                    return true;
                }
            }
        }
        return false;
    }

    private void setRules() {
        for (String rule : inputList) {
            String parentStr = rule.split("contain")[0];
            String childField = rule.split("contain")[1];
            String[] childArry = childField.split(",");
            for (String childStr : childArry) {
                String parent = parentStr.replace("bags", "").trim();
                if (!childStr.contains("no other bags")) {
                    Pair<String, Integer> child = fetchChildren(childStr);
                    map.computeIfAbsent(parent, a -> new ArrayList<>()).add(child);
                } else {
                    map.put(parent, new ArrayList<>());
                }
            }
        }
    }

    private Pair<String, Integer> fetchChildren(String childStr) {
        String[] parts = childStr.trim().split(" ", 2);
        int quantity = Integer.parseInt(parts[0]);
        return new Pair<>(parts[1]
            .replaceAll("bags?\\.?", "").trim(),
            quantity);
    }

    @Override
    protected Object task2() {
        return calculateBagsCount("shiny gold", new HashMap<>());
    }

    private int calculateBagsCount(String bag, Map<String, Integer> seen) {
        if (seen.containsKey(bag)) {
            return seen.get(bag);
        }
        int count = 0;
        for (Pair<String, Integer> pair : map.get(bag)) {
            int quantity = pair.getSecond();
            count += quantity + quantity * calculateBagsCount(pair.getFirst(), seen);
        }
        seen.put(bag, count);
        return count;
    }
}
