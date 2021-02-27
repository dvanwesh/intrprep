package datastructures.string;

// IMPORT LIBRARY PACKAGES NEEDED BY YOUR PROGRAM
// SOME CLASSES WITHIN A PACKAGE MAY BE RESTRICTED
// DEFINE ANY CLASS AND METHOD NEEDED

import java.util.*;

// CLASS BEGINS, THIS CLASS IS REQUIRED
class Solution {
    public static void main(String[] args) {
        //popularNToys(6, 2, Arrays.asList())
    }

    // METHOD SIGNATURE BEGINS, THIS METHOD IS REQUIRED
    public static ArrayList<String> popularNToys(int numToys,
                                                 int topToys,
                                                 List<String> toys,
                                                 int numQuotes,
                                                 List<String> quotes) {
        ArrayList<String> nPopularToys = new ArrayList<>();
        if (toys == null || toys.isEmpty()) {
            return nPopularToys;
        }
        Map<String, Integer> map = new HashMap<>();
        for (String toy : toys) {
            map.put(toy.toLowerCase(), 0);
        }
        for (String quote : quotes) {
            String[] words = quote.toLowerCase().split(" ");
            Set<String> toysInQuote = new HashSet<>();
            for (String word : words) {
                if (map.containsKey(word) && !toysInQuote.contains(word)) {
                    map.put(word, map.get(word) + 1);
                    toysInQuote.add(word);
                }
            }
        }
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
            (a, b) -> a.getValue() == b.getValue() ? b.getKey().compareTo(a.getKey()) :
                a.getValue() - b.getValue()
        );
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pq.offer(entry);
            if (pq.size() > topToys) {
                pq.poll();
            }
        }
        while (!pq.isEmpty()) {
            nPopularToys.add(pq.poll().getKey());
        }
        Collections.reverse(nPopularToys);
        return nPopularToys;

    }
    // METHOD SIGNATURE ENDS
}
