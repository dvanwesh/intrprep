package leetcode.amzon.restaurant;

import java.util.*;

public class Solution {
    List<Item> hotItems = new ArrayList<>();

    // nlog(k)
    public static void main(String[] args) {
        List<String> itemNames = new ArrayList<>(Arrays.asList("samosa", "bajji", "steak"));
    }

    public List<Item> getMostPopularItems(List<Item> items, int k) {
        List<Item> res = new ArrayList<>();
        if (items == null || items.size() == 0) {
            return res;
        }
        Queue<Item> pq = new PriorityQueue<>(
            (a, b) -> a.getNumberOfItemsSold() == b.getNumberOfItemsSold() ?
                a.getName().compareTo(b.getName()) : a.getNumberOfItemsSold() - b.getNumberOfItemsSold()
        );
        for (Item item : items) {
            pq.offer(item);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        while (!pq.isEmpty()) {
            res.add(pq.poll());
        }
        //
        Collections.reverse(res);
        hotItems = res;
        return res;
    }

    public List<Item> processItem(Item newItem) {
        if (newItem.getNumberOfItemsSold() < hotItems.get(hotItems.size() - 1).getNumberOfItemsSold()) {
            return hotItems;
        }
        hotItems.remove(hotItems.size() - 1);
        //int index =
        return hotItems;
    }
}
