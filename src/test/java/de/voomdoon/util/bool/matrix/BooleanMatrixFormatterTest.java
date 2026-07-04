package de.voomdoon.util.bool.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.stream.Stream;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.voomdoon.util.bool.matrix.BooleanMatrixFormatter.BooleanMatrixFormatterBuilder;
import de.voomdoon.util.bool.matrix.BooleanMatrixFormatter.Format;

/**
 * Tests for {@link BooleanMatrixFormatter}.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class BooleanMatrixFormatterTest {

	/**
	 * Tests for {@link BooleanMatrixFormatterBuilder}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class BuilderTest {

		/**
		 * Tests for {@link BooleanMatrixFormatterBuilder#build()}.
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class BuildTest {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_default() {
				BooleanMatrixFormatter formatter = new BooleanMatrixFormatterBuilder().build();

				Executable action = () -> formatter.format(new boolean[][] { { false, true } });

				assertDoesNotThrow(action);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_result() {
				BooleanMatrixFormatter formatter = new BooleanMatrixFormatterBuilder().withFormat(Format.ONE_AND_ZERO)
						.build();

				String actual = formatter.format(new boolean[][] { { false, true } });

				assertThat(actual).isEqualTo("01");
			}
		}

		/**
		 * Tests for {@link BooleanMatrixFormatter#withColumnSeparator(String)}.
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class WithColumnSeparatorTest {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IAE_null() {
				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				ThrowingCallable action = () -> builder.withColumnSeparator(null);

				assertThatThrownBy(action).isInstanceOf(NullPointerException.class).hasMessageContaining("separator");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_isRespected() {
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
			void test_result_sameInstance() {
				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				BooleanMatrixFormatterBuilder actual = builder.withColumnSeparator(" ");

				assertThat(actual).isSameAs(builder);
			}
		}

		/**
		 * Tests for {@link BooleanMatrixFormatter#withDoubleWidthBlocksFalseValue(String)}.
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class WithDoubleWidthBlocksFalseValueTest {

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
			void test_error_IAE_invalidLength(String falseValue) {
				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.DOUBLE_WIDTH_BLOCKS);

				ThrowingCallable action = () -> builder.withDoubleWidthBlocksFalseValue(falseValue);

				assertThatThrownBy(action).isInstanceOf(IllegalArgumentException.class)//
						.hasMessageContaining("length")//
						.hasMessageContaining(falseValue);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IAE_null() {
				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.DOUBLE_WIDTH_BLOCKS);

				ThrowingCallable action = () -> builder.withDoubleWidthBlocksFalseValue(null);

				assertThatThrownBy(action).isInstanceOf(NullPointerException.class)//
						.hasMessageContaining("falseValue");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IllegalStateException_canOnlyBeUsedWithDoubleWidthBlocksFormat() {
				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();
				builder.withFormat(Format.ONE_AND_ZERO);

				ThrowingCallable action = () -> builder.withDoubleWidthBlocksFalseValue("--");

				assertThatThrownBy(action).isInstanceOf(IllegalStateException.class)//
						.extracting(Throwable::getMessage).asString().containsIgnoringCase("format");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_isRespected() {
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
			void test_result_sameInstance() {
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
		class WithFormatTest {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_error_IAE_null() {
				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				ThrowingCallable action = () -> builder.withFormat(null);

				assertThatThrownBy(action).isInstanceOf(NullPointerException.class).hasMessageContaining("format");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_isRespected() {
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
			void test_result_sameInstance() {
				BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

				BooleanMatrixFormatterBuilder actual = builder.withFormat(Format.ONE_AND_ZERO);

				assertThat(actual).isSameAs(builder);
			}
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test() {
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
				String actual = format(new boolean[0][0]);

				assertThat(actual).isEmpty();
			}
		}

		/**
		 * Tests for {@link Format#DOUBLE_WIDTH_BLOCKS}.
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
			void test_rowSeparator() {
				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("██\n██");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() {
				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo("  ");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() {
				String actual = format(new boolean[][] { { true } });

				assertThat(actual).isEqualTo("██");
			}
		}

		/**
		 * Tests for error handling of {@link BooleanMatrixFormatter#format(boolean[][])}.
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
			void test_IAE_rowNull() {
				ThrowingCallable action = () -> format(new boolean[][] { null });

				assertThatThrownBy(action).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("null");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_IAE_rowsEmpty() {
				ThrowingCallable action = () -> format(new boolean[][] { {}, {} });

				assertThatThrownBy(action).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("empty");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_IAE_rowsHaveDifferentLengths() {
				ThrowingCallable action = () -> format(new boolean[][] { { true }, { true, false } });

				assertThatThrownBy(action).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("regular");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_NPE_null() {
				ThrowingCallable action = () -> format(null);

				assertThatThrownBy(action).isInstanceOf(NullPointerException.class).hasMessageContaining("matrix");
			}
		}

		/**
		 * Tests for {@link Format#HALF_BLOCKS}.
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
			void test_2Rows_falseFalse() {
				String actual = format(new boolean[][] { { false }, { false } });

				assertThat(actual).isEqualTo(" ");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_2Rows_falseTrue() {
				String actual = format(new boolean[][] { { false }, { true } });

				assertThat(actual).isEqualTo("▄");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_2Rows_trueFalse() {
				String actual = format(new boolean[][] { { true }, { false } });

				assertThat(actual).isEqualTo("▀");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_2Rows_trueTrue() {
				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("█");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_3Rows() {
				String actual = format(new boolean[][] { { true }, { true }, { true } });

				assertThat(actual).isEqualTo("█\n▀");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() {
				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo(" ");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() {
				String actual = format(new boolean[][] { { true } });

				assertThat(actual).isEqualTo("▀");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_twoRows() {
				String actual = format(new boolean[][] { { false, true, false, true }, { false, false, true, true } });

				assertThat(actual).isEqualTo(" ▀▄█");
			}
		}

		/**
		 * Tests for {@link Format#ONE_AND_ZERO}.
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
			void test_columnSeparator_none() {
				String actual = format(new boolean[][] { { true, true } });

				assertThat(actual).isEqualTo("11");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_rowSeparator() {
				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("1\n1");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() {
				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo("0");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() {
				String actual = format(new boolean[][] { { true } });

				assertThat(actual).isEqualTo("1");
			}
		}

		/**
		 * Tests for {@link Format#TRUE_AND_FALSE_WITH_SEPARATOR}.
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
			void test_columnSeparator_default() {
				String actual = format(new boolean[][] { { true, true } });

				assertThat(actual).isEqualTo("true,true");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_rowSeparator() {
				String actual = format(new boolean[][] { { true }, { true } });

				assertThat(actual).isEqualTo("true\ntrue");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_false() {
				String actual = format(new boolean[][] { { false } });

				assertThat(actual).isEqualTo("false");
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_singleValue_true() {
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
	private abstract class TestBase {

		/**
		 * @since 0.1.0
		 */
		protected BooleanMatrixFormatterBuilder builder = BooleanMatrixFormatter.builder();

		/**
		 * @param matrix
		 *            boolean matrix
		 * @return {@link String}
		 * @since 0.1.0
		 */
		protected String format(boolean[][] matrix) {
			return builder.build().format(matrix);
		}
	}
}
