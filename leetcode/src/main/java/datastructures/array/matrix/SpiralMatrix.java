package datastructures.array.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Fill matrix in a spiral and fetch data from matrix in a spiral
 * @author vdatla
 *
 */
public class SpiralMatrix {
	static int[][] matrix;

	public static void main(String[] args) {
		getSpiralMatrix(8);
		List<Integer> list = fetchSpiralPattern(matrix);
		System.out.println(list);
	}
/**
 * fetch spiral pattern
 * @param matrix
 * @return
 */
	private static List<Integer> fetchSpiralPattern(int[][] matrix) {
		List<Integer> res = new ArrayList<>();
		fetchSpiralPattern(matrix, matrix.length, 0, matrix[0].length - 1, res);
		return res;
	}
/**
 * store spiral pattern of matrix recursively in a list
 * @param matrix
 * @param size
 * @param start
 * @param end
 * @param res
 */
	private static void fetchSpiralPattern(int[][] matrix, int size, int start, int end, List<Integer> res) {
		if (size == 0)
			return;
		for (int j = start; j <= end; j++)
			res.add(matrix[start][j]);
		for (int i = start + 1; i <= end; i++)
			res.add(matrix[i][end]);
		for (int j = end - 1; j >= start; j--)
			res.add(matrix[end][j]);
		for (int i = end - 1; i > start; i--)
			res.add(matrix[i][start]);
		fetchSpiralPattern(matrix, size - 1, start + 1, end - 1, res);
	}
/**
 * prepare spiral matrix
 * @param n
 */
	public static void getSpiralMatrix(int n) {
		if (n < 1) {
			throw new IllegalArgumentException("n must be > 0");
		}
		matrix = new int[n][n];
		fillSpiralMatrix(matrix, n, 0, n - 1, 0);
		prinMatrix(matrix);
	}
// print matrix
	private static void prinMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + "  ");
			}
			System.out.println();
		}

	}
/**
 * fill matrix spirally
 * @param matrix
 * @param n
 * @param start
 * @param end
 * @param val
 */
	private static void fillSpiralMatrix(int[][] matrix, int n, int start, int end, int val) {
		if (n == 0)
			return;
		for (int j = start; j <= end; j++) {
			matrix[start][j] = ++val;
		}
		for (int i = start + 1; i <= end; i++) {
			matrix[i][end] = ++val;
		}
		for (int j = end - 1; j >= start; j--) {
			matrix[end][j] = ++val;
		}
		for (int i = end - 1; i > start; i--) {
			matrix[i][start] = ++val;
		}
		fillSpiralMatrix(matrix, n - 1, start + 1, end - 1, val);
	}
}
