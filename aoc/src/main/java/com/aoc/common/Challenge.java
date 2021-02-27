package com.aoc.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Challenge {
    private final int day;
    private final int year;
    private final String DEFAULT_DELIMITER = System.lineSeparator();
    private final String YEAR = "<year>";
    private final String DAY = "<day>";
    private final String FILE_PATH = "/Users/vdatla/git/intrprep/aoc/src/main/resources/" + YEAR + "/day" + DAY + "_entries";
    protected static List<String> inputList;
    protected static String[] inputArry;

    protected Challenge(int day, int year) {
        this.day = day;
        this.year = year;
        inputList = fetchInputAsList();
        inputArry = fetchInputAsArry();
    }

    protected List<String> fetchInputAsList() {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH.replaceAll(YEAR, String.valueOf(year)).
            replaceAll(DAY, String.valueOf(day))))) {
            return stream.collect(Collectors.toList());
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
        return null;
    }

    protected String[] fetchInputAsArry(String delimiter) {
        return getResourceAsString().split(delimiter);
    }

    protected String[] fetchInputAsArry() {
        return fetchInputAsArry(DEFAULT_DELIMITER);
    }

    protected String getResourceAsString() {
        try {
            return Files.readString(Paths.get(FILE_PATH.replaceAll(YEAR, String.valueOf(year)).
                replaceAll(DAY, String.valueOf(day))));
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
        return "";
    }

    protected abstract Object task1();

    protected abstract Object task2();

    protected void executeTasks() {
        System.out.println("Part1: " + task1());
        System.out.println("Part2: " + task2());
    }
}
