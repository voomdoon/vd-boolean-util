package de.voomdoon.util.bool.matrix;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import de.voomdoon.testing.tests.TestBase;
import de.voomdoon.util.bool.matrix.BooleanMatrixGenerator.BooleanMatrixGeneratorBuilder;
import de.voomdoon.util.bool.matrix.BooleanMatrixGenerator.BooleanMatrixSize;
import de.voomdoon.util.bool.matrix.BooleanMatrixGenerator.BooleanMatrixValueProvider;
import de.voomdoon.util.bool.matrix.BooleanMatrixGenerator.ConstantValueBooleanMatrixValueProvider;

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
	 * @since 0.1.0
	 */
	@Nested
	class BooleanMatrixSizeTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_erorr_IllegalArgumentException_columnCount_negative() throws Exception {
			logTestStart();

			assertThatThrownBy(() -> new BooleanMatrixSize(0, -1)).isInstanceOf(IllegalArgumentException.class);
		}

		/**
		 * @since 0.1.0
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

		/**
		 * DOCME add JavaDoc for BooleanMatrixGeneratorTest.BuilderTest
		 *
		 * @author André Schulz
		 *
		 * @since 0.1.0
		 */
		@Nested
		class WithValueProviderTest extends TestBase {

			/**
			 * @since 0.1.0
			 */
			@Test
			void test_result() throws Exception {
				logTestStart();

				BooleanMatrixGeneratorBuilder builder = BooleanMatrixGenerator.builder();

				BooleanMatrixGeneratorBuilder actual = builder.withValueProvider((r, c, s) -> true);

				assertThat(actual).isSameAs(builder);
			}

			/**
			 * @since 0.1.0
			 */
			@ParameterizedTest
			@ValueSource(booleans = { true, false })
			void test_supplierIsRespected(boolean value) throws Exception {
				logTestStart();

				BooleanMatrixGeneratorBuilder builder = BooleanMatrixGenerator.builder();

				builder.withSizeSupplier(() -> new BooleanMatrixSize(1, 1));
				builder.withValueProvider((r, c, s) -> value);

				BooleanMatrixGenerator generator = builder.build();

				boolean[][] actual = generator.generate();

				assertThat(actual).isEqualTo(new boolean[][] { { value } });
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
	class ConstantValueBooleanMatrixValueProviderTest extends TestBase {

		/**
		 * @since 0.1.0
		 */
		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void testGet(boolean value) throws Exception {
			logTestStart();

			ConstantValueBooleanMatrixValueProvider provider = new ConstantValueBooleanMatrixValueProvider(value);

			boolean actual = provider.get(0, 0, null);

			assertThat(actual).isEqualTo(value);
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

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_valueProvider_invocations_iColumn() throws Exception {
			logTestStart();

			BooleanMatrixValueProvider valueProvider = Mockito.spy(new ConstantValueBooleanMatrixValueProvider(false));
			BooleanMatrixSize size = new BooleanMatrixSize(2, 3);

			BooleanMatrixGenerator generator = BooleanMatrixGenerator.builder().withSizeSupplier(() -> size)
					.withValueProvider(valueProvider).build();

			generator.generate();

			verify(valueProvider, times(2)).get(anyInt(), eq(0), any());
			verify(valueProvider, times(2)).get(anyInt(), eq(1), any());
			verify(valueProvider, times(2)).get(anyInt(), eq(2), any());
			verifyNoMoreInteractions(valueProvider);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_valueProvider_invocations_iRow() throws Exception {
			logTestStart();

			BooleanMatrixValueProvider valueProvider = Mockito.spy(new ConstantValueBooleanMatrixValueProvider(false));
			BooleanMatrixSize size = new BooleanMatrixSize(2, 3);

			BooleanMatrixGenerator generator = BooleanMatrixGenerator.builder().withSizeSupplier(() -> size)
					.withValueProvider(valueProvider).build();

			generator.generate();

			verify(valueProvider, times(3)).get(eq(0), anyInt(), any());
			verify(valueProvider, times(3)).get(eq(1), anyInt(), any());
			verifyNoMoreInteractions(valueProvider);
		}

		/**
		 * @since 0.1.0
		 */
		@Test
		void test_valueProvider_invocations_size() throws Exception {
			logTestStart();

			BooleanMatrixValueProvider valueProvider = Mockito.spy(new ConstantValueBooleanMatrixValueProvider(false));
			BooleanMatrixSize size = new BooleanMatrixSize(2, 3);

			BooleanMatrixGenerator generator = BooleanMatrixGenerator.builder().withSizeSupplier(() -> size)
					.withValueProvider(valueProvider).build();

			generator.generate();

			verify(valueProvider, times(6)).get(anyInt(), anyInt(), eq(size));
			verifyNoMoreInteractions(valueProvider);
		}
	}
}
