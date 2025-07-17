package de.voomdoon.util.bool.matrix;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * DOCME add JavaDoc for
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class BooleanMatrixGenerator {

	/**
	 * DOCME add JavaDoc for BooleanMatrixGenerator
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class BooleanMatrixGeneratorBuilder {

		/**
		 * @since 0.1.0
		 */
		private BooleanMatrixSizeSupplier sizeSupplier;

		/**
		 * @since 0.1.0
		 */
		private BooleanMatrixValueProvider valueProvider;

		/**
		 * DOCME add JavaDoc for constructor BooleanMatrixGeneratorBuilder
		 * 
		 * @since 0.1.0
		 */
		private BooleanMatrixGeneratorBuilder() {
			// hidden
		}

		/**
		 * DOCME add JavaDoc for method build
		 * 
		 * @since 0.1.0
		 */
		public BooleanMatrixGenerator build() {
			return new BooleanMatrixGenerator(
					Optional.ofNullable(sizeSupplier).orElseGet(() -> new EmptyBooleanMatrixSizeSupplier()),
					Optional.ofNullable(valueProvider).orElse(new ConstantValueBooleanMatrixValueProvider(false)));
		}

		/**
		 * DOCME add JavaDoc for method withSizeSupplier
		 * 
		 * @param sizeSupplier
		 * @return
		 * @since 0.1.0
		 */
		public BooleanMatrixGeneratorBuilder withSizeSupplier(BooleanMatrixSizeSupplier sizeSupplier) {
			this.sizeSupplier = sizeSupplier;

			return this;
		}

		/**
		 * DOCME add JavaDoc for method withValueProvider
		 * 
		 * @param valueProvider
		 * @return
		 * @since 0.1.0
		 */
		public BooleanMatrixGeneratorBuilder withValueProvider(BooleanMatrixValueProvider valueProvider) {
			this.valueProvider = valueProvider;

			return this;
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixGenerator
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public interface BooleanMatrixSizeSupplier extends Supplier<BooleanMatrixSize> {

	}

	/**
	 * 
	 * DOCME add JavaDoc for BooleanMatrixGenerator
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@FunctionalInterface
	public interface BooleanMatrixValueProvider {

		/**
		 * DOCME add JavaDoc for method get
		 * 
		 * @param row
		 * @param column
		 * @param size
		 * @return
		 * @since 0.1.0
		 */
		boolean get(int row, int column, BooleanMatrixSize size);
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixGenerator
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class ConstantValueBooleanMatrixValueProvider implements BooleanMatrixValueProvider {

		/**
		 * @since 0.1.0
		 */
		private boolean value;

		/**
		 * DOCME add JavaDoc for constructor BooleanMatrixGenerator.ConstantValueBooleanMatrixValueProvider
		 * 
		 * @param value
		 * @since 0.1.0
		 */
		public ConstantValueBooleanMatrixValueProvider(boolean value) {
			this.value = value;
		}

		/**
		 * @since 0.1.0
		 */
		@Override
		public boolean get(int row, int column, BooleanMatrixSize size) {
			return value;
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixGenerator
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public static class EmptyBooleanMatrixSizeSupplier implements BooleanMatrixSizeSupplier {

		/**
		 * @since 0.1.0
		 */
		@Override
		public BooleanMatrixSize get() {
			return new BooleanMatrixSize(0, 0);
		}
	}

	/**
	 * DOCME add JavaDoc for BooleanMatrixGenerator
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public record BooleanMatrixSize(int rowCount, int columnCount) {

		/**
		 * DOCME add JavaDoc for constructor BooleanMatrixGenerator.BooleanMatrixSize
		 * 
		 * @since 0.1.0
		 */
		public BooleanMatrixSize(int rowCount, int columnCount) {
			if (rowCount < 0) {
				throw new IllegalArgumentException("rowCount must not be negative: " + rowCount);
			} else if (columnCount < 0) {
				throw new IllegalArgumentException("columnCount must not be negative: " + columnCount);
			}

			this.rowCount = rowCount;
			this.columnCount = columnCount;
		}
	}

	/**
	 * DOCME add JavaDoc for method builder
	 * 
	 * @since 0.1.0
	 */
	public static BooleanMatrixGeneratorBuilder builder() {
		return new BooleanMatrixGeneratorBuilder();
	}

	/**
	 * @since 0.1.0
	 */
	private BooleanMatrixSizeSupplier sizeSupplier;

	/**
	 * @since 0.1.0
	 */
	private BooleanMatrixValueProvider valueProvider;

	/**
	 * DOCME add JavaDoc for constructor BooleanMatrixGenerator
	 * 
	 * @param sizeSupplier
	 * @param valueProvider
	 * @since 0.1.0
	 */
	public BooleanMatrixGenerator(BooleanMatrixSizeSupplier sizeSupplier, BooleanMatrixValueProvider valueProvider) {
		this.sizeSupplier = sizeSupplier;
		this.valueProvider = valueProvider;
	}

	/**
	 * DOCME add JavaDoc for method generate
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public boolean[][] generate() {
		BooleanMatrixSize size = sizeSupplier.get();

		boolean[][] result = new boolean[size.rowCount()][size.columnCount()];

		for (int iRow = 0; iRow < size.rowCount(); iRow++) {
			for (int iColumn = 0; iColumn < size.columnCount(); iColumn++) {
				result[iRow][iColumn] = valueProvider.get(iRow, iColumn, size);
			}
		}

		return result;
	}
}
