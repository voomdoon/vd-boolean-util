package de.voomdoon.util.bool.matrix;

import java.util.Objects;

/**
 * Calculates boolean matrix operations.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public final class BooleanMatrixCalculator {

	/**
	 * Performs a logical OR operation on two boolean matrices of equal size.
	 * 
	 * @param left
	 *            left matrix
	 * @param right
	 *            right matrix
	 * @return resulting matrix where each element is the logical OR of the corresponding elements in the left and right
	 *         matrices
	 * @since 0.1.0
	 */
	public static boolean[][] or(boolean[][] left, boolean[][] right) {
		Objects.requireNonNull(left, "left");
		Objects.requireNonNull(right, "right");
		validateEqualRowCount(left, right);

		boolean[][] result = new boolean[left.length][];

		for (int iRow = 0; iRow < left.length; iRow++) {
			validateEqualColumnCount(left, right, iRow);
			result[iRow] = new boolean[left[iRow].length];

			for (int iCol = 0; iCol < left[iRow].length; iCol++) {
				result[iRow][iCol] = left[iRow][iCol] || right[iRow][iCol];
			}
		}

		return result;
	}

	/**
	 * @param left
	 * @param right
	 * @param iRow
	 * @since 0.1.0
	 */
	private static void validateEqualColumnCount(boolean[][] left, boolean[][] right, int iRow) {
		if (left[iRow].length != right[iRow].length) {
			throw new IllegalArgumentException("Column count of row " + iRow + " of left matrix (" + left[iRow].length
					+ ") must match column count of right matrix (" + right[iRow].length + ")!");
		}
	}

	/**
	 * @param left
	 * @param right
	 * @since 0.1.0
	 */
	private static void validateEqualRowCount(boolean[][] left, boolean[][] right) {
		if (left.length != right.length) {
			throw new IllegalArgumentException("Row count of left matrix (" + left.length
					+ ") must match row count of right matrix (" + right.length + ")!");
		}
	}

	/**
	 * @since 0.1.0
	 */
	private BooleanMatrixCalculator() {
		// utility class
	}
}
