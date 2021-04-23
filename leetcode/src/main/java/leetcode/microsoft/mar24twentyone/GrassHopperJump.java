package leetcode.microsoft.mar24twentyone;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class GrassHopperJump {
    public static void main(String [] args) {
        // you can write to stdout for debugging purposes, e.g.
        System.out.println("This is a debug message");
    }

    private static boolean canJumpToBank(int[] river){
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(0, 1));
        Set<Pair> seen = new HashSet<>();
        seen.add(queue.peek());
        while(!queue.isEmpty()){
            Pair cur = queue.poll();
            for(int i = -1; i <= 1; i++){
                int nextIdx = cur.idx + (cur.jump + i);
                if(nextIdx >= river.length){
                    return true;
                }
                if(nextIdx >=0 && !seen.contains(nextIdx) && river[nextIdx] == 1){
                    queue.offer(new Pair(nextIdx, nextIdx - cur.idx));
                    seen.add(new Pair(nextIdx, nextIdx - cur.idx));
                }
            }
        }
        return false;
    }
}

class Pair{
    int idx;
    int jump;
    public Pair(int i, int j){
        idx = i;
        jump = j;
    }
}
