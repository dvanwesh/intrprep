package hackerrank.challenge.intermediate;

public class Solution {
    /*
     * Complete the 'minFolders' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER cssFiles
     *  2. INTEGER jsFiles
     *  3. INTEGER readMeFiles
     *  4. INTEGER capacity
     */

    public static int minFolders(int cssFiles, int jsFiles, int readMeFiles, int capacity) {
        // Write your code here
        int maxFreq = Math.max(Math.max(cssFiles, jsFiles), readMeFiles);
        int cap = capacity, n = 0;
        while(jsFiles > 0 || cssFiles > 0 || readMeFiles > 0){
            cap = capacity;
            if(readMeFiles > 0){
                readMeFiles--;
                cap--;
            }
            int j = 0, css = 0;
            while(cap > 0 && (jsFiles > 0 || cssFiles > 0) && Math.abs(j-css) < 1){
                if(jsFiles > 0){
                    jsFiles--;
                    cap--;
                    j++;
                }
                if(cssFiles > 0 && cap > 0 && Math.abs(j-css) < 1){
                    cssFiles--;
                    cap--;
                    css++;
                }
            }
            n++;
        }
        return n;
    }

    public static void main(String[] args) {
        System.out.println(minFolders(5,0,2,2));
    }
}
