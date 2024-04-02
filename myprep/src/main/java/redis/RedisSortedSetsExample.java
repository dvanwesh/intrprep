package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.List;
import java.util.Set;
public class RedisSortedSetsExample {

    public static void main(String[] args) {
        // Connect to Redis server
        Jedis jedis = new Jedis("localhost");

        // Define a user ID
        String userId = "user123";

        // Add timestamps of user's actions to sorted set
        long timestamp1 = System.currentTimeMillis();
        jedis.zadd(userId, timestamp1, Long.toString(timestamp1));

        // Add another action with a different timestamp
        long timestamp2 = System.currentTimeMillis();
        jedis.zadd(userId, timestamp2, Long.toString(timestamp2));

        // Retrieve timestamps within a range (window duration)
        long windowDuration = 1000; // 1 second
        long currentTime = System.currentTimeMillis();
        List<Tuple> timestamps = jedis.zrangeByScoreWithScores(userId, currentTime - windowDuration, currentTime);

        // Print timestamps
        System.out.println("Timestamps within window duration:");
        for (Tuple tuple : timestamps) {
            System.out.println(tuple.getElement() + " - " + tuple.getScore());
        }

        // Close connection
        jedis.close();
    }
}
