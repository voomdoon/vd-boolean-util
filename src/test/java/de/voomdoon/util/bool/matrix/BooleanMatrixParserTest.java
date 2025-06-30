package de.voomdoon.util.bool.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.util.bool.matrix.BooleanMatrixParser;

/**
 * Tests for {@link BooleanMatrixParser}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class BooleanMatrixParserTest {

	/**
	 * Tests for {@link BooleanMatrixParser#parseMatrix(String)}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class ParseMatrixTest extends TestBase {

		/**
		 * Tests for different formats.
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class FormatTest extends TestBase {

			/**
			 * Tests for format using {@code 0} and {@code 1}.
			 *
			 * @author André Schulz
			 *
			 * @since 0.1.0
			 */
			@Nested
			class DigitsTest extends TestBase {

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultFalse_zero() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("0");

					assertThat(actual).isEqualTo(new boolean[][] { { false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultTrue_one() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("1");

					assertThat(actual).isEqualTo(new boolean[][] { { true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows1() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("101");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows2() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("101\n010");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true }, { false, true, false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows2WithCarriageReturn() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("101\r\n010");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true }, { false, true, false } });
				}
			}

			/**
			 * Tests for format using double full line blocks. ({@code ██} represents {@code true})
			 *
			 * @author André Schulz
			 *
			 * @since 0.1.0
			 */
			@Nested
			class FullLineBlocksTest extends TestBase {

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_error_IAE_illegalCharacter_left() throws Exception {
					logTestStart();

					assertThatThrownBy(() -> parser.parseMatrix("██  α█")).isInstanceOf(IllegalArgumentException.class)
							.hasMessageContaining("α");
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_error_IAE_illegalCharacter_right() throws Exception {
					logTestStart();

					assertThatThrownBy(() -> parser.parseMatrix("██  █α")).isInstanceOf(IllegalArgumentException.class)
							.hasMessageContaining("α");
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_error_IAE_invalidFormat() throws Exception {
					logTestStart();

					assertThatThrownBy(() -> parser.parseMatrix("██  █")).isInstanceOf(IllegalArgumentException.class);
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultFalse() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("  ");

					assertThat(actual).isEqualTo(new boolean[][] { { false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultTrue() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("██");

					assertThat(actual).isEqualTo(new boolean[][] { { true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows1() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("██  ██");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows2() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("██  ██\n  ██  ");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true }, { false, true, false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows2WithCarriageReturn() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("██  ██\r\n  ██  ");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true }, { false, true, false } });
				}
			}

			/**
			 * Tests for format using half full line blocks {@code ▀▄█ }.
			 *
			 * @author André Schulz
			 *
			 * @since 0.1.0
			 */
			@Nested
			class HalfLineBlocksTest extends TestBase {

				@BeforeEach
				void beforeEach_configure() {
					parser.useHalfLineBlocks();
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultFalse() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix(" ");

					assertThat(actual).isEqualTo(new boolean[][] { { false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultTrue() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("▀");

					assertThat(actual).isEqualTo(new boolean[][] { { true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultTrue_onTwoConsecutiveRows() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("█");

					assertThat(actual).isEqualTo(new boolean[][] { { true }, { true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows1() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("▀ ▀");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows2() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("▀▄▀");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true }, { false, true, false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows3() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("▀▄▀\n▀ ▀");

					assertThat(actual).isEqualTo(
							new boolean[][] { { true, false, true }, { false, true, false }, { true, false, true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows4_permutations() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("▀▄█ \n ▀▄█");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true, false },
							{ false, true, true, false }, { false, true, false, true }, { false, false, true, true } });
				}
			}

			/**
			 * Tests for format using the boolean literals {@code true} and {@code false}.
			 *
			 * @author André Schulz
			 *
			 * @since 0.1.0
			 */
			@Nested
			class LiteralsTest extends TestBase {

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultFalse() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("false");

					assertThat(actual).isEqualTo(new boolean[][] { { false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_resultTrue() throws Exception {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("true");

					assertThat(actual).isEqualTo(new boolean[][] { { true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows1() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("true,false,true");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows2() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("true,false,true\nfalse,true,false");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true }, { false, true, false } });
				}

				/**
				 * @since 0.1.0
				 */
				@Test
				void test_rows2WithCarriageReturn() {
					logTestStart();

					boolean[][] actual = parser.parseMatrix("true,false,true\r\nfalse,true,false");

					assertThat(actual).isEqualTo(new boolean[][] { { true, false, true }, { false, true, false } });
				}
			}
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_empty() throws Exception {
			logTestStart();

			boolean[][] actual = parser.parseMatrix("");

			assertThat(actual).isEqualTo(new boolean[][] {});
		}
	}

	/**
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	abstract class TestBase extends de.voomdoon.testing.tests.TestBase {

		/**
		 * @since 0.1.0
		 */
		protected BooleanMatrixParser parser = new BooleanMatrixParser();
	}
}
