package de.voomdoon.util.bool;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public final class BooleanMatrixUtil {

	/**
	 * DOCME add JavaDoc for method countTrue
	 * 
	 * @param matrix
	 * @return
	 * @since 0.1.0
	 */
	public static int countTrue(boolean[][] matrix) {
		int count = 0;

		for (int iRow = 0; iRow < matrix.length; iRow++) {
			for (int iColumn = 0; iColumn < matrix[iRow].length; iColumn++) {
				if (matrix[iRow][iColumn]) {
					count++;
				}
			}
		}

		return count;
	}

	/**
	 * @since 0.1.0
	 */
	private BooleanMatrixUtil() {
		// nothing to do
	}
}
