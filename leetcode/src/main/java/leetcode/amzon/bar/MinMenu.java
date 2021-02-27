package leetcode.amzon.bar;

import java.util.*;

public class MinMenu {
    Map<Integer, Set<Integer>> drinksMap = new HashMap<>();
    int[][] preferences;

    public int minDrinksToMemorize(int[][] preferences) {
        this.preferences = preferences;
        int numOfCustomers = preferences.length;
        // drink, set<customer>
        for (int customer = 0; customer < numOfCustomers; customer++) {
            int[] drinks = preferences[customer];
            for (int drink : drinks) {
                drinksMap.computeIfAbsent(drink, x -> new HashSet<>()).add(customer);
            }
        }
        int drink = drinksMap.keySet().stream().reduce((a, b) -> drinksMap.get(a).size() > drinksMap.get(b).size() ? a : b).get();
        return bfs(drink);
    }

    private int bfs(int drink) {
        int count = 1;
        Queue<Integer> drinks = new LinkedList<>();
        Set<Integer> seen = new HashSet<>();
        Set<Integer> customers = new HashSet<>();
        drinks.offer(drink);
        drinks.offer(null);
        while (!drinks.isEmpty()) {
            Integer d = drinks.poll();
            if (d == null) {
                drinks.offer(null);
                if (drinks.peek() == null) {
                    break;
                }
                count++;
                continue;
            }
            seen.add(d);
            customers.addAll(drinksMap.get(d));
            if (customers.size() == preferences.length) {
                return count;
            }
            for (int cust : drinksMap.getOrDefault(d, new HashSet<>())) {
                for (int preferredDrink : preferences[cust]) {
                    if (!seen.contains(preferredDrink)) {
                        drinks.offer(preferredDrink);
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        MinMenu minMenu = new MinMenu();
        int[][] pref = new int[][]{{0, 1, 3, 6}, {1, 4, 7}, {2, 4, 7, 5}, {3, 2, 5}, {5, 8}};
        System.out.println(minMenu.minDrinksToMemorize(pref));
    }
}
