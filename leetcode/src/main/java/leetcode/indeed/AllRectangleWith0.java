package leetcode.indeed;

public class AllRectangleWith0 {
    public static void main(String[] args) {

        int[][] mat = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 1, 1},
            {1, 0, 1, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}};
        findIndex(mat);
    }

    private static void findIndex(int[][] mat) {

        StringBuffer output = new StringBuffer();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 0) {
                    output.append("[" + i + "," + j + ",");
                    findEnd(output, i, j, mat);
                    output.append("]" + ",");
                }
            }
        }
        System.out.println(output.substring(0, output.length() - 1));
    }

    private static void findEnd(StringBuffer set, int k, int l, int[][] mat) {
        int i;
        int j = 0;
        for (i = k; i < mat.length; i++) {
            if (mat[i][l] == 1) {
                break;
            }
            for (j = l; j < mat[0].length; j++) {
                if (mat[i][j] == 1) {
                    break;
                }
                mat[i][j] = 5;
            }
        }
        i = i - 1;
        j = j - 1;
        set.append(i + "," + j);
    }
}
