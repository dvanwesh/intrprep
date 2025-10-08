package leetcode.tecton;

import java.util.*;
import java.util.stream.Collectors;

class Meeting {
    int start;
    int end;

    public Meeting(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}

public class MeetingRoomScheduler {

    public static List<List<Meeting>> scheduleMeetings(List<Meeting> meetings) {
        // Sort meetings by start time
        meetings.sort(Comparator.comparingInt(a -> a.start));

        // Priority queue to keep track of rooms and earliest ending meetings
        PriorityQueue<Integer> availableRooms = new PriorityQueue<>();

        List<List<Meeting>> schedule = new ArrayList<>();
        availableRooms.offer(meetings.get(0).end); // Add the first meeting to a room
        schedule.add(new ArrayList<>(List.of(meetings.get(0))));

        for (int i = 1; i < meetings.size(); i++) {
            Meeting currentMeeting = meetings.get(i);

            // Check if the current meeting can use an existing room
            if (currentMeeting.start >= availableRooms.peek()) {
                availableRooms.poll(); // Free up the room
            }

            // Assign meeting to a room (existing or new)
            int roomIndex = availableRooms.size();
            availableRooms.offer(currentMeeting.end);

            if (schedule.size() <= roomIndex) {
                schedule.add(new ArrayList<>()); // Create a new room's schedule
            }
            schedule.get(roomIndex).add(currentMeeting);
        }

        return schedule;
    }

    public static void main(String[] args) {
        int[][] appts = new int[][]{{9, 18}, {10, 12}, {10, 13}, {11, 13}, {14, 17}, {19, 20}};

        appts = new int[][]{{10, 12}, {10, 12}, {10, 12}, {10, 12}, {14, 17}, {19, 20}};
        List<Meeting> appointments = Arrays.stream(appts).map(a -> new Meeting(a[0], a[1])).collect(Collectors.toList());

        List<List<Meeting>> meetings = scheduleMeetings(appointments);
        for (List<Meeting> meetingsRoom : meetings) {
            for (Meeting meeting : meetingsRoom) {
                System.out.println(meeting);
            }
            System.out.println("---------------");
        }

    }
}
