package org.twitter.timeline.model;

import lombok.ToString;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class Tweet {
    @ToString.Exclude
    long id;
    ZonedDateTime dateCreated;
    String text;
}
