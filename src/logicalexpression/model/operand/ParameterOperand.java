package logicalexpression.model.operand;

/**
 *
 * @author Shulander
 */
class ParameterOperand extends Operand {
    private final String parameterName;

    public ParameterOperand(String value) {
        this.parameterName = value;
    }

    protected Comparable getValue() {
        if(dataSource != null) {
            return dataSource.getValue(parameterName);
        }
        return null;
    }

	@Override
	public int compareTo(Operand o)
	{
		if(o == null) {
			return 1;
		}
		if(o instanceof ParameterOperand) {
			ParameterOperand other = (ParameterOperand) o;
			Comparable thisValue = getValue();
			Comparable otherValue = other.getValue();
			return thisValue == null? otherValue == null?0:-1 : thisValue.compareTo(otherValue);
		} else {
			return this.toString().compareTo(o.toString());
		}
	}
    
	@Override
	public String toString() {
		Comparable value = getValue();
		if(value == null)
			return "[NULL] for: "+parameterName;
		return value.toString();
	}
	
}
