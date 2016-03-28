/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.model.operand;

import java.util.Objects;

/**
 *
 * @author Shulander
 */
class BooleanOperand extends Operand {

	private final Boolean booleanValue;

	public static final BooleanOperand TRUE;
	public static final BooleanOperand FALSE;

	static {
		TRUE = new BooleanOperand(true);
		FALSE = new BooleanOperand(false);
	}

	private BooleanOperand(boolean value) {
		this.booleanValue = value;
	}

	public static BooleanOperand getBooleanOperandInstance(String value) {
		return getBooleanOperandInstance(Boolean.valueOf(value));
	}

	public static BooleanOperand getBooleanOperandInstance(boolean value) {
		return value ? TRUE : FALSE;
	}

	@Override
	public int compareTo(Operand o) {
		if (o == null) {
			return 1;
		}
		if (o instanceof BooleanOperand) {
			return booleanValue.compareTo(((BooleanOperand) o).booleanValue);
		} else {
			return this.toString().compareTo(o.toString());
		}
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.booleanValue);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BooleanOperand)) {
			return false;
		}
		final BooleanOperand other = (BooleanOperand) obj;
		return Objects.equals(this.booleanValue, other.booleanValue);
	}

	@Override
	public String toString() {
		return booleanValue ? "TRUE" : "FALSE";
	}
}
