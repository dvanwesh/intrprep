package leetcode.square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
YY++GG
+Y++G+
+Y+GG+
B++RRR
B++++R
BBB++R

GG
GG
*/

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class BoardGame {
    public static void main(String[] args) {
        char[][] board = {
            {'Y', 'Y', '+', '+', 'G', 'G'},
            {'+', 'Y', '+', '+', 'G', '+'},
            {'+', 'Y', '+', 'G', 'G', '+'},
            {'B', '+', '+', 'R', 'R', 'R'},
            {'B', '+', '+', '+', '+', 'R'},
            {'B', 'B', 'B', '+', '+', 'R'}
        };
        BoardGame game = new BoardGame();
        System.out.println(Integer.MIN_VALUE / 2);
        //System.out.println(sol.isPlacable(board, 1, 0, 2, 2));
        System.out.println(game.getAllPossibleCoordinates(board, 2, 2));
    }

    private List<List<Integer>> getAllPossibleCoordinates(char[][] board, int w, int h) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '+' && isPlacable(board, i, j, w, h)) {
                    res.add(new ArrayList<>(Arrays.asList(i, j)));
                }
            }
        }
        return res;
    }

    private boolean isPlacable(char[][] board, int x, int y, int width, int height) {
        int w = width, h = height;
        for (int i = x; i < x + w; i++) { // 0; 1
            for (int j = y; j < y + h; j++) { // 3; 4
                if (i >= 0 && j >= 0 && i < board.length && j < board.length && board[i][j] == '+') {
                    //System.out.println(board[][]);
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}


