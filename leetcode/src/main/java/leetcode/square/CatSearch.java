package leetcode.square;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CatSearch {
    public static void main(String[] args) {
        CatSearch search = new CatSearch();
        String[] names = {"a", "b", "c", "d", "e", "f", "g", "h"};
        Integer[] height = {31, 24, 67, 12, 45, 21, 31, 12};
        Integer[] weight = {120, 124, 160, 130, 175, 120, 124, 142};
        List<Cat> cats = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            cats.add(new Cat(names[i], height[i], weight[i]));
        }
        System.out.println(search.getCatNames(cats, "WEIGHT", 120, "="));
    }

    private Predicate<Cat> getPredicate(String searchCriteria, String symbol,
                                        int val) {
        Predicate<Cat> heightGreaterThan = cat -> cat.getHeight() > val;
        Predicate<Cat> heightEqualTo = cat -> cat.getHeight() == val;
        Predicate<Cat> heightLessThan = cat -> cat.getHeight() < val;
        Predicate<Cat> weightGreaterThan = cat -> cat.getWeight() > val;
        Predicate<Cat> weightEqualTo = cat -> cat.getWeight() == val;
        Predicate<Cat> weightLessThan = cat -> cat.getWeight() < val;
        Map<String, Predicate<Cat>> heightMap = new HashMap<>();
        Map<String, Predicate<Cat>> weightMap = new HashMap<>();
        heightMap.put(">", heightGreaterThan);
        heightMap.put("=", heightEqualTo);
        heightMap.put("<", heightLessThan);
        weightMap.put(">", weightGreaterThan);
        weightMap.put("=", weightEqualTo);
        weightMap.put("<", weightLessThan);
        if (searchCriteria.equals("WEIGHT")) {
            return weightMap.get(symbol);
        } else {
            return heightMap.get(symbol);
        }
    }

    public List<Cat> getCatNames(List<Cat> cats, String searchCriteria,
                                 Integer searchValue, String symbol) {
        List<Cat> res = new ArrayList<>();
        /*for(Cat cat: cats){
        if(doesCriteriaMatch(cat, searchValue, symbol,
                           searchCriteria.equals("WEIGHT"))){
            res.add(cat);
        }
        }*/
        res = cats.stream().filter(getPredicate(searchCriteria, symbol, searchValue)).
            collect(Collectors.toList());
        if (searchCriteria.equals("WEIGHT")) {
            Collections.sort(res, (a, b) -> b.getWeight() == a.getWeight() ?
                b.getName().compareTo(a.getName()) :
                b.getWeight() - a.getWeight());
        } else {
            Collections.sort(res, (a, b) -> b.getHeight() == a.getHeight() ?
                b.getName().compareTo(a.getName()) :
                b.getHeight() - a.getHeight());
        }
        return res;
    }

    private boolean doesCriteriaMatch(Cat cat, int val, String symbol,
                                      boolean isWeight) {
        switch (symbol) {
            case "<":
                return (isWeight && cat.getWeight() < val) ||
                    (!isWeight && cat.getHeight() < val);
            case ">":
                return (isWeight && cat.getWeight() > val) ||
                    (!isWeight && cat.getHeight() > val);
            case "=":
                return (isWeight && cat.getWeight() == val) ||
                    (!isWeight && cat.getHeight() == val);
            default:
                return false;
        }
    }
}

class Cat {
    private final String name;
    private final int height;
    private final int weight;

    public Cat(String name, int h, int w) {
        this.name = name;
        height = h;
        weight = w;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Cat{name=" + name + ", height=" + height + ", weight=" + weight + "}";
    }
}
