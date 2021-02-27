package leetcode.amzon.restaurant;

public class Item {
    private final String name;
    private double cost;
    private int orderCount;

    public Item(String name, double cost) {
        orderCount = 0;
        this.name = name;
    }

    public int getNumberOfItemsSold() {
        orderCount++;
        return orderCount;
    }

    public String getName() {
        return name;
    }
}
