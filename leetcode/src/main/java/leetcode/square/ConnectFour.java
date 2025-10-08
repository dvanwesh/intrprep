package leetcode.square;

public class ConnectFour {
    private final char[][] grid;
    private final int rows;
    private final int cols;
    private final int toWin;

    public ConnectFour(int rows, int cols, int toWin) {
        this.rows = rows;
        this.cols = cols;
        this.toWin = toWin;
        this.grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '-';
            }
        }
    }

    public void play(int column, char color) {
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][column] == '-') {
                grid[row][column] = color;
                break;
            }
        }
    }

    public boolean checkWin(int column, char color) {
        for (int row = 0; row < rows; row++) {
            if (grid[row][column] == color) {
                if (checkDirection(row, column, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDirection(int row, int column, char color) {
        int[][] dirs = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        for (int[] dir:dirs) {
            int count = 1;
            int r = row;
            int c = column;

            // Check in one direction
            while (isValid(r + dir[1], c + dir[0], color)) {
                r += dir[1];
                c += dir[0];
                count++;
            }

            r = row;
            c = column;

            // Check in the opposite direction
            while (isValid(r - dir[1], c - dir[0], color)) {
                r -= dir[1];
                c -= dir[0];
                count++;
            }

            if (count >= toWin) {
                return true;
            }
        }

        return false;
    }

    private boolean isValid(int row, int column, char color) {
        return row >= 0 && row < rows && column >= 0 && column < cols && grid[row][column] == color;
    }

    public void displayGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        ConnectFour game = new ConnectFour(6, 6, 5);
        game.play(3, 'R');
        game.play(3, 'R');
        game.play(3, 'R');
        game.play(3, 'R');
        game.play(3, 'R');
        game.play(1, 'R');
        game.play(2, 'R');
        game.displayGrid();

        System.out.println("Winner? " + game.checkWin(3, 'R'));
    }
}
