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

		private BooleanMatrixSizeSupplier sizeSupplier;

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
					Optional.ofNullable(sizeSupplier).orElseGet(() -> new EmptyBooleanMatrixSizeSupplier()));
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
		 * @since DOCME add inception version number
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
	 * DOCME add JavaDoc for constructor BooleanMatrixGenerator
	 * 
	 * @param sizeSupplier
	 * @since 0.1.0
	 */
	public BooleanMatrixGenerator(BooleanMatrixSizeSupplier sizeSupplier) {
		this.sizeSupplier = sizeSupplier;
	}

	/**
	 * DOCME add JavaDoc for method generate
	 * 
	 * @return
	 * @since 0.1.0
	 */
	public boolean[][] generate() {
		BooleanMatrixSize size = sizeSupplier.get();

		return new boolean[size.rowCount()][size.columnCount()];
		// TODO implement generate
	}
}
