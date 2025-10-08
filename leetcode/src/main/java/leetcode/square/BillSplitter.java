package leetcode.square;

import java.util.*;


/*
April 11th 2024

Krabby Patty, ¥1600 - Jack Dorsey
Salty Sea Dog, ¥1200 - Venkata
Kelp Rings, ¥350 - Jack Dorsey


output
------
Jack Dorsey, ¥1950
- Krabby Patty
- Kelp Rings
Venkata, ¥1200
- Salty Sea Dog
*/


/*
Jack: 20
Venkata: 10
-- subtotal: 30


tax: 3
tip: 3


Total(Jack): 24
Total(Venkata): 12
*/


/*
Krabby Patty, ¥1600 - Jack Dorsey
Salty Sea Dog, ¥1200 - Venkata
Kelp Rings, ¥350 - Jack Dorsey
Cream Filled Coral, ¥1450 - Patrick Star
Buttered Barnacles, ¥400 - Patrick Star


Tax: ¥400
Tip: ¥900
*/


public class BillSplitter {
    public static void main(String[] args) {
        BillSplitter billSplitter = new BillSplitter();
        billSplitter.recordLineItem("Jack Dorsey", "Krabby Patty", 1000);
        billSplitter.recordLineItem("Venkata", "Salty Sea Dog", 1000);
        billSplitter.recordLineItem("Jack Dorsey", "Kelp Rings", 1000);
        billSplitter.setTax(200);
        billSplitter.setTip(400);


        List<BillSplit> result = billSplitter.getBillSplits();
        for (BillSplit split : result) {
            System.out.println(split.personName + ", ¥" + split.totalPrice);
            for (String item : split.items) {
                System.out.println("- " + item);
            }
        }
    }

    Map<String, BillSplit> billSplitMap = new HashMap<>();
    int tip;
    int tax;
    int totalItemPrice;


    public void recordLineItem(String personName, String item, int price) {


        BillSplit billSplit = billSplitMap.computeIfAbsent(personName, p -> new BillSplit(personName));
        billSplit.items.add(item);
        billSplit.totalPrice += price;
        totalItemPrice += price;
    }


    public void setTip(int tip) {
        this.tip = tip;
    }


    public void setTax(int tax) {
        this.tax = tax;
    }


    public List<BillSplit> getBillSplits() {
        billSplitMap.forEach((key, value) -> {
            double share = ((double) value.totalPrice / totalItemPrice);
            value.totalPrice = (int) (share * (tip + tax)) + value.totalPrice;
        });
        return new ArrayList<>(billSplitMap.values());
    }
}


class BillSplit {
    String personName;
    List<String> items;
    int totalPrice;

    public BillSplit(String personName) {
        this.personName = personName;
        this.items = new ArrayList<>();
    }
}

