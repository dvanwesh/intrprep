package leetcode.robinhood.intr;

import java.util.PriorityQueue;
import java.util.Queue;

/*
* You have N types of securities available to buy and for each security i, each has a starting price Pi. Your friend predicts that the stock price will be Si for each associated security. But based on volatility of the market, you only want to buy up to Ai shares of security i.
Given M dollars to spend, calculate the maximum return you could potentially gain based on the predicted prices Si (and including any cash you have remaining). You can think about this as the final portfolio value according to the predicted prices.
Assume you are able to buy fractional amounts of shares.
    N = Number of securities
    Pi = Current Price
    Si = Expected Future Price
    Ai = Maximum units you are willing to purchase
    M = Dollars available to invest
--- Sample Input ---
current_prices: [15, 20]
predicted_prices: [30, 45]
max_number_of_units: [3, 3]
dollars_to_spend: 30

AAPL's current price is $15, predicted price is $30, and max units to buy is 3.
TSLA current price is $20, predicted price is $45, and max units to buy is 3.
We have $30 to spend.

---Sample Output---
Max portfolio value is $67.5.

    [execution time limit] 3 seconds (java)
    [input] array.float current_prices
    [input] array.float predicted_prices
    [input] array.integer max_number_of_units
    [input] integer dollars_to_spend
    [output] float
* */
public class MaxReturnSolution {
    public static void main(String[] args) {
        double[] current_prices = new double[]{15, 20};
        double[] predicted_prices = new double[]{30, 45};
        int[] max_number_of_units = new int[]{3, 3};
        MaxReturnSolution sol = new MaxReturnSolution();
        System.out.println(sol.portfolio_value_optimization(current_prices, predicted_prices, max_number_of_units, 30));
    }
    // nlog(n) time, space O(n)
    double portfolio_value_optimization(double[] current_prices, double[] predicted_prices, int[] max_number_of_units, int dollars_to_spend) {
        double res = 0.0;
        if(current_prices == null || current_prices.length == 0){
            return res;
        }
        Queue<Integer> pq = new PriorityQueue<>((i, j)->Double.compare(
            (predicted_prices[j]/current_prices[j]), (predicted_prices[i]/current_prices[i])));
        for(int i = 0; i < current_prices.length; i++){
            pq.offer(i);
        }
        while(!pq.isEmpty() && dollars_to_spend > 0){
            int i = pq.poll();
            double stocksToBuy = 0.0;
            //System.out.println("----->"+stocksToBuy+"->"+current_prices[i]+"-->"+dollars_to_spend+"->"+i);
            if(current_prices[i] >= predicted_prices[i]){
                res += dollars_to_spend;
                dollars_to_spend = 0;
                break;
            } else if(current_prices[i] * max_number_of_units[i] > dollars_to_spend){
                stocksToBuy = dollars_to_spend/(double)current_prices[i];
            } else{
                stocksToBuy = max_number_of_units[i];
            }
            res += stocksToBuy * predicted_prices[i];
            dollars_to_spend -= stocksToBuy * current_prices[i];
            //System.out.println(stocksToBuy+"->"+current_prices[i]+"-->"+dollars_to_spend+"->"+i);
        }
        return res;
    }
}
