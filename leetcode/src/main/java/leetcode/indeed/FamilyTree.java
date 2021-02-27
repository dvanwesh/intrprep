package leetcode.indeed;

import java.util.*;
import java.util.stream.Collectors;

public class FamilyTree {
    private Map<Integer, List<Integer>> parentChildMap = new HashMap<>();
    private Map<Integer, Integer> freqMap = new HashMap<>();
    boolean isC1Found, isC2Found;
    public static void main(String[] args) {
        int[][] parentChildArry = {{1, 3}, {2, 3}, {3, 6}, {5, 6}, {5, 7}, {4, 5},
                                   {4, 8}, {4, 9}, {9, 11}, {14, 4}, {13, 12}, {12, 9}};
        FamilyTree ft = new FamilyTree();
        System.out.println("3,8 ->"+ft.hasCommonAncestor(parentChildArry, 3, 8));
        System.out.println("5,8 ->"+ft.hasCommonAncestor(parentChildArry, 5, 8));
        System.out.println("6,8 ->"+ft.hasCommonAncestor(parentChildArry, 6, 8));
        System.out.println("6,9 ->"+ft.hasCommonAncestor(parentChildArry, 6, 9));
        System.out.println("1,3 ->"+ft.hasCommonAncestor(parentChildArry, 1, 3));
        System.out.println("3,1 ->"+ft.hasCommonAncestor(parentChildArry, 3, 1));
        System.out.println("7,11 ->"+ft.hasCommonAncestor(parentChildArry, 7, 11));
        System.out.println("6,5 ->"+ft.hasCommonAncestor(parentChildArry, 6, 5));
        System.out.println("5,6 ->"+ft.hasCommonAncestor(parentChildArry, 5, 6));
        parentChildArry = new int[][]{{11, 10}, {11, 12}, {2, 3}, {10, 2},
                                      {10, 5}, {1, 3}, {3, 4}, {5, 6}, {5, 7}, {7, 8}};
        System.out.println("4,12 ->"+ft.hasCommonAncestor(parentChildArry, 4, 12));
        System.out.println("1,6 ->"+ft.hasCommonAncestor(parentChildArry, 1, 6));
        System.out.println("1,12 ->"+ft.hasCommonAncestor(parentChildArry, 1, 12));
    }

    private boolean hasCommonAncestor(int[][] grid, int c1, int c2){
        parentChildMap.clear();
        freqMap.clear();
        Arrays.stream(grid).forEach(pair->{
            parentChildMap.computeIfAbsent(pair[0], a->new ArrayList<>()).add(pair[1]);
            freqMap.put(pair[1], freqMap.getOrDefault(pair[1], 0)+1);
            freqMap.put(pair[0], freqMap.getOrDefault(pair[0], 0));
        });
        List<Integer> parents = freqMap.entrySet().stream().filter(e->e.getValue()==0)
            .map(e->e.getKey()).collect(Collectors.toList());
        for(int parent: parents){
            isC1Found = false;
            isC2Found = false;
            if(parent == c1 || parent == c2){
                return false;
            }
            if(hasCommonAncestor(parent, c1, c2)){
                return true;
            }
        }
        return false;
    }

    private boolean hasCommonAncestor(int parent, int c1, int c2) {
        if(parent == c1){
            isC1Found = true;
        } else if(parent == c2){
            isC2Found = true;
        }
        if(isC1Found && isC2Found){
            return true;
        }
        for(int child : parentChildMap.getOrDefault(parent, new ArrayList<>())){
            if(hasCommonAncestor(child, c1, c2)){
                return true;
            }
        }
        return false;
    }
}
