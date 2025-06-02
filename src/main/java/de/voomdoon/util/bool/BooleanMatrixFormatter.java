package de.voomdoon.util.bool;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class BooleanMatrixFormatter {

	/**
	 * DOCME add JavaDoc for method format
	 * 
	 * @param matrix
	 * @return
	 * @since 0.1.0
	 */
	public String format(boolean[][] matrix) {
		if (matrix.length == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder();

		for (int iRow = 0; iRow < matrix.length; iRow++) {
			if (iRow > 0) {
				sb.append("\n");
			}

			for (int iColumn = 0; iColumn < matrix[iRow].length; iColumn++) {
				if (iColumn > 0) {
					sb.append(",");
				}

				sb.append(matrix[iRow][iColumn]);

			}
		}

		return sb.toString();
		// TODO implement format
	}
}
