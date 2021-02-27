package datastructures.array.pkg;

public class FindKthMissingElement {
	 public static void main(String... arg) {

	        //System.out.println(countingSort(new int[]{1, 3, 4, 6, 7, 8,10,11,13}, 0, 0, 8));
	        //System.out.println(countingSort(new int[]{1, 3, 4, 6, 7, 8,10,11,13}, 1, 0, 8));
	        //System.out.println(countingSort(new int[]{1, 3, 4, 6, 7, 8,10,11,13}, 2, 0, 8));
	       // System.out.println(countingSort(new int[]{1, 3, 4, 6, 7, 8,10,11,13}, 3, 0, 8));
	        System.out.println(countingSort(new int[]{5, 7, 9, 10, 11, 13,14,15,17}, 2, 0, 8));

	    }

	    public static int countingSort(int[] arr, int k, int s, int e) {
	        int mid = s + ((e - s) / 2);
	        if (mid == s) {
	            return arr[mid] + 1;
	        }
	        int i1 =   arr[mid]-((mid - s) + arr[s]);
	        System.out.println(i1+"----"+(k+1));
	        if (i1 >= (k + 1)) {
	            return countingSort(arr, k, s, mid);
	        } else {
	            return countingSort(arr, k - i1, mid, e);
	        }
	    }
}
