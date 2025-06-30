package de.voomdoon.util.bool.matrix;

import java.util.Objects;

/**
 * Utility class for boolean matrices.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public final class BooleanMatrixUtil {

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private static class BooleanMatrixTrimmer {

		/**
		 * @since 0.1.0
		 */
		private int iColumnMax;

		/**
		 * @since 0.1.0
		 */
		private int iColumnMin;

		/**
		 * @since 0.1.0
		 */
		private int iRowMax;

		/**
		 * @since 0.1.0
		 */
		private int iRowMin;

		/**
		 * @since 0.1.0
		 */
		private boolean[][] matrix;

		/**
		 * @param matrix
		 *            boolean matrix
		 * @since 0.1.0
		 */
		public BooleanMatrixTrimmer(boolean[][] matrix) {
			this.matrix = matrix;
		}

		/**
		 * @since 0.1.0
		 */
		private void prepare() {
			iRowMin = Integer.MAX_VALUE;
			iRowMax = Integer.MIN_VALUE;
			iColumnMin = Integer.MAX_VALUE;
			iColumnMax = Integer.MIN_VALUE;

			for (int iRow = 0; iRow < matrix.length; iRow++) {
				for (int iColumn = 0; iColumn < matrix[iRow].length; iColumn++) {
					if (matrix[iRow][iColumn]) {
						iRowMin = Math.min(iRowMin, iRow);
						iRowMax = Math.max(iRowMax, iRow);
						iColumnMin = Math.min(iColumnMin, iColumn);
						iColumnMax = Math.max(iColumnMax, iColumn);
					}
				}
			}
		}

		/**
		 * @return
		 * @since 0.1.0
		 */
		private boolean[][] realTrim() {
			if (iRowMin == Integer.MAX_VALUE && iRowMax == Integer.MIN_VALUE) {
				return new boolean[0][0];
			}

			boolean[][] result = new boolean[iRowMax - iRowMin + 1][];
			int iResult = 0;

			for (int iRow = iRowMin; iRow <= iRowMax; iRow++) {
				boolean[] resultRow = new boolean[iColumnMax - iColumnMin + 1];
				System.arraycopy(matrix[iRow], iColumnMin, resultRow, 0, resultRow.length);
				result[iResult++] = resultRow;
			}

			return result;
		}

		/**
		 * @param matrix
		 * @return
		 * @since 0.1.0
		 */
		private synchronized boolean[][] trim() {
			prepare();

			return realTrim();
		}
	}

	/**
	 * Counts the number of {@code true} values in a boolean matrix.
	 * 
	 * @param matrix
	 *            boolean matrix
	 * @return the number of {@code true} values
	 * @since 0.1.0
	 */
	public static int countTrue(boolean[][] matrix) {
		Objects.requireNonNull(matrix, "matrix");
		int count = 0;

		for (int iRow = 0; iRow < matrix.length; iRow++) {
			Objects.requireNonNull(matrix[iRow], "row at index " + iRow);

			for (int iColumn = 0; iColumn < matrix[iRow].length; iColumn++) {
				if (matrix[iRow][iColumn]) {
					count++;
				}
			}
		}

		return count;
	}

	/**
	 * Returns a new boolean matrix which is trimmed of all rows and columns that contain no {@code true} values.
	 * 
	 * @param matrix
	 *            boolean matrix
	 * @return trimmed boolean matrix
	 * @since 0.1.0
	 */
	public static boolean[][] getTrimmed(boolean[][] matrix) {
		return new BooleanMatrixTrimmer(matrix).trim();
	}

	/**
	 * Returns {@code true} if all values in the given boolean matrix are {@code false}.
	 * 
	 * @param matrix
	 *            boolean matrix
	 * @return {@code true} if all values are {@code false}, {@code false} otherwise
	 * @since 0.1.0
	 */
	public static boolean isAllFalse(boolean[][] matrix) {
		Objects.requireNonNull(matrix, "matrix");

		for (int iRow = 0; iRow < matrix.length; iRow++) {
			Objects.requireNonNull(matrix[iRow], "row at index " + iRow);

			for (int iColumn = 0; iColumn < matrix[iRow].length; iColumn++) {
				if (matrix[iRow][iColumn]) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * @since 0.1.0
	 */
	private BooleanMatrixUtil() {
		// utility class
	}
}
