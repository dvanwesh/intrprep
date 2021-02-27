package org.twitter.timeline.service;

import org.junit.Before;
import org.junit.Test;
import org.twitter.timeline.model.Tweet;
import org.twitter.timeline.model.User;
import org.twitter.timeline.util.Pair;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class TimelineServiceTest {
    private static TimelineService timelineService;

    @Before
    public void setUp() throws Exception {
        timelineService = new TimelineService(33580136l);
    }

    @Test
    public void testSpecialCharacters() {
        Tweet twt = timelineService.getUser().getTweets().stream()
            .filter(tweet -> tweet.getId()==608307220892254209l).findAny().get();
        assertTrue(twt.getText().contains("Выложили"));
    }

    @Test
    public void testTweetsOrder() {
        List<Pair<Tweet, User>> tweets = timelineService.generateTimeline(10);
        assertTrue(tweets.get(0).getKey().getDateCreated().isAfter(tweets.get(1).getKey().getDateCreated()));
    }
}
