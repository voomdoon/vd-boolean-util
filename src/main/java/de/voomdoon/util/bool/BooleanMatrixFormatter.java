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
	 * DOCME add JavaDoc for BooleanMatrixFormatter
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public enum Format {

		/**
		 * @since 0.1.0
		 */
		ONE_ZERO,

		/**
		 * @since 0.1.0
		 */
		TRUE_FALSE,

		;
	}

	/**
	 * @since 0.1.0
	 */
	private Format format = Format.TRUE_FALSE;

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
				if (format == Format.TRUE_FALSE && iColumn > 0) {
					sb.append(",");
				}

				sb.append(format(matrix[iRow][iColumn]));

			}
		}

		return sb.toString();
		// TODO implement format
	}

	/**
	 * DOCME add JavaDoc for method setFormat
	 * 
	 * @param format
	 * @since 0.1.0
	 */
	public void withFormat(Format format) {
		this.format = format;
	}

	/**
	 * DOCME add JavaDoc for method format
	 * 
	 * @param b
	 * @return
	 * @since 0.1.0
	 */
	private String format(boolean b) {
		return switch (format) {
			case TRUE_FALSE -> Boolean.toString(b);
			case ONE_ZERO -> b ? "1" : "0";
			default -> throw new UnsupportedOperationException("Format '" + format + "' not supported!");
		};
	}
}
