package de.voomdoon.util.bool;

import java.util.Objects;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public final class BooleanMatrixUtil {

	/**
	 * DOCME add JavaDoc for BooleanMatrixUtil
	 *
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
		 * DOCME add JavaDoc for constructor BooleanMatrixTrimmer
		 * 
		 * @param matrix
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
		 * DOCME add JavaDoc for method realTrim
		 * 
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
		 * DOCME add JavaDoc for method trim
		 * 
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
	 * DOCME add JavaDoc for method countTrue
	 * 
	 * @param matrix
	 * @return
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
	 * DOCME add JavaDoc for method getTrimmed
	 * 
	 * @param matrix
	 * @return
	 * @since 0.1.0
	 */
	public static boolean[][] getTrimmed(boolean[][] matrix) {
		return new BooleanMatrixTrimmer(matrix).trim();
	}

	/**
	 * DOCME add JavaDoc for method isAllFalse
	 * 
	 * @param matrix
	 * @return
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
		// nothing to do
	}
}
