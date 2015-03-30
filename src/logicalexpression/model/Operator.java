package logicalexpression.model;

import logicalexpression.model.operand.Operand;

/**
 *
 * @author Shulander
 */
public class Operator implements ITokenIdentifier, ITokenProcessor, IToken {
    private final EOperator operator;
    private Operand operandA = null;
    private Operand operandB = null;
    
    private Operator(EOperator operator) {
        this.operator = operator;
    }

    @Override
    public boolean isOperand() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public boolean isChangePrecedence() {
        return false;
    }

    public static Operator build(String value) {
        EOperator operator=  EOperator.parseString(value);
        if (operator == null) {
            return null;
        }
        Operator returnValue = new Operator(operator);
        return returnValue;
    }

	public void setOperandA(Operand operand){
		operandA = operand;
	}

	public void setOperandB(Operand operand){
		operandB = operand;
	}

    public EOperator getOperator() {
        return operator;
    }
    
    @Override
    public Operand process() {
        Comparable a = operandA!=null?operandA.getValue():null;
        Comparable b = operandB!=null?operandB.getValue():null;
        return Operand.build(operator.compare(a, b));
    }
}
