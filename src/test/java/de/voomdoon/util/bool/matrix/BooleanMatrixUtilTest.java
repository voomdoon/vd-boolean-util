package de.voomdoon.util.bool.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.bool.matrix.BooleanMatrixFormatter.Format;

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

	/**
	 * Tests for {@link BooleanMatrixUtil#getTrimmed(boolean[][])}.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class GetTrimmedTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		private static final BooleanMatrixFormatter FORMATTER = BooleanMatrixFormatter.builder()
				.withFormat(Format.DOUBLE_WIDTH_BLOCKS).build();

		/**
		 * @return
		 * @since 0.1.0
		 */
		private static Stream<Arguments> getColumnsAtBeginning() {
			return Stream.of(//
					Arguments.of((Object) new boolean[][] { //
							{ false, true } }), //
					Arguments.of((Object) new boolean[][] { //
							{ false, false, true } }));
		}

		/**
		 * @return
		 * @since 0.1.0
		 */
		private static Stream<Arguments> getColumnsAtEnd() {
			return Stream.of(//
					Arguments.of((Object) new boolean[][] { //
							{ true, false } }), //
					Arguments.of((Object) new boolean[][] { //
							{ true, false, false } }));
		}

		/**
		 * @return
		 * @since 0.1.0
		 */
		private static Stream<Arguments> getEmptyRowsAtBeginning() {
			return Stream.of(//
					Arguments.of((Object) new boolean[][] { //
							{ false }, //
							{ true } }), //
					Arguments.of((Object) new boolean[][] { //
							{ false }, //
							{ false }, //
							{ true } }));
		}

		/**
		 * @return
		 * @since 0.1.0
		 */
		private static Stream<Arguments> getEmptyRowsAtEnd() {
			return Stream.of(//
					Arguments.of((Object) new boolean[][] { //
							{ true }, //
							{ false } }), //
					Arguments.of((Object) new boolean[][] { //
							{ true }, //
							{ false }, //
							{ false } }));
		}

		/**
		 * @return
		 * @since 0.1.0
		 */
		private static Stream<Arguments> getFalseInBetween() {
			return Stream.of(//
					Arguments.of((Object) new boolean[][] { { true, false, true } }), //
					Arguments.of((Object) new boolean[][] { //
							{ true }, //
							{ false }, //
							{ true } }), //
					Arguments.of((Object) new boolean[][] { { true, false, false, true } }), //
					Arguments.of((Object) new boolean[][] { //
							{ true, true, true }, //
							{ true, false, true }, //
							{ true, true, true } }));
		}

		/**
		 * @return
		 * @since 0.1.0
		 */
		private static Stream<Arguments> getTrueMatrices() {
			return Stream.of(//
					Arguments.of((Object) new boolean[][] { { true } }),
					Arguments.of((Object) new boolean[][] { { true, true } }),
					Arguments.of((Object) new boolean[][] { { true }, { true } }),
					Arguments.of((Object) new boolean[][] { { true, true }, { true, true } }));
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_empty_resultEmptyColumn() throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(new boolean[][] { {} });

			assertThat(actual).isEmpty();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_empty_resultEmptyRows() throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(new boolean[][] {});

			assertThat(actual).isEmpty();
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getColumnsAtBeginning")
		void test_emptyColumnsAtBeginning_areRemoved(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			logger.debug("actual:\n" + BooleanMatrixFormatter.builder().build().format(actual));

			assertThat(actual.length).isEqualTo(matrix.length);

			for (int iRow = 0; iRow < actual.length; iRow++) {
				assertThat(actual[iRow].length).describedAs(FORMATTER.format(actual)).isEqualTo(1);
			}
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getColumnsAtEnd")
		void test_emptyColumnsAtEnd_areRemoved(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			logger.debug("actual:\n" + BooleanMatrixFormatter.builder().build().format(actual));

			assertThat(actual.length).isEqualTo(matrix.length);

			for (int iRow = 0; iRow < actual.length; iRow++) {
				assertThat(actual[iRow].length).describedAs(FORMATTER.format(actual)).isEqualTo(1);
			}
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getEmptyRowsAtBeginning")
		void test_emptyRowsAtBeginning_areRemoved(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			logger.debug("actual:\n" + BooleanMatrixFormatter.builder().build().format(actual));

			assertThat(actual.length).isEqualTo(1);
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getEmptyRowsAtEnd")
		void test_emptyRowsAtEnd_areRemoved(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			logger.debug("actual:\n" + BooleanMatrixFormatter.builder().build().format(actual));

			assertThat(actual.length).describedAs(FORMATTER.format(actual)).isEqualTo(1);
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getFalseInBetween")
		void test_falseInBetween_resultEqual(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			assertThat(actual).isEqualTo(matrix);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_falseOnly_resultEmpty() throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(new boolean[][] { { false } });

			assertThat(actual).isEmpty();
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getTrueMatrices")
		void test_trueOnly_resultEqual(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			assertThat(actual).isEqualTo(matrix);
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getTrueMatrices")
		void test_trueOnly_resultNotSame(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			assertThat(actual).isNotSameAs(matrix);
		}

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@MethodSource(value = "getTrueMatrices")
		void test_trueOnly_resultRowsNotSame(boolean[][] matrix) throws Exception {
			logTestStart();

			boolean[][] actual = BooleanMatrixUtil.getTrimmed(matrix);

			assertThat(actual[0]).isNotSameAs(matrix[0]);
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixUtilTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class IsAllFalseTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsTrueAtFirstRow_resultFalse() throws Exception {
			logTestStart();

			boolean actual = BooleanMatrixUtil.isAllFalse(new boolean[][] { { true } });

			assertThat(actual).isFalse();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_containsTrueAtSecondRow_resultFalse() throws Exception {
			logTestStart();

			boolean actual = BooleanMatrixUtil.isAllFalse(new boolean[][] { {}, { true } });

			assertThat(actual).isFalse();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_empty_resultTrue() throws Exception {
			logTestStart();

			boolean actual = BooleanMatrixUtil.isAllFalse(new boolean[][] {});

			assertThat(actual).isTrue();
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_NPE_columnNull() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> BooleanMatrixUtil.isAllFalse(new boolean[][] { null }))
					.isInstanceOf(NullPointerException.class).hasMessageContaining("row").hasMessageContaining("0");
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_error_NPE_matrixNull() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> BooleanMatrixUtil.isAllFalse(null)).isInstanceOf(NullPointerException.class)
					.hasMessageContaining("matrix");
		}
	}
}
