package logicalexpression.model.operand;

/**
 *
 * @author Shulander
 */
public class StringOperand extends Operand {
    private String stringValue;

    StringOperand(String value) {
        stringValue = (value.startsWith("'") || value.startsWith("\"")?value.substring(1):value);
        stringValue = (stringValue.endsWith("'") || value.endsWith("\"")?value.substring(0, value.length()-1):stringValue);
    }

    @Override
    public Comparable getValue() {
        return stringValue;
    }
}
