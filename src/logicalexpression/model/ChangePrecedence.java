package logicalexpression.model;

/**
 *
 * @author Shulander
 */
public class ChangePrecedence implements ITokenIdentifier, IToken {

	private static int balance = 0;
	private final EPrecedenceTokens precedence;

	private ChangePrecedence(EPrecedenceTokens precedence) {
		this.precedence = precedence;
		if (precedence.equals(EPrecedenceTokens.OPEN)) {
			balance++;
		} else {
			balance--;
		}
	}

	@Override
	public boolean isOperand() {
		return false;
	}

	@Override
	public boolean isOperator() {
		return false;
	}

	@Override
	public boolean isChangePrecedence() {
		return true;
	}

	public static ChangePrecedence build(String value) {
		EPrecedenceTokens precedence = EPrecedenceTokens.parseString(value);
		if (precedence == null) {
			return null;
		}
		ChangePrecedence returnValue = new ChangePrecedence(precedence);
		return returnValue;
	}

	public static boolean isBalanced() {
		return balance == 0;
	}

	public enum EPrecedenceTokens {

		OPEN("("),
		CLOSE(")");
		private String value;

		private EPrecedenceTokens(String value) {
			this.value = value;
		}

		public static EPrecedenceTokens parseString(String str) {
			for (EPrecedenceTokens operator : EPrecedenceTokens.values()) {
				if (operator.value.compareTo(str.toUpperCase()) == 0) {
					return operator;
				}
			}
			return null;
		}
	}

	public EPrecedenceTokens getPrecedence() {
		return precedence;
	}

}
