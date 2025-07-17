package de.voomdoon.util.bool.matrix;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Generator for boolean matrices.
 *
 * @author André Schulz
 *
 * @since 0.1.0
 */
public class BooleanMatrixGenerator {

	/**
	 * Builder for {@link BooleanMatrixGenerator}.
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
		 * Creates a new builder for {@link BooleanMatrixGenerator}.
		 * 
		 * @since 0.1.0
		 */
		private BooleanMatrixGeneratorBuilder() {
			// hidden
		}

		/**
		 * Builds the {@link BooleanMatrixGenerator} using the configured suppliers.
		 * 
		 * @since 0.1.0
		 */
		public BooleanMatrixGenerator build() {
			return new BooleanMatrixGenerator(
					Optional.ofNullable(sizeSupplier).orElseGet(() -> new EmptyBooleanMatrixSizeSupplier()),
					Optional.ofNullable(valueProvider).orElse(new ConstantValueBooleanMatrixValueProvider(false)));
		}

		/**
		 * Sets the supplier for the matrix size.
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
		 * Sets the provider for generating boolean values in the matrix.
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
	 * Supplies the size of a matrix.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public interface BooleanMatrixSizeSupplier extends Supplier<BooleanMatrixSize> {

	}

	/**
	 * Provides the boolean value for the specified cell in the matrix.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	@FunctionalInterface
	public interface BooleanMatrixValueProvider {

		/**
		 * Provides the boolean value for the specified cell in the matrix.
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
	 * {@link BooleanMatrixValueProvider} with constant value.
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
		 * @param value
		 *            The constant value to return for all cells in the matrix.
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
	 * {@link BooleanMatrixSizeSupplier} returning {@link BooleanMatrixSize} representing empty matrix.
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
	 * Definition for the size of the matrix to generate.
	 *
	 * @author André Schulz
	 *
	 * @since 0.1.0
	 */
	public record BooleanMatrixSize(int rowCount, int columnCount) {

		/**
		 * @param rowCount
		 *            the amount of rows
		 * @param columnCount
		 *            the amount of columns
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
	 * Returns a new {@link BooleanMatrixGeneratorBuilder}.
	 * 
	 * @return {@link BooleanMatrixGeneratorBuilder}
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
	 * @param sizeSupplier
	 * @param valueProvider
	 * @since 0.1.0
	 */
	private BooleanMatrixGenerator(BooleanMatrixSizeSupplier sizeSupplier, BooleanMatrixValueProvider valueProvider) {
		this.sizeSupplier = sizeSupplier;
		this.valueProvider = valueProvider;
	}

	/**
	 * Generates a boolean matrix using the configured size supplier and value provider.
	 * 
	 * @return boolean matrix
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
