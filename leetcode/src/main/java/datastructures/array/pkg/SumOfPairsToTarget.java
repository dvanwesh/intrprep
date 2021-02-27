package datastructures.array.pkg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SumOfPairsToTarget {
	public static void main(String[] args) {
		int[] nums=new int[]{1,2,1,4};
		System.out.println(getSumOfTarget(nums,3));
	}
	public static List<List<Integer>> getSumOfTarget(int[] nums,int target){
	    List<List<Integer>> res=new ArrayList<>();
	    if(nums==null || nums.length<2) return res;
	    Map<Integer,Integer> map=new HashMap<>();
	    for(int i:nums){
	        if(map.containsKey(target-i) && map.get(target-i)==1 && !map.containsKey(i)){
	            List<Integer> list=new ArrayList<>();
	            list.add((target-i));
	            list.add(i);
	            res.add(list);
	        }
	        map.put(i,map.getOrDefault(i,0)+1);
	    }
	    return res;
	}
}
