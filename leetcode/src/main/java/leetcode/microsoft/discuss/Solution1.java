package leetcode.microsoft.discuss;

import leetcode.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution1 {
    public static void main(String[] args) {

    }
    private void printZeroPath(int r, int c){
        Map<Node, List<Pair<Node, Dir>>> adj = new HashMap<>();
        for(int i = 0; i < r; i++){
            for(int j = 0; j < c; j++){
                Node cur = new Node(i, j);
                adj.put(cur, new ArrayList<>());
                if(j + 1 < c){
                    adj.get(cur).add(new Pair<>(new Node(i, j+1), Dir.RIGHT));
                }
                if(i + 1 < r){
                    adj.get(cur).add(new Pair<>(new Node(i+1, j), Dir.DOWN));
                }
                if(i > 0){
                    adj.get(cur).add(new Pair<>(new Node(i-1, j), Dir.LEFT));
                }
                if(j > 0){
                    adj.get(cur).add(new Pair<>(new Node(i, j-1), Dir.UP));
                }
            }
        }

    }
}
class Node{
    int x;
    int y;
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
enum Dir{
    UP,
    DOWN,
    RIGHT,
    LEFT;
    public int cost(int x){
        switch (this){
            case UP: return x*2;
            case DOWN: return x/2;
            case RIGHT: return x+2;
            case LEFT: return x-2;
        }
        return 0;
    }
}
