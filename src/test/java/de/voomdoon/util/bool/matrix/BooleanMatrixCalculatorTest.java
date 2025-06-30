package de.voomdoon.util.bool.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
class BooleanMatrixCalculatorTest {

	/**
	 * DOCME add JavaDoc for BooleanMatrixCalculatorTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class OrTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_empty() {
			logTestStart();

			boolean[][] actual = BooleanMatrixCalculator.or(new boolean[0][0], new boolean[0][0]);

			assertThat(actual).isEmpty();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_IllegalArgumentException_columnCountOfLeftSmaller() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> {
				BooleanMatrixCalculator.or(new boolean[][] { { false } }, new boolean[][] { { false, false } });
			}).isInstanceOf(IllegalArgumentException.class)//
					.hasMessageContainingAll("column", "count", "1", "2");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_IllegalArgumentException_columnCountOfRightSmaller() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> {
				BooleanMatrixCalculator.or(new boolean[][] { { false, false } }, new boolean[][] { { false } });
			}).isInstanceOf(IllegalArgumentException.class)//
					.hasMessageContainingAll("column", "count", "1", "2");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_IllegalArgumentException_rowCountOfLeftSmaller() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> {
				BooleanMatrixCalculator.or(new boolean[][] { { false } }, new boolean[][] { { false }, { false } });
			}).isInstanceOf(IllegalArgumentException.class)//
					.hasMessageContainingAll("row", "count", "1", "2");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_IllegalArgumentException_rowCountOfRightSmaller() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> {
				BooleanMatrixCalculator.or(new boolean[][] { { false }, { false } }, new boolean[][] { { false } });
			}).isInstanceOf(IllegalArgumentException.class)//
					.hasMessageContainingAll("row", "count", "1", "2");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_false() throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixCalculator.or(new boolean[][] { { false } },
					new boolean[][] { { false } });

			assertThat(actual).isEqualTo(new boolean[][] { { false } });
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_true_byBoth() throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixCalculator.or(new boolean[][] { { true } }, new boolean[][] { { true } });

			assertThat(actual).isEqualTo(new boolean[][] { { true } });
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_true_byLeft() throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixCalculator.or(new boolean[][] { { true } },
					new boolean[][] { { false } });

			assertThat(actual).isEqualTo(new boolean[][] { { true } });
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_true_byRight() throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixCalculator.or(new boolean[][] { { false } },
					new boolean[][] { { true } });

			assertThat(actual).isEqualTo(new boolean[][] { { true } });
		}
	}
}
