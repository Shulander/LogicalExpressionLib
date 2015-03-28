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

    @Override
    public Comparable getValue() {
        if(dataSource != null) {
            return dataSource.getValue(parameterName);
        }
        return null;
    }
    
}
