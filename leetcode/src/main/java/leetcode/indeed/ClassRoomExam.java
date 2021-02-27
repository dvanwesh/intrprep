package leetcode.indeed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassRoomExam {
    public static void main(String[] args) {
        String[] words = {"cat", "baby", "dog", "bird", "car", "ax"};
        ClassRoomExam exam = new ClassRoomExam();
        for (String str : new String[]{"tcabnihjs", "tbcanihjs", "baykkjl", "bbabylkkj", "ccc", "nbird"}) {
            System.out.println(str+"---->"+exam.findHiddenWord(words, str));
        }
        char[][] grid1 = new char[][]{{'c', 'c', 'c', 'a', 'r', 's'},
                                     {'c', 'c', 'i', 't', 'n', 'b'},
                                     {'a', 'c', 'n', 'n', 't', 'i'},
                                     {'t', 'c', 'i', 'i', 'p', 't'}};
        System.out.println("catnip -->"+exam.findWordLocation(grid1, "catnip"));
        System.out.println("cccc -->"+exam.findWordLocation(grid1, "cccc"));
        char[][] grid2 = new char[][]{{'c', 'p', 'a', 'n', 't', 's'},
                                      {'a', 'b', 'i', 't', 'a', 'b'},
                                      {'t', 'f', 'n', 'n', 'c', 'i'},
                                      {'x', 's', 'c', 'a', 't', 'n'},
                                      {'x', 's', 'd', 'd', 'e', 'a'},
                                      {'s', 'q', 'w', 'x', 's', 'p'}};
        System.out.println("catnap -->"+exam.findWordLocation(grid2, "catnap"));
        char[][] grid3 = {{'c', 'r', 'c', 'a', 'r', 's'},
                          {'a', 'b', 'i', 't', 'n', 'i'},
                          {'t', 'f', 'n', 'n', 'x', 'p'},
                          {'x', 's', 'i', 'x', 'p', 't'}};
        System.out.println("catnip -->"+exam.findWordLocation(grid3, "catnip"));
        char[][] grid4 = {{'a', 'o', 'o', 'o', 'a', 'a'},
                        {'b', 'b', 'i', 't', 'n', 'i'},
                        {'c', 'f', 'n', 'n', 'v', 'p'},
                        {'o', 'a', 'a', 'a', 'o', 'o'}};
        System.out.println("aaa -->"+exam.findWordLocation(grid4, "aaa"));
        System.out.println("ooo -->"+exam.findWordLocation(grid4, "ooo"));
    }

    /** You are running a classroom and suspect that some of your students are passing around the answers to multiple
     * choice questions disguised as random strings.Your task is to write a function that, given a list of words and
     * a string, finds and returns the word in the list that is scrambled up inside the string, if any exists.
     * There will be at most one matching word. The letters don't need to be contiguous.
     Example:
     words = ['cat', 'baby', 'dog', 'bird', 'car', 'ax']
     string1 = 'tcabnihjs'
     find_embedded_word(words, string1) -> cat

     string2 = 'tbcanihjs'
     find_embedded_word(words, string2) -> cat

     string3 = 'baykkjl'
     find_embedded_word(words, string3) -> None

     string4 = 'bbabylkkj'
     find_embedded_word(words, string4) -> baby

     string5 = 'ccc'
     find_embedded_word(words, string5) -> None

     string6 = 'nbird'
     find_embedded_word(words, string6) -> bird

     n = number of words in words
     m = maximal string length of each word

     * */
    private String findHiddenWord(String[] words, String str) {
        int[] mem = new int[256];
        for (char ch : str.toCharArray()) {
            mem[ch]++;
        }
        for (String word : words) {
            int[] ref = mem.clone();
            int i = 0;
            while (i < word.length()) {
                if (ref[word.charAt(i++)]-- <= 0) {
                    break;
                }
                if (i == word.length()) {
                    return word;
                }
            }
        }
        return "None";
    }

    /**
     After catching your classroom students cheating before, you realize your students are getting craftier and
     hiding words in 2D grids of letters. The word may start anywhere in the grid, and consecutive letters can be
     either immediately below or immediately to the right of the previous letter.
     Given a grid and a word, write a function that returns the location of the word in the grid as a list of
     coordinates. If there are multiple matches, return any one.
     grid1 = [
     ['c', 'c', 'c', 'a', 'r', 's'],
     ['c', 'c', 'i', 't', 'n', 'b'],
     ['a', 'c', 'n', 'n', 't', 'i'],
     ['t', 'c', 'i', 'i', 'p', 't']
     ]

     word1_1 = "catnip"
     find_word_location(grid1, word1_1)-> [ (0, 2), (0, 3), (1, 3), (2, 3), (3, 3), (3, 4) ]

     word1_2 = "cccc"
     find_word_location(grid1, word1_2)-> [(0, 1), (1, 1), (2, 1), (3, 1)]
     OR [(0, 0), (1, 0), (1, 1), (2, 1)]
     OR [(0, 0), (0, 1), (1, 1), (2, 1)]
     OR [(1, 0), (1, 1), (2, 1), (3, 1)]


     grid2 = [
     ['c', 'p', 'a', 'n', 't', 's'],
     ['a', 'b', 'i', 't', 'a', 'b'],
     ['t', 'f', 'n', 'n', 'c', 'i'],
     ['x', 's', 'c', 'a', 't', 'n'],
     ['x', 's', 'd', 'd', 'e', 'a'],
     ['s', 'q', 'w', 'x', 's', 'p']
     ]


     word2 = "catnap"
     find_word_location(grid2, word2)-> [ (3, 2), (3, 3), (3, 4), (3, 5), (4, 5), (5, 5) ]

     grid3 = {{'c', 'r', 'c', 'a', 'r', 's'},
                {'a', 'b', 'i', 't', 'n', 'i'},
                {'t', 'f', 'n', 'n', 'x', 'p'},
                {'x', 's', 'i', 'x', 'p', 't'}}
     word3 = "catnip"
     find_word_location(grid3, word3)-> [ (0, 2), (0, 3), (1, 3), (1, 4), (1, 5), (2, 5) ]

     grid4 = {
     {'a', 'o', 'o', 'o', 'a', 'a'},
     {'b', 'b', 'i', 't', 'n', 'i'},
     {'c', 'f', 'n', 'n', 'v', 'p'},
     {'o', 'a', 'a', 'a', 'o', 'o'}}
     word4_1 = "aaa"
     word4_2 = "ooo"

     find_word_location(grid4, word4_1)-> [ (3, 1), (3, 2), (3, 3) ]
     find_word_location(grid4, word4_2)-> [ (0, 1), (0, 2), (0, 3) ]
     * */
    private List<List<Integer>> findWordLocation(char[][] grid, String word){
        for(int i=0; i<grid.length; i++){
            for(int j=0; j<grid[i].length; j++){
                if(grid[i][j]==word.charAt(0)){
                    List<List<Integer>> res = new ArrayList<>();
                    if(dfs(grid, word, i, j, res, 0)){
                        return res;
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * catnip -->[[0, 2], [0, 3], [1, 3], [2, 3], [3, 3], [3, 4]]
     * cccc -->[[0, 0], [1, 0], [1, 1], [2, 1], [0, 1], [1, 1], [2, 1]]--
     * catnap -->[[3, 2], [3, 3], [3, 4], [3, 5], [4, 5], [5, 5]]
     * catnip -->[[0, 2], [0, 3], [1, 3], [1, 4], [1, 5], [2, 5]]
     * aaa -->[[3, 1], [3, 2], [3, 3]]
     * ooo -->[[0, 1], [0, 2], [0, 3]]
     * @param grid
     * @param word
     * @param i
     * @param j
     * @param res
     * @param idx
     * @return
     */
    private boolean dfs(char[][] grid, String word, int i, int j, List<List<Integer>> res, int idx) {
        if(idx == word.length()){
            return true;
        }
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != word.charAt(idx)){
            return false;
        }
        if(dfs(grid, word, i, j+1, res, idx+1)){
            res.add(0, Arrays.asList(i,j));
            return true;
        } else if(dfs(grid, word, i+1, j, res, idx+1)){
            res.add(0, Arrays.asList(i,j));
            return true;
        }
        return false;
    }
}
