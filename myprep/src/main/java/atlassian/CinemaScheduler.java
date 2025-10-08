package atlassian;

import java.util.*;

/**
 *
 * help solve this in java
 *
 * (1 hour, Code Design):
 * You are a Manager of the cinema hall,
 * 1. Have to screen the movies, if the new movie can be added to the existng schedule w/o removing existing movie.
 * 2. cinema opens at 10 AM and closes at 11 PM - single day schedule
 * 3. movies start & end time is the number of mins from midnight.
 * 4. boolean canSchedule(Movie movie, MovieSchedule schedule);
 * 5. takes the movie (duration of the movie) as input and tell if it can be scheudled.
 *
 * {"movies": [
 *     {
 *       "title": "Lord Of The Rings",
 *       "durationInMinutes":  120
 *     },
 *     {
 *       "title": "Back To The Future",
 *       "durationInMinutes":  90
 *     }
 *    ],
 * "screenings": [
 *     {
 *        "title": "Lord Of The Rings",
 *        "startTime": 660
 *      },
 *      {
 *        "title": "Lord Of The Rings",
 *        "startTime": 840
 *      },
 *      {
 *        "title": "Back To The Future",
 *        "startTime": 1020
 *      },
 *      {
 *        "title": "Lord Of The Rings",
 *        "startTime": 1200
 *      }
 *    ]
 *  }
 * **/
class Movie {
    final String title;
    final int durationInMinutes;
    Movie(String title, int durationInMinutes) {
        this.title = title;
        this.durationInMinutes = durationInMinutes;
    }
}

class Screening {
    final String title;
    final int startTime; // minutes from midnight
    Screening(String title, int startTime) {
        this.title = title;
        this.startTime = startTime;
    }
}

class MovieSchedule {
    final List<Movie> movies;
    final List<Screening> screenings;
    // cinema day bounds
    static final int OPEN = 10 * 60;   // 600 = 10:00
    static final int CLOSE = 23 * 60;  // 1380 = 23:00

    MovieSchedule(List<Movie> movies, List<Screening> screenings) {
        this.movies = movies;
        this.screenings = screenings;
    }
}

public class CinemaScheduler {

    /** Core function:
     *  returns true if there exists any start time today where `movie` fits without overlapping existing screenings.
     */
    public static boolean canSchedule(Movie movie, MovieSchedule schedule) {
        final int OPEN = MovieSchedule.OPEN;
        final int CLOSE = MovieSchedule.CLOSE;

        if (movie.durationInMinutes <= 0) return false;
        if (movie.durationInMinutes > (CLOSE - OPEN)) return false;

        // Build a quick lookup of title -> duration
        Map<String, Integer> durationByTitle = new HashMap<>();
        for (Movie m : schedule.movies) {
            durationByTitle.put(m.title, m.durationInMinutes);
        }

        // Build [start, end) intervals for existing screenings, clipped to the day window
        List<int[]> intervals = new ArrayList<>();
        for (Screening s : schedule.screenings) {
            Integer d = durationByTitle.get(s.title);
            if (d == null) {
                throw new IllegalArgumentException("Unknown movie in screenings: " + s.title);
            }
            int start = Math.max(OPEN, s.startTime);
            int end = Math.min(CLOSE, s.startTime + d);
            if (start < end) {
                intervals.add(new int[]{start, end});
            }
        }

        // Sort by start time
        intervals.sort(Comparator.comparingInt(a -> a[0]));

        // Sweep through the day, merging overlaps on-the-fly; check gaps as we go.
        int cursor = OPEN; // earliest free minute we can use
        for (int[] iv : intervals) {
            int ivStart = iv[0];
            int ivEnd = iv[1];

            // Free gap before this interval?
            int free = ivStart - cursor;
            if (free >= movie.durationInMinutes) {
                return true;
            }

            // Consume this interval (merge overlaps by advancing cursor to max)
            cursor = Math.max(cursor, ivEnd);
            if (cursor >= CLOSE) break; // day fully consumed
        }

        // Gap after last interval until closing time?
        return (CLOSE - cursor) >= movie.durationInMinutes;
    }

    /** demo with the provided data */
    public static void main(String[] args) {
        List<Movie> movies = List.of(
                new Movie("Lord Of The Rings", 120),
                new Movie("Back To The Future", 90)
        );

        List<Screening> screenings = List.of(
                new Screening("Lord Of The Rings", 660),  // 11:00 - 13:00
                new Screening("Lord Of The Rings", 840),  // 14:00 - 16:00
                new Screening("Back To The Future", 1020),// 17:00 - 18:30
                new Screening("Lord Of The Rings", 1200)  // 20:00 - 22:00
        );

        MovieSchedule schedule = new MovieSchedule(movies, screenings);

        // Try to add another 120-min movie
        Movie newLotR = new Movie("Lord Of The Rings", 120);
        System.out.println("Can schedule 120-min movie? " + canSchedule(newLotR, schedule));

        // Try to add a shorter one
        Movie shortFilm = new Movie("Short", 45);
        System.out.println("Can schedule 45-min movie? " + canSchedule(shortFilm, schedule));
    }
}

