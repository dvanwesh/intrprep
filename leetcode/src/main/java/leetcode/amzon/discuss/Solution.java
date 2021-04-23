package leetcode.amzon.discuss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public static void main(String[] args) {
        String cmd = "GRGL";
        String cmd2 = "GGLLGG";
        String cmd3 = "GG";
        String cmd4 = "GL";
        List<String> inp = new ArrayList<>();
        inp.add(cmd);
        inp.add(cmd2);
        inp.add(cmd3);
        inp.add(cmd4);
        System.out.println(doesCircleExists(inp,1));
    }

    /*
    * time complexity: O(n*m) n: number of commands, m: avg length of cmd
    * space complexity: O(1) constant space
    * */
    private static List<String> doesCircleExists(List<String> cmds, int n){
        List<String> res = new ArrayList<>();
        for(String cmd : cmds){
            res.add(doesCircleExists(cmd));
        }
        return res;
    }

    private static String doesCircleExists(String cmd) {
        int[] dir = new int[]{0, 0, 0, 1};
        for(char ch : cmd.toCharArray()){
            switch (ch){
                case 'G': dir[0] += dir[2];
                dir[1] += dir[3];
                break;
                case 'R': moveRight(dir);
                    break;
                case 'L': moveLeft(dir);
                    break;
            }
        }
        //System.out.println(Arrays.toString(dir));
        return dir[0] == 0 && dir[1] == 0 ? "YES" : (dir[2]==0 && dir[3]==1 ? "NO" : "YES");
    }

    private static void moveRight(int[] dir){
        if(dir[2] == 0 && dir[3] == 1){
            dir[2] = 1;
            dir[3] = 0;
        } else if(dir[2] == 1 && dir[3] == 0){
            dir[2] = 0;
            dir[3] = -1;
        } else if(dir[2] == 0 && dir[3] == -1){
            dir[2] = -1;
            dir[3] = 0;
        } else if(dir[2] == -1 && dir[3] == 0){
            dir[2] = 0;
            dir[3] = 1;
        }
    }

    private static void moveLeft(int[] dir){
        if(dir[2] == 0 && dir[3] == 1){
            dir[2] = -1;
            dir[3] = 0;
        } else if(dir[2] == -1 && dir[3] == 0){
            dir[2] = 0;
            dir[3] = -1;
        } else if(dir[2] == 0 && dir[3] == -1){
            dir[2] = 1;
            dir[3] = 0;
        } else if(dir[2] == 1 && dir[3] == 0){
            dir[2] = 0;
            dir[3] = 1;
        }
    }
}
