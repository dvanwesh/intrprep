package leetcode.square;

import java.util.Arrays;

/**
 * There's a nxn board, initialize it with a player facing a particular direction (N:^, S:, E:>, W:<) at an index given. Write move function with takes the following i/p :
 * F: step forward 1 in the current direction, L: turn left, R: turn right.
 * move("FLRFFR")
 * Print the output board.
 */
public class MoveInGrid {
    char[][] grid;
    char currDir;
    int currX;
    int currY;

    public static void main(String[] args) {
        for (char dir:"^<v>".toCharArray()) {
            MoveInGrid moveInGrid = new MoveInGrid(6, dir, 3, 3);
            moveInGrid.move("FLFFFRFFF");
            System.out.println("------------------------------");
        }
    }

    public MoveInGrid(int n, char dir, int x, int y) {
        grid = new char[n][n];
        for (char[] row:grid) {
            Arrays.fill(row, '.');
        }
        grid[x][y] = dir;
        currDir = dir;
        currX = x;
        currY = y;
    }

    public void move(String movement) {
        for (char action:movement.toCharArray()) {
            if (action == 'F') {
                moveForward();
            } else {
                turn(action);
            }
        }
        for (char[] row:grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    private void turn(char action) {
        switch (currDir) {
            case '^' : currDir = action == 'L' ? '<' : '>';
            break;
            case 'v' : currDir = action == 'L' ? '>' : '<';
            break;
            case '>' : currDir = action == 'L' ? '^' : 'v';
            break;
            case '<': currDir = action == 'L' ? 'v' : '^';
            break;
        }
    }

    private void moveForward() {
        switch (currDir) {
            case '^': currX = currX > 0 ? currX - 1 : currX;
            break;
            case 'v': currX = currX < grid.length - 1 ? currX + 1 : currX;
            break;
            case '>': currY = currY < grid.length - 1 ? currY + 1 : currY;
            break;
            case '<': currY = currY > 0 ? currY - 1: currY;
        }
        grid[currX][currY] = currDir;
    }
}
