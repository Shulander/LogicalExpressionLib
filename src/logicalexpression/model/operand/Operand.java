package logicalexpression.model.operand;

import logicalexpression.model.IToken;
import logicalexpression.model.ITokenIdentifier;

/**
 *
 * @author Shulander
 */
public abstract class Operand implements ITokenIdentifier, IToken, Comparable<Operand> {
    protected IDataSource dataSource;

    @Override
    public boolean isOperand() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }

    @Override
    public boolean isChangePrecedence() {
        return false;
    }
    
    public static Operand build(String value) {
        Operand returnValue = null;
        if(value.startsWith("'") || value.startsWith("\"")) {
            returnValue = new StringOperand(value);
        } else if(value.matches("[0-9]+(.[0-9]+)*")) {
            returnValue = NumericalOperand.buildNumericalOperand(value);
        } else if("true".equals(value.toLowerCase())||"false".equals(value.toLowerCase())) {
            returnValue = BooleanOperand.getBooleanOperandInstance(value);
        } else if(value.matches("[\\p{Alnum}]+")) {
            returnValue = new ParameterOperand(value);
        }
        return returnValue;
    }
	
	public static Operand build(Number value) {
		return NumericalOperand.buildNumericalOperand(value);
	}
    
    public static Operand build(boolean value) {
        return BooleanOperand.getBooleanOperandInstance(value);
    }
    
    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
