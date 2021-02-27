package org.twitter.timeline.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
public class User {
    @ToString.Exclude
    private final long id;
    private final String screenName;
    @ToString.Exclude
    private final String fullDisplayName;
    @ToString.Exclude
    private List<User> followedUsers = new ArrayList<>();
    @ToString.Exclude
    private List<Tweet> tweets = new ArrayList<>();
    private ZonedDateTime recentTweetTime;

    public void startFollowing(User user) {
        followedUsers.add(user);
    }

    public void postTweet(Tweet tweet) {
        recentTweetTime = Objects.isNull(recentTweetTime) ? tweet.getDateCreated() :
            (tweet.getDateCreated().isAfter(recentTweetTime) ? tweet.getDateCreated() : recentTweetTime);
        tweets.add(tweet);
    }
}
