package com.aoc.common;

import java.awt.*;
import java.util.Arrays;

public enum Direction {
    EAST('E'), WEST('W'), SOUTH('S'), NORTH('N'),
    ;
    char dir;

    Direction(char dir) {
        this.dir = dir;
    }

    public static Direction getByDir(char d) {
        return Arrays.stream(values()).filter(dir -> dir.dir == d).findAny().get();
    }

    public Direction move(Point location, int dist) {
        return move(location, dir, dist, true);
    }

    public Direction move(Point location, char direction, int dist, boolean isForward) {
        switch (direction) {
            case 'E':
                location.x += dist;
                return isForward ? this : location.x >= 0 ? EAST : WEST;
            case 'W':
                location.x -= dist;
                return isForward ? this : location.x < 0 ? WEST : EAST;
            case 'N':
                location.y += dist;
                return isForward ? this : location.y >= 0 ? NORTH : SOUTH;
            case 'S':
                location.y -= dist;
                return isForward ? this : location.y < 0 ? SOUTH : NORTH;
        }
        return getByDir(direction);
    }

    public Direction turn(boolean clockWise, int degree) {
        Direction newDir = this;
        while (degree > 0) {
            if (clockWise) {
                newDir = newDir == EAST ? SOUTH : (newDir == SOUTH ? WEST : (newDir == WEST ? NORTH : EAST));
            } else {
                newDir = newDir == EAST ? NORTH : (newDir == NORTH ? WEST : (newDir == WEST ? SOUTH : EAST));
            }
            degree -= 90;
        }
        return newDir;
    }
}
