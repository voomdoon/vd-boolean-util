package de.voomdoon.util.bool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class BooleanMatrixFormatterTest extends TestBase {

	/**
	 * DOCME add JavaDoc for BooleanMatrixFormatterTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Format_trueFalse_Test extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_columnSeparator() throws Exception {
			logTestStart();

			String actual = new BooleanMatrixFormatter().format(new boolean[][] { { true, true } });

			assertThat(actual).isEqualTo("true,true");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_rowSeparator() throws Exception {
			logTestStart();

			String actual = new BooleanMatrixFormatter().format(new boolean[][] { { true }, { true } });

			assertThat(actual).isEqualTo("true\ntrue");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_false() throws Exception {
			logTestStart();

			String actual = new BooleanMatrixFormatter().format(new boolean[][] { { false } });

			assertThat(actual).isEqualTo("false");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_true() throws Exception {
			logTestStart();

			String actual = new BooleanMatrixFormatter().format(new boolean[][] { { true } });

			assertThat(actual).isEqualTo("true");
		}
	}

	/**
	 * @since 0.1.0
	 */
	@Test
	void test_empty() {
		logTestStart();

		String actual = new BooleanMatrixFormatter().format(new boolean[0][0]);

		assertThat(actual).isEmpty();
	}
}
