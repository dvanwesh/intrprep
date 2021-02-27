package com.aoc.twenty20;

import com.aoc.common.Challenge;
import com.aoc.common.Direction;

import java.awt.*;

public class Day12 extends Challenge {
    protected Day12() {
        super(12, 2020);
    }

    public static void main(String[] args) {
        new Day12().executeTasks();
    }

    @Override
    protected Object task1() {
        Point location = new Point(0, 0);
        Direction currDir = Direction.EAST;
        for (String move : inputList) {
            int dist = Integer.parseInt(move.substring(1));
            char dir = move.charAt(0);
            switch (dir) {
                case 'F':
                    currDir.move(location, dist);
                    break;
                case 'E':
                case 'W':
                case 'S':
                case 'N':
                    currDir.move(location, dir, dist, false);
                    break;
                case 'R':
                case 'L':
                    currDir = currDir.turn(dir == 'R', dist);
                    break;
            }
        }
        return Math.abs(location.x) + Math.abs(location.y);
    }

    @Override
    protected Object task2() {
        Point location = new Point(0, 0);
        Point wayPoint = new Point(10, 1);
        Direction currHzt = Direction.EAST, currVert = Direction.NORTH;
        for (String move : inputList) {
            int dist = Integer.parseInt(move.substring(1));
            char dir = move.charAt(0);
            switch (dir) {
                case 'F':
                    currHzt = currHzt.move(location, Math.abs(wayPoint.x * dist));
                    currVert = currVert.move(location, Math.abs(wayPoint.y * dist));
                    break;
                case 'E':
                case 'W':
                    currHzt = currHzt.move(wayPoint, dir, dist, false);
                    break;
                case 'S':
                case 'N':
                    currVert = currVert.move(wayPoint, dir, dist, false);
                    break;
                case 'R':
                case 'L':
                    currHzt = currHzt.turn(dir == 'R', dist);
                    currVert = currVert.turn(dir == 'R', dist);
                    if (currHzt == Direction.SOUTH || currHzt == Direction.NORTH) {
                        int tmpX = wayPoint.x;
                        wayPoint.x = wayPoint.y;
                        wayPoint.y = tmpX;
                        Direction tmp = currHzt;
                        currHzt = currVert;
                        currVert = tmp;
                    }
                    if ((currHzt == Direction.WEST && wayPoint.x >= 0) || (currHzt == Direction.EAST && wayPoint.x < 0)) {
                        wayPoint.x = -wayPoint.x;
                    }
                    if ((currVert == Direction.NORTH && wayPoint.y < 0) || (currVert == Direction.SOUTH && wayPoint.y >= 0)) {
                        wayPoint.y = -wayPoint.y;
                    }
                    break;
            }
        }
        return Math.abs(location.x) + Math.abs(location.y);
    }
}
