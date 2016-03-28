/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.model.operand;

import java.text.NumberFormat;

/**
 *
 * @author Shulander
 */
abstract class NumericalOperand extends Operand {

	protected static NumberFormat nf;

	static {
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
	}

	public static NumericalOperand buildNumericalOperand(String value) {
		if (value.matches("[0-9]+(.[0-9]+)*")) {
			return new FloatOperand(Float.parseFloat(value));
		} else {
			return new IntegerOperand(Integer.parseInt(value));
		}
	}

	static Operand buildNumericalOperand(Number value) {
		if (value instanceof Float) {
			return new FloatOperand((Float) value);
		} else if (value instanceof Integer) {
			return new IntegerOperand((Integer) value);
		} else {
			return buildNumericalOperand(nf.format(value));
		}
	}

	static private class FloatOperand extends NumericalOperand {

		Float value;

		FloatOperand(Float value) {
			this.value = value;
		}

		@Override
		public int compareTo(Operand o) {
			if (o == null) {
				return 1;
			}
			if (o instanceof IntegerOperand) {
				return Float.compare(value, ((IntegerOperand) o).value);
			} else if (o instanceof FloatOperand) {
				return Float.compare(value, ((FloatOperand) o).value);
			} else {
				return this.getClass().getName().compareTo(o.getClass().getName());
			}
		}

		@Override
		public String toString() {
			return nf.format(value);
		}
	}

	static private class IntegerOperand extends NumericalOperand {

		Integer value;

		IntegerOperand(Integer value) {
			this.value = value;
		}

		@Override
		public int compareTo(Operand o) {
			if (o == null) {
				return 1;
			}
			if (o instanceof IntegerOperand) {
				return Integer.compare(value, ((IntegerOperand) o).value);
			} else if (o instanceof FloatOperand) {
				return Float.compare(value, ((FloatOperand) o).value);
			} else {
				return this.toString().compareTo(o.toString());
			}
		}

		@Override
		public String toString() {
			return value.toString();
		}
	}

}
