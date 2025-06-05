package de.voomdoon.util.bool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;

/**
 * Tests for {@link BooleanMatrixUtil}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class BooleanMatrixUtilTest {

	/**
	 * Tests for {@link BooleanMatrixUtil#countTrue(boolean[][])}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class CountTrueTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsFalseOnly_result0() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil.countTrue(new boolean[][] { { false } });

			assertThat(actual).isZero();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsOnetrue_result1() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil.countTrue(new boolean[][] { { true } });

			assertThat(actual).isEqualTo(1);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsSomeTruesINSomeColumns_result4() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil
					.countTrue(new boolean[][] { { false, false }, { false, true }, { true, false }, { true, true } });

			assertThat(actual).isEqualTo(4);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsTwoTruesInAColumn_result2() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil.countTrue(new boolean[][] { { true }, { true } });

			assertThat(actual).isEqualTo(2);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsTwoTruesInARow_result2() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil.countTrue(new boolean[][] { { true, true } });

			assertThat(actual).isEqualTo(2);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsTwoTruesOnTheDiagonal_result2() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil.countTrue(new boolean[][] { { true, false }, { false, true } });

			assertThat(actual).isEqualTo(2);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_empty_result0() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil.countTrue(new boolean[0][0]);

			assertThat(actual).isZero();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_NPE_columnNull() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> BooleanMatrixUtil.countTrue(new boolean[][] { null }))
					.isInstanceOf(NullPointerException.class).hasMessageContaining("row").hasMessageContaining("0");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_NPE_matrixNull() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> BooleanMatrixUtil.countTrue(null)).isInstanceOf(NullPointerException.class)
					.hasMessageContaining("matrix");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_irregularMatrixWithTrueAtLongRow_resultAsExpected() throws Exception {
			logTestStart();

			int actual = BooleanMatrixUtil.countTrue(new boolean[][] { { false }, { false, true } });

			assertThat(actual).isEqualTo(1);
		}
	}
}
