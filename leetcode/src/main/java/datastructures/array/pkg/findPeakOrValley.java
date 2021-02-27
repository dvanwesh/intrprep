package datastructures.array.pkg;

import java.util.Arrays;

/**
 * Given an array which has either a peak or valley, find the turning point Ex:
 * [0, 1, 2, 3, 4, 5, 6, 7, 8, 7] 8 [10, 9, 8, 7, 6, 5, 4, 3, 4, 5] 7---ar[r]--3
 *
 * @author vdatla
 *
 */
public class findPeakOrValley {
public static void main(String[] args) {
	int[] ar=createInput(10,"Down");
	System.out.println(Arrays.toString(ar));
	int r=getValeyOrPeak(ar);
	System.out.println(r+"---ar[r]--"+ar[r]);
}
private static int[] createInput(int k, String s) {
	int n=2+(int)(Math.random()*(k-2));
	int ar[]=new int[k];
	int i=0;
	if(s.equals("UP")){
		for(i=0;i<n;i++){
			ar[i]=i;
		}
		while(i<k){
			ar[i]=i-2;
			i++;
		}
	}
	else{
		for(i=0;i<n;i++){
			ar[i]=k-i;
		}
		while(i<k){
			ar[i]=ar[i-1]+1;
			i++;
		}
	}

	return ar;
}
private static int getValeyOrPeak(int[] ar){
    if(ar==null || ar.length==0) return 0;
    if(ar.length==1) return ar[0];
    if(ar[1]-ar[0]>0){
     return BinarySearchofPeak(ar,0,ar.length-1);
    }
    else{
     return BinarySearchofValley(ar,0,ar.length-1);
    }
 }

 // We will finish coding at 3:40 PM

 // e.g. [4, 5, 4, 3, 2]
 static int BinarySearchofPeak(int[] ar,int start,int end){
   int mid=(start+end)/2;                                     // 4 6, 5
   if(start<=end){
     if(start==end){
     return mid;
     }
     if(ar[mid]>ar[mid-1] && ar[mid]>ar[mid+1]){
       return mid;
     }
     if(ar[mid]>ar[start] && ar[mid]>ar[mid-1]){
       return BinarySearchofPeak(ar,mid,end);
     }
     else{
       return BinarySearchofPeak(ar,start,mid);
     }
   }
    return 0;

 }

static int BinarySearchofValley(int[] ar,int start,int end){
   int mid=(start+end)/2;
   if(start<=end){
     if(start==end){
     return mid;
     }
     if(ar[mid]<ar[start] && ar[mid]<ar[mid-1]){
       return BinarySearchofValley(ar,mid,end);
     }
     else{
       return BinarySearchofValley(ar,start,mid);
     }
   }
    return 0;

 }
}
