package de.voomdoon.util.bool;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.voomdoon.util.bool.BooleanMatrixFormatter.BooleanMatrixFormatterBuilder;
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
	 * DOCME add JavaDoc for BooleanMatrixFormatterTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class BuilderTest extends de.voomdoon.testing.tests.TestBase {

		/**
		 * DOCME add JavaDoc for BooleanMatrixFormatterTest.BuilderTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class BuildTest extends de.voomdoon.testing.tests.TestBase {

			/**
			 * DOCME add JavaDoc for method test_default_format
			 * 
			 * @since 0.1.0
			 */
			@Test
			void test_default() throws Exception {
				logTestStart();

				BooleanMatrixFormatter formatter = new BooleanMatrixFormatterBuilder().build();

				assertDoesNotThrow(() -> formatter.format(new boolean[][] { { false, true } }));
			}

			/**
			 * DOCME add JavaDoc for method test_result
			 * 
			 * @since 0.1.0
			 */
			@Test
			void test_result() throws Exception {
				logTestStart();

				BooleanMatrixFormatter formatter = new BooleanMatrixFormatterBuilder().withFormat(Format.ONE_AND_ZERO)
						.build();

				String actual = formatter.format(new boolean[][] { { false, true } });

				assertThat(actual).isEqualTo("01");
			}
		}

		/**
		 * DOCME add JavaDoc for BooleanMatrixFormatterTest.BuilderTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class WithColumnSeparatorTest extends de.voomdoon.testing.tests.TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IAE_null() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				assertThatThrownBy(() -> builder.withColumnSeparator(null)).isInstanceOf(NullPointerException.class)
						.hasMessageContaining("separator");
			}

			/**
			 * DOCME add JavaDoc for method test_isRespected
			 * 
			 * @since 0.1.0
			 */
			@Test
			void test_isRespected() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder = builder.withColumnSeparator("SEPARATOR");
				BooleanMatrixFormatter formatter = builder.build();

				String actual = formatter.format(new boolean[][] { { false, true } });

				assertThat(actual).contains("falseSEPARATORtrue");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_result_sameInstance() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				BooleanMatrixFormatterBuilder actual = builder.withColumnSeparator(" ");

				assertThat(actual).isSameAs(builder);
			}
		}

		/**
		 * DOCME add JavaDoc for BooleanMatrixFormatterTest.BuilderTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class WithDoubleWidthBlocksFalseValueTest extends de.voomdoon.testing.tests.TestBase {

			/**
			 * @return
			 * @since 0.1.0
			 */
			private static Stream<Arguments> getInvalidLengthValues() {
				return Stream.of(//
						Arguments.of((Object) ""), //
						Arguments.of((Object) "-"), //
						Arguments.of((Object) "---"));
			}

			/**
			 * @since 0.1.0
			 */
			@ParameterizedTest
			@MethodSource(value = "getInvalidLengthValues")
			void test_error_IAE_invalidLength(String falseValue) throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.DOUBLE_WIDTH_BLOCKS);

				assertThatThrownBy(() -> builder.withDoubleWidthBlocksFalseValue(falseValue))
						.isInstanceOf(IllegalArgumentException.class)//
						.hasMessageContaining("length")//
						.hasMessageContaining(falseValue);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IAE_null() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.DOUBLE_WIDTH_BLOCKS);

				assertThatThrownBy(() -> builder.withDoubleWidthBlocksFalseValue(null))
						.isInstanceOf(NullPointerException.class)//
						.hasMessageContaining("falseValue");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IllegalStateException_canOnlyBeUsedWithDoubleWidthBlocksFormat() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.ONE_AND_ZERO);

				assertThatThrownBy(() -> builder.withDoubleWidthBlocksFalseValue("--"))
						.isInstanceOf(IllegalStateException.class)//
						.extracting(Throwable::getMessage).asString().containsIgnoringCase("format");
			}

			/**
			 * DOCME add JavaDoc for method test
			 * 
			 * @since 0.1.0
			 */
			@Test
			void test_isRespected() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.DOUBLE_WIDTH_BLOCKS);
				builder.withDoubleWidthBlocksFalseValue("--");

				String actual = builder.build().format(new boolean[][] { { true, false, true } });

				assertThat(actual).isEqualTo("██--██");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_result_sameInstance() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.DOUBLE_WIDTH_BLOCKS);

				BooleanMatrixFormatterBuilder actual = builder.withDoubleWidthBlocksFalseValue("--");

				assertThat(actual).isSameAs(builder);
			}
		}

		/**
		 * Tests for {@link BooleanMatrixFormatter#withFormat(Format)}.
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class WithFormatTest extends de.voomdoon.testing.tests.TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IAE_null() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				assertThatThrownBy(() -> builder.withFormat(null)).isInstanceOf(NullPointerException.class)
						.hasMessageContaining("format");
			}

			/**
			 * DOCME add JavaDoc for method test_isRespected
			 * 
			 * @since 0.1.0
			 */
			@Test
			void test_isRespected() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder = builder.withFormat(Format.ONE_AND_ZERO);
				BooleanMatrixFormatter formatter = builder.build();

				String actual = formatter.format(new boolean[][] { { false, true } });

				assertThat(actual).isEqualTo("01");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_result_sameInstance() throws Exception {
				logTestStart();

				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				BooleanMatrixFormatterBuilder actual = builder.withFormat(Format.ONE_AND_ZERO);

				assertThat(actual).isSameAs(builder);
			}
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test() throws Exception {
			logTestStart();

			BooleanMatrixFormatterBuilder actual = BooleanMatrixFormatter.builder();

			assertThat(actual).isNotNull();
		}
	}

	/**
	 * Tests for {@link BooleanMatrixFormatter#format(boolean[][])}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class FormatTests {

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

				String actual = format(new boolean[0][0]);

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
		class DoubleWidthBlocksFormatTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@BeforeEach
			void beforeEach_configureFormatter() {
				builder.withFormat(Format.DOUBLE_WIDTH_BLOCKS);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_rowSeparator() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("██\n██");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo("  ");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true } });

				assertThat(actual).isEqualTo("██");
			}
		}

		/**
		 * DOCME add JavaDoc for BooleanMatrixFormatterTest.FormatTests
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class ErrorHandlingTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_IAE_rowNull() throws Exception {
				logTestStart();

				assertThatThrownBy(() -> format(new boolean[][] { null })).isInstanceOf(IllegalArgumentException.class)
						.hasMessageContaining("null");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_IAE_rowsEmpty() throws Exception {
				logTestStart();

				assertThatThrownBy(() -> format(new boolean[][] { {}, {} }))
						.isInstanceOf(IllegalArgumentException.class).hasMessageContaining("empty");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_IAE_rowsHaveDifferentLengths() throws Exception {
				logTestStart();

				assertThatThrownBy(() -> format(new boolean[][] { { true }, { true, false } }))
						.isInstanceOf(IllegalArgumentException.class).hasMessageContaining("regular");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_NPE_null() throws Exception {
				logTestStart();

				assertThatThrownBy(() -> format(null)).isInstanceOf(NullPointerException.class)
						.hasMessageContaining("matrix");
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
		class HalfBlocksFormatTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@BeforeEach
			void beforeEach_configureFormatter() {
				builder.withFormat(Format.HALF_BLOCKS);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_2Rows_falseFalse() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { false }, { false } });

				assertThat(actual).isEqualTo(" ");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_2Rows_falseTrue() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { false }, { true } });

				assertThat(actual).isEqualTo("▄");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_2Rows_trueFalse() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true }, { false } });

				assertThat(actual).isEqualTo("▀");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_2Rows_trueTrue() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("█");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_3Rows() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true }, { true }, { true } });

				assertThat(actual).isEqualTo("█\n▀");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo(" ");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true } });

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

				String actual = format(new boolean[][] { { false, true, false, true }, { false, false, true, true } });

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
		class OneZeroFormatTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@BeforeEach
			void beforeEach_configureFormatter() {
				builder.withFormat(Format.ONE_AND_ZERO);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_columnSeparator_none() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true, true } });

				assertThat(actual).isEqualTo("11");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_rowSeparator() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("1\n1");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo("0");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true } });

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
		class TrueFalseFormatTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_columnSeparator_default() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true, true } });

				assertThat(actual).isEqualTo("true,true");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_rowSeparator() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("true\ntrue");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo("false");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() throws Exception {
				logTestStart();

				String actual = format(new boolean[][] { { true } });

				assertThat(actual).isEqualTo("true");
			}

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
		protected BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

		/**
		 * DOCME add JavaDoc for method format
		 * 
		 * @param matrix
		 * @return
		 * @since 0.1.0
		 */
		protected String format(boolean[][] matrix) {
			return builder.build().format(matrix);
		}
	}
}
