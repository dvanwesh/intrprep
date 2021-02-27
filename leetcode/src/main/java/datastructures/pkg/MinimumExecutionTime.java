package datastructures.pkg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*given tasks such as ABBABC and an integer k, cool down time between two same
tasks.Assume execution time for each individual task is one unit. rearrange task
sequence such that execution time is minimal.
S=AAABBBCCC K=3 return ABCABCABC, execution time 9
S=AAABC, K=2 return ABCA_ _ A, execution time 7*/

public class MinimumExecutionTime {
	public static int reArrangeTasks(String tasks,int coolDown){
		Map<Character,Integer> map=new HashMap<Character, Integer>();
		//Storing string in map by character as key and number of repetitions of it as value
		for(int i=0;i<tasks.length();i++){
			char c=tasks.charAt(i);
			if(map.containsKey(c)){
				map.put(c, map.get(c)+1);
			}
			else{
				map.put(c, 1);
			}
		}
		//looping through map and removing each character in every unique alphabet for cool time period
		int c=0;
		boolean newLoop=false;
		while(!map.isEmpty()){
			newLoop=true;
			Iterator<Character> it=map.keySet().iterator();
			while(it.hasNext()){
				char ch=it.next();
				if(!newLoop && c!=0 && c%(coolDown+1)==0) break;
				newLoop=false;
				map.put(ch, map.get(ch)-1);
				c++;
				if(map.get(ch)==0) it.remove();
			}
			while(c%(coolDown+1)!=0 && !map.isEmpty()) c++;
		}
		return c;

	}
public static void main(String[] args) {
	String s="AAABC";
	System.out.println(reArrangeTasks(s,2));
}
}
