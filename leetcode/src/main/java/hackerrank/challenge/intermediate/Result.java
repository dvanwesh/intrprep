package hackerrank.challenge.intermediate;

import java.util.HashMap;
import java.util.Map;
/*
* 2. Maximizing Element With Constraints
In this problem, the goal is to determine the maximum value of an element at a certain index in an array of integers that can be
constructed under some constraints.
More specifically, nis the desired array size, maxSum is the maximum allowed sum of elements in the array, and kis the index of the
element that needs its value to be maximized. The 0-indexed array has the following constraints:
1. The array consists of n positive integers.
2. The sum of all elements in the array is at most maxSum.
3. The absolute difference between any two consecutive elements in the array is at most 1.
What is the maximum value of the integer at index kin such an array?
For example, let's say 9 = 3, maxSum = 6, and k = 1. So, the goal is to find the maximum value of the element at index 1 in an array of 3
positive integers, where the sum of elements is at most 6, and the absolute difference between every two consecutive elements is at most
1.
The maximum such value is 2, and it can be achieved, for example, by the array [1, 2, 2]. This array has 3 elements, each of them a positive
integer. The sum of the elements does not exceed 6, and the absolute difference between any two consecutive elements is at most 1.
There is no other such array that has a larger value at index k = 7. Therefore, the answer is 2 because that is the maximum value of the
integer at index k.
Function Description
Complete the function max£/ement in the editor below. The function must return an integer denoting the maximum value of the element
at index kgiven the above constraints.
maxElement has the following parameter(s):
int m: the size of the array
int maxSum. the maximum allowed sum of the elements in the array
int k: the index of the element in the array where the value needs to be maximized
Returns
int: the maximum value of the element at index k given the above constraints
* */
public class Result {
    /*
     * Complete the 'maxElement' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER maxSum
     *  3. INTEGER k
     */
    static int max = 0;
    static int len = 0;
    static boolean isValid = false;
    private static int[] dir = new int[]{-1, 0, 1};

    public static int maxElement(int n, int maxSum, int k) {
        // Write your code here
        max = maxSum;
        len = n;
        for(int i = maxSum; i >= 0; i--){
            System.out.println("----------------"+i);
            if(isPossible(i, i,k-1, k+1, maxSum-i)){
                return i;
            }
        }
        return -1;
    }

    private static boolean isPossible(int left, int right, int x, int y, int m){
        //System.out.println(val+","+idx+","+m);
        if(x < 0 && y >= len){
            return true;
        }
        if(m < 0){
            return false;
        }
        for(int i : dir){
            //if(isPossible(left+i, right, x-1, y+1, m-));
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(maxElement(3, 7, 1));
    }
}
