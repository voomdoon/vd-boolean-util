package de.voomdoon.util.bool.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.bool.matrix.BooleanMatrixGenerator.BooleanMatrixGeneratorBuilder;
import de.voomdoon.util.bool.matrix.BooleanMatrixGenerator.BooleanMatrixSize;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
class BooleanMatrixGeneratorTest {

	/**
	 * DOCME add JavaDoc for BooleanMatrixGeneratorTest
	 *
	 * @author André Schulz
	 *
	 * @since DOCME add inception version number
	 */
	@Nested
	class BooleanMatrixSizeTest extends TestBase {

		/**
		 * @since DOCME add inception version number
		 */
		@Test
		void test_erorr_IllegalArgumentException_columnCount_negative() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> new BooleanMatrixSize(0, -1)).isInstanceOf(IllegalArgumentException.class);
		}

		/**
		 * @since DOCME add inception version number
		 */
		@Test
		void test_erorr_IllegalArgumentException_rowCount_negative() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> new BooleanMatrixSize(-1, 0)).isInstanceOf(IllegalArgumentException.class);
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixGeneratorTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class BuilderTest {

		/**
		 * DOCME add JavaDoc for BooleanMatrixGeneratorTest.BuilderTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class WithSizeSupplierTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_result() throws Exception {
				logTestStart();

				BooleanMatrixGeneratorBuilder builder = BooleanMatrixGenerator.builder();

				BooleanMatrixGeneratorBuilder actual = builder.withSizeSupplier(() -> new BooleanMatrixSize(1, 1));

				assertThat(actual).isSameAs(builder);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_supplierIsRespected() throws Exception {
				logTestStart();

				BooleanMatrixGeneratorBuilder builder = BooleanMatrixGenerator.builder();
				builder.withSizeSupplier(() -> new BooleanMatrixSize(1, 1));
				BooleanMatrixGenerator generator = builder.build();

				boolean[][] actual = generator.generate();

				assertThat(actual).hasDimensions(1, 1);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_supplierIsRespected_columnCount() throws Exception {
				logTestStart();

				BooleanMatrixGeneratorBuilder builder = BooleanMatrixGenerator.builder();
				builder.withSizeSupplier(() -> new BooleanMatrixSize(0, 1));
				BooleanMatrixGenerator generator = builder.build();

				boolean[][] actual = generator.generate();

				assertThat(actual).hasDimensions(0, 1);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_supplierIsRespected_forEmpty() throws Exception {
				logTestStart();

				BooleanMatrixGeneratorBuilder builder = BooleanMatrixGenerator.builder();
				builder.withSizeSupplier(() -> new BooleanMatrixSize(0, 0));
				BooleanMatrixGenerator generator = builder.build();

				boolean[][] actual = generator.generate();

				assertThat(actual).hasDimensions(0, 0);
			}

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_supplierIsRespected_rowCount() throws Exception {
				logTestStart();

				BooleanMatrixGeneratorBuilder builder = BooleanMatrixGenerator.builder();
				builder.withSizeSupplier(() -> new BooleanMatrixSize(1, 0));
				BooleanMatrixGenerator generator = builder.build();

				boolean[][] actual = generator.generate();

				assertThat(actual).hasDimensions(1, 0);
			}
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixGeneratorTest
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@Nested
	class GenerateTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test() {
			logTestStart();

			BooleanMatrixGenerator generator = BooleanMatrixGenerator.builder().build();

			boolean[][] actual = generator.generate();

			assertThat(actual).isNotNull();
		}
	}
}
