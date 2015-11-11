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

    protected Comparable<?> getValue() {
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
		
		
		Comparable<?> thisValue = getValue();
		Operand o1 = convert(thisValue);
		Operand o2 = o;
		if(o instanceof ParameterOperand) {
			ParameterOperand other = (ParameterOperand) o;
			Comparable<?> otherValue = other.getValue();
			o2 = convert(otherValue);
			
			if(thisValue == null && otherValue == null) {
				return 0;
			}
		}
		
		return o1.compareTo(o2);
	}
	
	private Operand convert(Object value) {
		if(value == null){
			return null;
		}
		if(value instanceof String) {
			return Operand.build("'"+((String) value)+"'");
		} else if(value instanceof Number) {
			return Operand.build((Number) value);
		} else if(value instanceof Boolean) {
			return Operand.build((Boolean) value);
		} else {
			return Operand.build(value.toString());
		}
	}
    
	@Override
	public String toString() {
		Comparable<?> value = getValue();
		if(value == null) {
			return "[NULL] for: "+parameterName;
		}
		return value.toString();
	}
	
	public String getParameterName() {
		return parameterName;
	}
}
