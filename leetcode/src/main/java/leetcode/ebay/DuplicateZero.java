package leetcode.ebay;

import java.util.Arrays;

/* Click `Run` to execute the snippet below!
input [1,0,2,3,0,4,5,0]
       0, , , , , ,6,
                      1,0,0,2,3,0,0,4
       zeroCount = 3;
       for(int i = 6; i >=0; i--){
       }

output[1,0,0,2,3,0,0,4]

input        [0 1 2 3 4 5 6 7 8 9]
output input [0 0 1 2 3 4 5 6 7 8]

req: int[] input
duplicate 0

T O(n)
S O(1)

 */
class DuplicateZero {
    public static void main(String[] args) {
        DuplicateZero sol = new DuplicateZero();
        int[] ar = new int[]{1,0,2,3,0,4,5,0};
        System.out.println(Arrays.toString(ar));
        sol.duplicateZeros(ar);
        System.out.println(Arrays.toString(ar));
    }
    public void duplicateZeros(int[] arr) {
        if(arr == null || arr.length == 0){
            return;
        }
        int zeroCount = 0;
        for(int n : arr){
            if(n == 0){
                zeroCount++;
            }
        }

        for(int i = arr.length - 1; i >= 0; i--){
            if(arr[i] == 0){
                zeroCount--;
            } else{
                int idx = i + zeroCount;
                if(idx < arr.length){
                    arr[idx] = arr[i];
                }
                if(idx > i){
                    arr[i] = 0;
                }
            }
        }
    }
}
