package leetcode.twitter;

import java.util.*;

/**
 * https://leetcode.com/discuss/interview-question/1045860/Twitter-Phone-interview-question
 *
 * We want to measure a metric called User Active Minutes (UAM). User active minutes for a given user
 * is defined as the count of the number of distinct minutes in which the user takes some action on Twitter.
 * Multiple actions in the same minute are only counted as one minute. We would like a histogram of the number
 * of users who spend X minutes on Twitter, for different values ​​of X, given 30 days of raw logs and
 * an interval size in minutes.
 *
 * The raw logs are in the format: [ user_id, epoch timestamp]. Each row represents an action a user took
 * on Twitter. The logs are ordered chronologically. Duplicates are possible.
 *
 * Write code to compute the histogram of UAMs across our user base.
 *
 * Example:
 * Raw logs
 *
 * """
 * [1, 1518290973]
 * [2, 1518291032]
 * [3, 1518291095]
 * [1, 1518291096]
 * [4, 1518291120]
 * [3, 1518291178]
 * [1, 1518291200]
 * [1, 1518291200]
 * """
 * Interval size
 * 2
 *
 * Resulting histogram
 * [2, 2]
 *
 * 2 users spend 0 -1 minutes on Twitter
 * 2 users spend 2-3 minutes on Twitter
 */
public class UserActiveMinutes {
    public static void main(String[] args) {
        long[][] data = new long[][]{{1, 1518290973},
                                     {2, 1518291032},
                                     {3, 1518291095},
                                     {1, 1518291096},
                                     {4, 1518291120},
                                     {3, 1518291178},
                                     {1, 1518291200},
                                     {1, 1518291200}};
        UserActiveMinutes userActiveMinutes = new UserActiveMinutes();
        System.out.println(Arrays.toString(userActiveMinutes.getHistogram(data, 2)));
    }

    /*
     * 5 - 66,126
     * 5 + 60 * x = 128
     * add only start of first 60 seconds to users timestamp.
     * */
    private int[] getHistogram(long[][] data, int k) {
        Map<Long, List<Long>> userTimes = new HashMap<>();
        for (long[] row : data) {
            if (userTimes.containsKey(row[0])) {
                List<Long> timeline = userTimes.get(row[0]);
                if (row[1] - timeline.get(timeline.size() - 1) > 60) {
                    long x = (row[1] - timeline.get(timeline.size() - 1)) / 60;
                    timeline.add(timeline.get(timeline.size() - 1) + 60 * x);
                }
            } else {
                userTimes.computeIfAbsent(row[0], a -> new ArrayList<>()).add(row[1]);
            }
        }
        Map<Integer, List<Long>> freqMap = new HashMap<>();
        userTimes.entrySet().stream().forEach(e -> freqMap.computeIfAbsent(e.getValue().size() / k, a -> new ArrayList<>()).add(e.getKey()));
        int hi = freqMap.keySet().stream().mapToInt(Integer::valueOf).max().getAsInt();
        int[] res = new int[hi + 1];
        freqMap.entrySet().stream().forEach(e -> {
            res[e.getKey()] = e.getValue().size();
        });
        return res;
    }
}
