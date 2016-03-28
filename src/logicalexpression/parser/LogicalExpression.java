package logicalexpression.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import logicalexpression.exception.ParseException;
import logicalexpression.exception.ProcessException;
import logicalexpression.model.ChangePrecedence;
import logicalexpression.model.ChangePrecedence.EPrecedenceTokens;
import logicalexpression.model.EOperator;
import logicalexpression.model.IToken;
import logicalexpression.model.ITokenIdentifier;
import logicalexpression.model.Operator;
import logicalexpression.model.operand.IDataSource;
import logicalexpression.model.operand.Operand;

/**
 *
 * @author Shulander
 */
public class LogicalExpression implements IDataSource {

	private List<IToken> tokenList;
	private Map<String, Comparable<?>> parameters;
	private IDebugListener debugListener;
	private int nProcessToken;

	public LogicalExpression() {
		tokenList = new ArrayList<>();
		parameters = new HashMap<>();
	}

	public void setDebugListener(IDebugListener debugListener) {
		this.debugListener = debugListener;
	}

	public void parseInputString(String str) throws ParseException {
		tokenList.clear();
		str = str.replace("(", " ( ").replace(")", " ) ");

		Map<String, String> stringTokens = new HashMap<>();
		int i = 0;
		int iniIndex = -1;
		while ((iniIndex = str.indexOf('\'')) >= 0) {
			int endIndex = str.indexOf('\'', iniIndex + 1);
			String newIndex = String.format("IDX%03d", ++i);
			String subString = str.substring(iniIndex, endIndex + 1);
			str = str.replace(subString, newIndex);
			stringTokens.put(newIndex, subString);
		}

		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens()) {
			String newToken = st.nextToken();
			newToken = stringTokens.containsKey(newToken) ? stringTokens.get(newToken) : newToken;
			parseToken(newToken);
		}
		if (!ChangePrecedence.isBalanced()) {
			throw new ParseException("the number of open parentesis does not maches the close number, please review your expression!");
		}
	}

	public Boolean process(Map<String, Comparable<?>> parameters) throws ProcessException {
		if (parameters != null) {
			this.parameters = parameters;
		}
		nProcessToken = 0;
		Operand op = process();

		return op.compareTo(Operand.build(true)) == 0;
	}

	private Operand process() throws ProcessException {

		Stack<Operand> operandStack = new Stack<>();
		Stack<Operator> operatorStack = new Stack<>();

		boolean doneHere = false;

		while (nProcessToken < tokenList.size() && !doneHere) {
			ITokenIdentifier token = (ITokenIdentifier) tokenList.get(nProcessToken++);
			if (token.isChangePrecedence()) {
				if (((ChangePrecedence) token).getPrecedence().equals(EPrecedenceTokens.OPEN)) {
					operandStack.push(process());
				} else {
					doneHere = true;
				}
			} else if (token.isOperand()) {
				operandStack.push((Operand) token);
			} else if (token.isOperator()) {
				if (((Operator) token).getOperator().equals(EOperator.NOT)) {
					operandStack.push(processNot());
				} else {
					operatorStack.push((Operator) token);
				}
			}

			if (operandStack.size() >= 2 && operatorStack.size() >= 1) {
				operandStack.push(processOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
			}
		}

		while (operandStack.size() >= 2 && operatorStack.size() >= 1) {
			operandStack.push(processOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
		}

		if (operandStack.size() == 1) {
			return operandStack.pop();
		} else {
			throw new ProcessException("There is a problem processing the expression, there are operands or operators left.");
		}
	}

	private Operand processNot() throws ProcessException {
		Operand returnValue = null;
		if (nProcessToken < tokenList.size()) {
			ITokenIdentifier token = (ITokenIdentifier) tokenList.get(nProcessToken++);
			if (token.isChangePrecedence()) {
				if (((ChangePrecedence) token).getPrecedence().equals(EPrecedenceTokens.OPEN)) {
					returnValue = process();
				} else {
					throw new ProcessException("expecting and operand or open parentesis ('('), found a ')'");
				}
			} else if (token.isOperand()) {
				returnValue = (Operand) token;
			} else if (token.isOperator()) {
				if (((Operator) token).getOperator().equals(EOperator.NOT)) {
					returnValue = processNot();
				} else {
					throw new ProcessException("expecting and operand or open parentesis ('('), found an operator");
				}
			}
		} else {
			throw new ProcessException("expecting and operand or open parentesis ('(')");
		}

		Operand returnValue2 = EOperator.NOT.compare(returnValue, null);
		logDebug("Process: " + EOperator.NOT + " " + returnValue + "; Result: " + returnValue2);
		return returnValue2;
	}

	private Operand processOperator(Operator operator, Operand operandA, Operand operandB) {
		operator.setOperandA(operandB);
		operator.setOperandB(operandA);
		Operand returnValue = operator.process();
		logDebug("Process: " + operandB + " " + operator.getOperator() + " " + operandA + "; Result: " + returnValue);
		return returnValue;
	}

	private void parseToken(String newStrToken) throws ParseException {
		IToken newToken = null;
		if ((newToken = ChangePrecedence.build(newStrToken)) != null) {
			tokenList.add(newToken);
		} else if ((newToken = Operator.build(newStrToken)) != null) {
			tokenList.add(newToken);
		} else if ((newToken = Operand.build(newStrToken)) != null) {
			((Operand) newToken).setDataSource(this);
			tokenList.add(newToken);
		} else {
			throw new ParseException("couldn't parse the token '" + newStrToken + "'");
		}
	}

	public List<String> getParameterOperandsNames() {
		return Operand.getParameterOperands(tokenList);
	}

	public static void main(String[] args) throws ParseException, ProcessException {
		LogicalExpression le = new LogicalExpression();
		le.setDebugListener(new IDebugListener() {

			@Override
			public void logDebug(String description) {
				System.out.println(description);
			}
		});

		le.parseInputString("eventID == '123456' or (123 GT 23 AND (123 != 45)) AND (asdf == null)");
//        le.parseInputString("(directionA == 'out') and (eventIdB == null)");

		Map<String, Comparable<?>> parameters = new HashMap<>();
		parameters.put("eventID", "123456");
		parameters.put("directionA", "in");

		for (String parameterOperandsName : le.getParameterOperandsNames()) {
			System.out.println("operand: " + parameterOperandsName);
		}
		System.out.println("result: " + le.process(parameters));
//
//		
//		String str = "(upsMessage == '$') or (upsMessage != '$')";
//		Map<String, String> stringTokens = new HashMap<>();
//		int i=0;
//		int iniIndex = -1;
//		while((iniIndex = str.indexOf('\''))>=0) {
//			int endIndex = str.indexOf('\'', iniIndex+1);
//			String newIndex = String.format("IDX%03d", ++i);
//			String subString = str.substring(iniIndex, endIndex+1);
//			str = str.replace(subString, newIndex);
//			stringTokens.put(newIndex, subString);
//		}
//		
//		System.out.println(str);

		LogicalExpression le2 = new LogicalExpression();
		le2.setDebugListener(new IDebugListener() {

			@Override
			public void logDebug(String description) {
				System.out.println(description);
			}
		});

		le2.parseInputString("(NOT sim02) AND (NOT sim01) AND (NOT sim03)");
//        le.parseInputString("(directionA == 'out') and (eventIdB == null)");
		Map<String, Comparable<?>> parameters2 = new HashMap<>();
		parameters2.put("sim02", false);
		parameters2.put("sim01", true);
		parameters2.put("directionA", "in");
		System.out.println("result: " + le2.process(parameters2));
	}

	@Override
	public Comparable<?> getValue(String key) {
		return parameters.get(key);
	}

	private void logDebug(String description) {
		if (debugListener != null) {
			debugListener.logDebug(description);
		}
	}
}
