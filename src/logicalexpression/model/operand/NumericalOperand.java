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
class NumericalOperand extends Operand {
    private final Comparable numericalValue;

    public NumericalOperand(String value) {
        if(value.matches("[0-9]+(.[0-9]+)*")) {
            numericalValue = Float.parseFloat(value);
        } else {
            numericalValue = Integer.parseInt(value);
        }
    }

    @Override
    public Comparable getValue() {
        return numericalValue;
    }
    
}
