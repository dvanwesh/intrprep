package datastructures.array.pkg;

import java.util.Random;

/**
 *
 * @author vdatla
 *
 */
public class IndexesOfmaxValueinArray {
	static Random random = new Random();
public static void main(String[] args) {
	int[] ar={1,-2,0,6,2,-4,6,6};
	System.out.println(indexofMaxvalue(ar));
}

private static int indexofMaxvalue(int[] ar) {
	if(ar==null || ar.length<1) return -1;
	if(ar.length==1) return 0;
	int max=ar[0];
	for(int i=0;i<ar.length;i++){
		max=Math.max(max, ar[i]);
	}
	int index=-1;
	int maxSoFar=0;
	for(int i=0;i<ar.length;i++){
		if(ar[i]!=max){
			continue;
		}
		maxSoFar++;
		if(randomCheckForIndex(maxSoFar)){
			index=i;
		}
	}
	return index;
}

private static boolean randomCheckForIndex(int maxSoFar) {
	int i=random.nextInt(maxSoFar);
	return i==0;
}
}
