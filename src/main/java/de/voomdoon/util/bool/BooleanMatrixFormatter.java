package de.voomdoon.util.bool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	public static class BooleanMatrixFormatterBuilder {

		/**
		 * @since 0.1.0
		 */
		public static final String DEFAULT_COLUMN_SEPARATOR = ",";

		/**
		 * @since 0.1.0
		 */
		public static final Format DEFAULT_FORMAT = Format.TRUE_AND_FALSE_WITH_SEPARATOR;

		/**
		 * @since 0.1.0
		 */
		private String columnSeparator = DEFAULT_COLUMN_SEPARATOR;

		/**
		 * @since 0.1.0
		 */
		private Format format = DEFAULT_FORMAT;

		/**
		 * DOCME add JavaDoc for method build
		 * 
		 * @return
		 * 
		 * @since 0.1.0
		 */
		public BooleanMatrixFormatter build() {
			return new BooleanMatrixFormatter(format, columnSeparator);
		}

		/**
		 * DOCME add JavaDoc for method withColumnSeparator
		 * 
		 * @param separator
		 * @return
		 * @since 0.1.0
		 */
		public BooleanMatrixFormatterBuilder withColumnSeparator(String separator) {
			columnSeparator = Objects.requireNonNull(separator, "separator");

			return this;
		}

		/**
		 * DOCME add JavaDoc for method withFormat
		 * 
		 * @param format
		 * @return
		 * @since 0.1.0
		 */
		public BooleanMatrixFormatterBuilder withFormat(Format format) {
			this.format = Objects.requireNonNull(format, "format");

			return this;
		}
	}

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
		DOUBLE_WIDTH_BLOCKS,

		/**
		 * @since 0.1.0
		 */
		HALF_BLOCKS,

		/**
		 * @since 0.1.0
		 */
		ONE_AND_ZERO,

		/**
		 * @since 0.1.0
		 */
		TRUE_AND_FALSE_WITH_SEPARATOR,

		;
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixFormatter
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private static class HalfLineBlocksFormatter {

		/**
		 * @since 0.1.0
		 */
		private static final String BOTH = "█";

		/**
		 * @since 0.1.0
		 */
		private static final String BOTTOM = "▄";

		/**
		 * @since 0.1.0
		 */
		private static final String EMPTY = " ";

		/**
		 * @since 0.1.0
		 */
		private static final String TOP = "▀";

		/**
		 * DOCME add JavaDoc for method addFirstPassElement
		 * 
		 * @param row
		 * @param outputRow
		 * @param iColumn
		 * @since 0.1.0
		 */
		private void addFirstPassElement(boolean[] row, List<String> outputRow, int iColumn) {
			if (row[iColumn]) {
				outputRow.add(TOP);
			} else {
				outputRow.add(EMPTY);
			}
		}

		/**
		 * @param sb
		 * @param outputRow
		 * @since 0.1.0
		 */
		private void appenNewLine(StringBuilder sb, List<String> outputRow) {
			if (sb.length() > 0) {
				sb.append("\n");
			}

			outputRow.forEach(sb::append);
		}

		/**
		 * DOCME add JavaDoc for method apply
		 * 
		 * @param row
		 * @param outputRow
		 * @param pass
		 * @since 0.1.0
		 */
		private void apply(boolean[] row, List<String> outputRow, int pass) {
			for (int iColumn = 0; iColumn < row.length; iColumn++) {
				if (pass == 1) {
					addFirstPassElement(row, outputRow, iColumn);
				} else {
					updateSecondPassElement(row, outputRow, iColumn);
				}
			}
		}

		/**
		 * DOCME add JavaDoc for method formatHalfLineBlocks
		 * 
		 * @param matrix
		 * @return
		 * @since 0.1.0
		 */
		private String format(boolean[][] matrix) {
			StringBuilder sb = new StringBuilder();

			List<String> outputRow = new ArrayList<>();
			int pass = 0;

			for (int iRow = 0; iRow < matrix.length; iRow++) {
				pass++;

				apply(matrix[iRow], outputRow, pass);

				if (pass == 2) {
					appenNewLine(sb, outputRow);
					outputRow.clear();
					pass = 0;
				}
			}

			if (!outputRow.isEmpty()) {
				appenNewLine(sb, outputRow);
			}

			return sb.toString();
		}

		/**
		 * 
		 * DOCME add JavaDoc for method updateSecondPassElement
		 * 
		 * @param row
		 * @param outputRow
		 * @param iColumn
		 * @since 0.1.0
		 */
		private void updateSecondPassElement(boolean[] row, List<String> outputRow, int iColumn) {
			if (row[iColumn]) {
				if (outputRow.get(iColumn).equals(TOP)) {
					outputRow.set(iColumn, BOTH);
				} else {
					outputRow.set(iColumn, BOTTOM);
				}
			}
		}
	}

	/**
	 * @since 0.1.0
	 */
	private static final HalfLineBlocksFormatter HALF_LINE_BLOCKS_FORMATTER = new HalfLineBlocksFormatter();

	/**
	 * DOCME add JavaDoc for method builder
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public static BooleanMatrixFormatterBuilder builder() {
		return new BooleanMatrixFormatterBuilder();
	}

	private String columnSeparator;
	/**
	 * @since 0.1.0
	 */
	private final Format format;

	/**
	 * DOCME add JavaDoc for constructor BooleanMatrixFormatter
	 * 
	 * @param format
	 * @param columnSeparator
	 * @since 0.1.0
	 */
	private BooleanMatrixFormatter(Format format, String columnSeparator) {
		this.format = format;
		this.columnSeparator = columnSeparator;
	}

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
		} else if (format == Format.HALF_BLOCKS) {
			return HALF_LINE_BLOCKS_FORMATTER.format(matrix);
		}

		StringBuilder sb = new StringBuilder();
		int rowLength = -1;

		for (int iRow = 0; iRow < matrix.length; iRow++) {
			if (matrix[iRow] == null) {
				throw new IllegalArgumentException("Matrix must not contain null rows!");
			} else if (matrix[iRow].length == 0) {
				throw new IllegalArgumentException("Matrix must not contain empty rows!");
			} else if (rowLength > -1 && matrix[iRow].length != rowLength) {
				throw new IllegalArgumentException("Matrix is not regular: All rows must have the same length!");
			}

			rowLength = matrix[iRow].length;

			if (iRow > 0) {
				sb.append("\n");
			}

			for (int iColumn = 0; iColumn < matrix[iRow].length; iColumn++) {
				if (format == Format.TRUE_AND_FALSE_WITH_SEPARATOR && iColumn > 0) {
					sb.append(columnSeparator);
				}

				sb.append(formatValue(matrix[iRow][iColumn]));

			}
		}

		return sb.toString();
		// TODO implement format
	}

	/**
	 * @param b
	 *            the value to format
	 * @return {@link String}
	 * @since 0.1.0
	 */
	private String formatValue(boolean b) {
		return switch (format) {
			case TRUE_AND_FALSE_WITH_SEPARATOR -> Boolean.toString(b);
			case ONE_AND_ZERO -> b ? "1" : "0";
			case DOUBLE_WIDTH_BLOCKS -> b ? "██" : "  ";// FEATURE #8 make configurable
			default -> throw new UnsupportedOperationException("Format '" + format + "' not supported!");
		};
	}
}
