package leetcode.fb;

public class PrintDiagonals {

    public static void main(String[] args) {
        int[][] arr = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9,10,11,12}};
        System.out.println("--------------a--------------------");
        printLeftLeaningDiagonals(arr);
    }

    public static void printLeftLeaningDiagonals(int[][] grid) {
        int r = 0;
        int c = 0;
        // move right
        while(c < grid[r].length) { // r = 0; c = 0, 1, 2, 3, 4
            printDiagonals(grid, r, c);
            c++;
        }

        c = grid[r].length - 1; //
        r = 1;
        while(r < grid.length) { // r =  1, 2, c= 3
            printDiagonals(grid, r, c);
            r++;
        }
    }

    private static void printDiagonals(int[][] grid, int r, int c) { // r = 2, c = 3
        int row = r;
        int col = c;
        while(row < grid.length && col >= 0) {               // row = 3, col = 2
            System.out.print(grid[row++][col--]+" ");         // 1 , (2, 5), (3, 6, 9), (4, 7, 10), (8, 11), (12)
        }
        System.out.println();
    }
}
