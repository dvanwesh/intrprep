package org.twitter.timeline.service;


import lombok.Data;
import lombok.extern.java.Log;
import org.twitter.timeline.model.Tweet;
import org.twitter.timeline.model.User;
import org.twitter.timeline.util.Pair;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

/**
 * this class is used to generate the timeline of a user with O(F*log(k)+N*log(k)) time complexity
 * F: Number of followed users, N: number of Tweets of K followed users, k is 10 in this case.
 */
@Data
@Log
public class TimelineService {
    private static final FileReaderService fileReaderService = new FileReaderService();
    // The userId mappings in follows.csv that dont have a record in users.csv are initialized with some default missing data
    private static final String NO_NAME = "MISSING_NAME";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static List<User> users;
    private Map<Long, User> userMap;
    private User user;

    public TimelineService(long userId) throws NoSuchFieldException {
        // load users
        users = fileReaderService.getUsers();
        // Map userId->User for easy retrieval for later part
        userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
        if (!userMap.containsKey(userId)) {
            throw new NoSuchFieldException(String.format("No Such User Exists with id %s", userId));
        }
        this.user = userMap.get(userId);
        // load followers
        stream(fileReaderService.getRelationShips()).forEach(col -> userMap.get(col[0])
            .startFollowing(userMap.getOrDefault(col[1], new User(col[1], NO_NAME, NO_NAME))));
        // load tweets
        stream(fileReaderService.readTweets()).forEach(str ->
            userMap.get(Long.parseLong(str[1])).postTweet(new Tweet(Long.parseLong(str[0]), getDateCreated(str[2]), str[3])));
    }

    public List<Pair<Tweet, User>> generateTimeline(int k) {
        Queue<User> friendsWithRecentTweets = new PriorityQueue<>(Comparator.comparing(User::getRecentTweetTime));
        /** find K friends of the user with most recent tweet times. Time: F*log(k) */
        user.getFollowedUsers().stream().filter(f -> f.getTweets().size() > 0).forEach(author -> {
            friendsWithRecentTweets.offer(author);
            if (friendsWithRecentTweets.size() > k) {
                friendsWithRecentTweets.poll();
            }
        });
        /** Iterate over all the tweets of K recently tweeted friends and find K recent tweets. Time N*log(k) */
        Queue<Pair<Tweet, User>> minHeap = new PriorityQueue<>(Comparator.comparing(a -> a.getKey().getDateCreated()));
        friendsWithRecentTweets.stream().forEach(author ->
            author.getTweets().stream().forEach(tweet -> {
                minHeap.offer(new Pair(tweet, author));
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }));
        return minHeap.stream().sorted((a, b) -> b.getKey().getDateCreated().compareTo(a.getKey().getDateCreated())).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        try {
            TimelineService timelineService = new TimelineService(Long.parseLong("1234"));
            List<Pair<Tweet, User>> tweets = timelineService.generateTimeline(10);
            System.out.println(String.format("-------------------------%s Timeline-------------------------", timelineService.getUser().getScreenName()));
            tweets.stream().forEach(p -> System.out.println(String.format("%s [%s] %s",
                p.getValue().getScreenName(), p.getKey().getDateCreated().format(dateTimeFormatter),
                p.getKey().getText())));
        } catch (NoSuchFieldException e) {
            log.severe(e.getMessage());
        }
    }

    /**
     * converts uploaded unix timestamp to Date
     *
     * @param unixTime
     * @return
     */
    protected ZonedDateTime getDateCreated(String unixTime) {
        Instant instant = Instant.ofEpochSecond(Long.parseLong(unixTime));
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
