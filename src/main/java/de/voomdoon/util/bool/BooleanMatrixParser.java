package de.voomdoon.util.bool;

import java.util.ArrayList;
import java.util.List;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class BooleanMatrixParser {

	/**
	 * @since 0.1.0
	 */
	private boolean useHalfLineBlocks;

	/**
	 * DOCME add JavaDoc for method parseMatrix
	 * 
	 * @param string
	 * @return
	 * @since 0.1.0
	 */
	public boolean[][] parseMatrix(String string) {
		if (string.isEmpty()) {
			return new boolean[0][0];
		}

		if (useHalfLineBlocks) {
			return parseMatrixFromHalfLineBlocks(string);
		}

		if (string.contains("0") || string.contains("1")) {
			return parseMatrixFromDigits(string);
		} else if (string.contains("true") || string.contains("false")) {
			return parseMatrixFromLiterals(string);
		} else {
			return parseMatrixFromFullLineBlocks(string);
		}
	}

	/**
	 * Informs the parser to parse input from half line blocks like {@code ▀▄█}.
	 * 
	 * @since 0.1.0
	 */
	public void useHalfLineBlocks() {
		this.useHalfLineBlocks = true;
	}

	/**
	 * DOCME add JavaDoc for method parseDigits
	 * 
	 * @param string
	 * @return
	 * @since 0.1.0
	 */
	private boolean[][] parseMatrixFromDigits(String string) {
		String[] rows = string.split("\n");

		boolean[][] result = new boolean[rows.length][];

		for (int iRow = 0; iRow < rows.length; iRow++) {
			List<Boolean> resultRow = new ArrayList<>();

			for (int iCol = 0; iCol < rows[iRow].length(); iCol++) {
				char c = rows[iRow].charAt(iCol);

				if (c == '1') {
					resultRow.add(true);
				} else if (c == '0') {
					resultRow.add(false);
				} else if (c == '\r') {
					// ignore
				} else {
					// TODO implement parseMatrix
					throw new UnsupportedOperationException("Method 'parseMatrix' not implemented yet");
				}
			}

			result[iRow] = new boolean[resultRow.size()];

			for (int iCol = 0; iCol < resultRow.size(); iCol++) {
				result[iRow][iCol] = resultRow.get(iCol);
			}
		}

		return result;
	}

	/**
	 * DOCME add JavaDoc for method parseMatrixFromFullLineBlocks
	 * 
	 * @param string
	 * @return
	 * @since 0.1.0
	 */
	private boolean[][] parseMatrixFromFullLineBlocks(String string) {
		String[] rows = string.split("\n");

		boolean[][] result = new boolean[rows.length][];

		for (int iRow = 0; iRow < rows.length; iRow++) {
			String row = rows[iRow].replace("\r", "");

			if (row.length() % 2 != 0) {
				throw new IllegalArgumentException("Row length must be even: " + row.length());
			}

			result[iRow] = new boolean[row.length() / 2];

			for (int iCol = 0; iCol < result[iRow].length; iCol++) {
				if (row.charAt(iCol * 2) == '█' && row.charAt(iCol * 2 + 1) == '█') {
					result[iRow][iCol] = true;
				} else if (row.charAt(iCol * 2) == ' ' && row.charAt(iCol * 2 + 1) == ' ') {
					result[iRow][iCol] = false;
				} else {
					throw new IllegalArgumentException("Invalid character in matrix: '" + row.charAt(iCol * 2)
							+ "' or '" + row.charAt(iCol * 2 + 1) + "'!");
				}
			}
		}

		return result;
	}

	/**
	 * DOCME add JavaDoc for method parseMatrixFromHalfLineBlocks
	 * 
	 * @param string
	 * @return
	 * @since 0.1.0
	 */
	private boolean[][] parseMatrixFromHalfLineBlocks(String string) {
		String[] lines = string.split("\n");

		boolean[][] result = (lines[lines.length - 1].contains("▄") || lines[lines.length - 1].contains("█")) //
				? new boolean[lines.length * 2][lines[0].length()]
				: new boolean[lines.length * 2 - 1][lines[0].length()];

		for (int iLine = 0; iLine < lines.length; iLine++) {
			for (int i = 0; i < lines[iLine].length(); i++) {
				char c = lines[iLine].charAt(i);

				if (c == '▀') {
					result[iLine * 2][i] = true;
				} else if (c == '▄') {
					result[iLine * 2 + 1][i] = true;
				} else if (c == '█') {
					result[iLine * 2][i] = true;
					result[iLine * 2 + 1][i] = true;
				} else if (c == ' ') {
					result[iLine * 2][i] = false;
				} else {
					// TODO implement parseMatrixFromHalfLineBlocks
					throw new UnsupportedOperationException(
							"Method 'parseMatrixFromHalfLineBlocks' not implemented yet");
				}
			}
		}

		return result;
	}

	/**
	 * DOCME add JavaDoc for method parseLiterals
	 * 
	 * @param string
	 * @return
	 * @since 0.1.0
	 */
	private boolean[][] parseMatrixFromLiterals(String string) {
		String[] rows = string.replace("\r", "").split("\n");

		boolean[][] result = new boolean[rows.length][];

		for (int iRow = 0; iRow < rows.length; iRow++) {
			String[] columns = rows[iRow].split(",");
			result[iRow] = new boolean[columns.length];

			for (int iCol = 0; iCol < columns.length; iCol++) {
				String value = columns[iCol];

				if ("true".equals(value)) {
					result[iRow][iCol] = true;
				}
			}
		}

		return result;
	}
}
