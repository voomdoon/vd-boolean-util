package de.voomdoon.util.bool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.util.bool.BooleanMatrixFormatter.Format;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class BooleanMatrixFormatterTest {

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class BasicsTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_emptyMatrix() {
			logTestStart();

			String actual = new BooleanMatrixFormatter().format(new boolean[0][0]);

			assertThat(actual).isEmpty();
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixFormatterTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Format_1_0_Test extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@BeforeEach
		void beforeEach_configureFormatter() {
			formatter.withFormat(Format.ONE_AND_ZERO);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_columnSeparator_none() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true, true } });

			assertThat(actual).isEqualTo("11");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_rowSeparator() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true }, { true } });

			assertThat(actual).isEqualTo("1\n1");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_false() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { false } });

			assertThat(actual).isEqualTo("0");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_true() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true } });

			assertThat(actual).isEqualTo("1");
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixFormatterTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Format_doubleWidthBlocks_Test extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@BeforeEach
		void beforeEach_configureFormatter() {
			formatter.withFormat(Format.DOUBLE_WIDTH_BLOCKS);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_rowSeparator() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true }, { true } });

			assertThat(actual).isEqualTo("██\n██");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_false() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { false } });

			assertThat(actual).isEqualTo("  ");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_true() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true } });

			assertThat(actual).isEqualTo("██");
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixFormatterTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class Format_halfBlocks_Test extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@BeforeEach
		void beforeEach_configureFormatter() {
			formatter.withFormat(Format.HALF_BLOCKS);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_2Rows_falseFalse() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { false }, { false } });

			assertThat(actual).isEqualTo(" ");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_2Rows_falseTrue() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { false }, { true } });

			assertThat(actual).isEqualTo("▄");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_2Rows_trueFalse() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true }, { false } });

			assertThat(actual).isEqualTo("▀");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_2Rows_trueTrue() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true }, { true } });

			assertThat(actual).isEqualTo("█");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_3Rows() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true }, { true }, { true } });

			assertThat(actual).isEqualTo("█\n▀");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_false() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { false } });

			assertThat(actual).isEqualTo(" ");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_true() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true } });

			assertThat(actual).isEqualTo("▀");
		}

		/**
		 * DOCME add JavaDoc for method test_twoRows
		 * 
		 * @since 0.1.0
		 */
		@Test
		void test_twoRows() throws Exception {
			logTestStart();

			String actual = formatter
					.format(new boolean[][] { { false, true, false, true }, { false, false, true, true } });

			assertThat(actual).isEqualTo(" ▀▄█");
		}
	}

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
		void test_columnSeparator_default() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true, true } });

			assertThat(actual).isEqualTo("true,true");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_rowSeparator() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true }, { true } });

			assertThat(actual).isEqualTo("true\ntrue");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_false() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { false } });

			assertThat(actual).isEqualTo("false");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_singleValue_true() throws Exception {
			logTestStart();

			String actual = formatter.format(new boolean[][] { { true } });

			assertThat(actual).isEqualTo("true");
		}
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	private abstract class TestBase extends de.voomdoon.testing.tests.TestBase {

		/**
		 * @since 0.1.0
		 */
		protected BooleanMatrixFormatter formatter = new BooleanMatrixFormatter();
	}
}
