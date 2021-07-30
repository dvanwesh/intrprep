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
		int i = 0, j = 0;
		while (i < usr1.size() && j < usr2.size()) {
			if (usr1.get(i).end <= usr2.get(j).start) {
				i++;
			} else if (usr2.get(j).end <= usr1.get(i).start) {
				j++;
			} else {
				long start = Math.max(usr1.get(i).start, usr2.get(j).start);
				if (usr1.get(i).end <= usr2.get(j).end) {
					res.add(new Time(start, usr1.get(i++).end));
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
