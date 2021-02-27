package org.twitter.timeline.service;

import lombok.extern.java.Log;
import org.twitter.timeline.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * this service class is used to load all the input data
 */
@Log
public class FileReaderService {
    private final String FILE_NAME = "<name>";
    private final String USERS = "users.csv";
    private final String FOLLOWS = "follows.csv";
    private final String TWEETS = "tweets.csv";
    private final String FILE_PATH = "src/main/resources/" + FILE_NAME;

    private List<String> fetchInputAsList(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            return stream.collect(Collectors.toList());
        } catch (IOException ex) {
            log.severe(String.format("Error reading file %s Exception: ", FILE_NAME, ex));
        }
        return null;
    }

    public List<User> getUsers() {
        return fetchInputAsList(FILE_PATH.replaceAll(FILE_NAME, USERS)).stream()
            .map(line -> line.split(",", 3))
            .map(fields -> new User(Long.parseLong(fields[0].trim()), fields[1].trim(), fields[2].trim()))
            .collect(Collectors.toList());
    }

    public long[][] getRelationShips() {
        return fetchInputAsList(FILE_PATH.replaceAll(FILE_NAME, FOLLOWS)).stream()
            .map(line -> line.split(","))
            .map(fields -> new long[]{Long.parseLong(fields[0].trim()), Long.parseLong(fields[1].trim())})
            .toArray(long[][]::new);
    }

    public String[][] readTweets() {
        return fetchInputAsList(FILE_PATH.replaceAll(FILE_NAME, TWEETS)).stream()
            .map(line -> line.split(",", 4))
            .map(fields -> new String[]{fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim()})
            .toArray(String[][]::new);
    }
}
