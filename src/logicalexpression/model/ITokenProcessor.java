package logicalexpression.model;

import logicalexpression.model.operand.Operand;

/**
 *
 * @author Shulander
 */
public interface ITokenProcessor {
    
    public Operand process();
}
