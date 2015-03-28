/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.model.operand;

/**
 *
 * @author Shulander
 */
class BooleanOperand extends Operand {
    private final Boolean booleanValue;
    
    BooleanOperand(String value) {
        this.booleanValue = Boolean.valueOf(value);
    }

    BooleanOperand(boolean value) {
        this.booleanValue = value;
    }

    @Override
    public Comparable getValue() {
        return booleanValue;
    }
}
