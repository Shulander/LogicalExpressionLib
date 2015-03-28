package logicalexpression.model.operand;

import logicalexpression.model.IToken;
import logicalexpression.model.ITokenIdentifier;
import logicalexpression.model.ITokenProcessor;

/**
 *
 * @author Shulander
 */
public abstract class Operand implements ITokenIdentifier, IToken{
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
            returnValue = new NumericalOperand(value);
        } else if("true".equals(value.toLowerCase())||"false".equals(value.toLowerCase())) {
            returnValue = new BooleanOperand(value);
        } else if(value.matches("[\\p{Alnum}]+")) {
            returnValue = new ParameterOperand(value);
        }
        return returnValue;
    }
    
    public static Operand build(boolean value) {
        Operand returnValue = null;
        returnValue = new BooleanOperand(value);
        return returnValue;
    }
    
    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public abstract Comparable getValue();
}
