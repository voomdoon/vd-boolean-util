package de.voomdoon.util.bool;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class BooleanArrayUtil {

	/**
	 * DOCME add JavaDoc for method countTrue
	 * 
	 * @param array
	 * @return
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
