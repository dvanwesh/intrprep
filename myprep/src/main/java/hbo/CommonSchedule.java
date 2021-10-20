package hbo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonSchedule {
	public static void main(String[] args) {
		List<Time> usr1 = Arrays.asList(new Time(900, 1200), new Time(1300, 1600), new Time(1700, 1800), new Time(2100, 2200));
		List<Time> usr2 = Arrays.asList(new Time(1000, 1100), new Time(1400, 1900), new Time(2000, 2300));
		System.out.println(getCommonSchedule(usr1, usr2));
		usr1 = Arrays.asList(new Time(900, 1200), new Time(1300, 1600), new Time(1700, 1800), new Time(2100, 2200));
		usr2 = Arrays.asList(new Time(1400, 1900));
		System.out.println(getCommonSchedule(usr1, usr2));
	}

	private static List<Time> getCommonSchedule(List<Time> usr1, List<Time> usr2) {
		List<Time> res = new ArrayList<>();
		// i - user1's counter, j - user2's counter
		int i = 0, j = 0;
		while (i < usr1.size() && j < usr2.size()) {
			if (usr1.get(i).end <= usr2.get(j).start) { // since user2's start time starts after user1's end time, you can skip user1's current schedule and increment user1's counter
				i++;
			} else if (usr2.get(j).end <= usr1.get(i).start) { // since user1's start time starts after user2's end time, you can skip user2's current schedule and increment user2's counter
				j++;
			} else { // when above two if conditions aren't true, we can conclude there is an intersection between schedules.
				long start = Math.max(usr1.get(i).start, usr2.get(j).start); // start time is max of user1's and user2's start time
				if (usr1.get(i).end <= usr2.get(j).end) {
					res.add(new Time(start, usr1.get(i++).end)); // if user1's end time ends before user2's end time, capture intersection and increment user1's counter
				} else {
					res.add(new Time(start, usr2.get(j++).end));
				}
			}
		}
		return res;
	}

	static class Time {
		long start;
		long end;

		public Time(long start, long end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public String toString() {
			return "Time{" +
					"start=" + start +
					", end=" + end +
					'}';
		}
	}
}
