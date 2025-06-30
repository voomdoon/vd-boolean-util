package de.voomdoon.util.bool.array;

/**
 * Utility class for boolean arrays.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class BooleanArrayUtil {

	/**
	 * Counts the number of {@code true} values in a boolean array.
	 * 
	 * @param array
	 *            boolean array
	 * @return the number of {@code true} values
	 * @since 0.1.0
	 */
	public static int countTrue(boolean[] array) {
		int count = 0;

		for (int i = 0; i < array.length; i++) {
			if (array[i]) {
				count++;
			}
		}

		return count;
	}

	/**
	 * @since 0.1.0
	 */
	private BooleanArrayUtil() {
		// utility class
	}
}
