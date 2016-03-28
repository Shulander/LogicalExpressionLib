package logicalexpression.model.operand;

/**
 *
 * @author Shulander
 */
class StringOperand extends Operand {

	private String stringValue;

	StringOperand(String value) {
		stringValue = (value.startsWith("'") || value.startsWith("\"") ? value.substring(1) : value);
		stringValue = (stringValue.endsWith("'") || stringValue.endsWith("\"") ? stringValue.substring(0, stringValue.length() - 1) : stringValue);
	}

	@Override
	public int compareTo(Operand o) {
		if (o == null) {
			return 1;
		}
		if (o instanceof StringOperand) {
			return stringValue.compareTo(((StringOperand) o).stringValue);
		} else {
			return this.toString().compareTo(o.toString());
		}
	}

	@Override
	public String toString() {
		return stringValue;
	}
}
