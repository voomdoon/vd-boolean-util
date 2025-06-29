package de.voomdoon.util.bool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;

/**
 * Tests for {@link BooleanArrayUtil}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class BooleanArrayUtilTest {

	/**
	 * Tests for {@link BooleanArrayUtil#countTrue(boolean[])}.
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
		void test_empty_result0() throws Exception {
			logTestStart();

			boolean[] array = new boolean[] {};

			int actual = BooleanArrayUtil.countTrue(array);

			assertThat(actual).isZero();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_false_result0() throws Exception {
			logTestStart();

			boolean[] array = new boolean[] { false };

			int actual = BooleanArrayUtil.countTrue(array);

			assertThat(actual).isZero();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_true1_result1() throws Exception {
			logTestStart();

			boolean[] array = new boolean[] { true };

			int actual = BooleanArrayUtil.countTrue(array);

			assertThat(actual).isEqualTo(1);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_true2_result2() throws Exception {
			logTestStart();

			boolean[] array = { true, true };

			int actual = BooleanArrayUtil.countTrue(array);

			assertThat(actual).isEqualTo(2);
		}
	}
}
