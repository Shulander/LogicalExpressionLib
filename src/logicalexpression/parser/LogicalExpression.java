/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import logicalexpression.model.ChangePrecedence;
import logicalexpression.model.ChangePrecedence.EPrecedenceTokens;
import logicalexpression.model.EOperator;
import static logicalexpression.model.EOperator.NOT;
import logicalexpression.model.IToken;
import logicalexpression.model.ITokenIdentifier;
import logicalexpression.model.Operator;
import logicalexpression.model.operand.IDataSource;
import logicalexpression.model.operand.Operand;

/**
 *
 * @author Shulander
 */
class LogicalExpression implements IDataSource {
    List<IToken> tokenList;
    private Map<String, Comparable> parameters;
    private int startIndex;
    
    public LogicalExpression() {
        tokenList = new ArrayList<>();
        parameters = new HashMap<>();
    }
    
    public void parseInputString(String str) {
        tokenList.clear();
        str = str.replace("(", " ( ").replace(")", " ) ");

        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            String newToken = st.nextToken();
            System.out.println(newToken);
            parseToken(newToken);
        }
        if(!ChangePrecedence.isBalanced()) {
            throw new IllegalArgumentException("the number of open parentesis does not maches the close number, please review your expression!");
        }
        System.out.println("token size: "+tokenList.size());
    }
    
    public Boolean process(Map<String, Comparable> parameters) {
        this.parameters = parameters;
 
        Operand op = process();
        
        return op.getValue().compareTo(true)==0;
    }
    
    private Operand process() {
        
        Stack<Operand> operandStack = new Stack<>();
        Stack<Operator> operatorStack = new Stack<>();
        
        boolean doneHere = false;

        while(!tokenList.isEmpty() && !doneHere) {
            ITokenIdentifier token = (ITokenIdentifier) tokenList.remove(0);
            if(token.isChangePrecedence()) {
                if(((ChangePrecedence)token).getPrecedence().equals(EPrecedenceTokens.OPEN)) {
                    operandStack.push(process());
                } else {
                    doneHere=true;
                }
            } else if(token.isOperand()) {
                operandStack.push((Operand) token);
            } else if(token.isOperator()) {
                if(((Operator) token).getOperator().equals(EOperator.NOT)) {
                    operandStack.push(processNot());
                } else {
                    operatorStack.push((Operator) token);
                }
            }
            
            if(operandStack.size()>=2 && operatorStack.size()>=1) {
                operandStack.push(processOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
            }
        }
        
        while(operandStack.size()>=2 && operatorStack.size()>=1) {
            operandStack.push(processOperator(operatorStack.pop(), operandStack.pop(), operandStack.pop()));
        }
        
        return operandStack.size()==1?operandStack.pop(): null;
    }

    private Operand processNot() {
        Operand returnValue = null;
        if(!tokenList.isEmpty()) {
            ITokenIdentifier token = (ITokenIdentifier) tokenList.remove(0);
            if(token.isChangePrecedence()) {
                if(((ChangePrecedence)token).getPrecedence().equals(EPrecedenceTokens.OPEN)) {
                    returnValue = process();
                } else {
                    System.out.println("expecting and operand or open parentesis ('('), found a ')'");
                }
            } else if(token.isOperand()) {
                returnValue = (Operand) token;
            } else if(token.isOperator()) {
                if(((Operator) token).getOperator().equals(EOperator.NOT)) {
                    returnValue = processNot();
                } else {
                    System.out.println("expecting and operand or open parentesis ('('), found an operator");
                }
            }
        } else {
            System.out.println("expecting and operand or open parentesis ('(')");
        }
        
        Operand returnValue2 = Operand.build(NOT.compare(returnValue.getValue(), null));
        System.out.println("Process: "+NOT+" "+returnValue.getValue()+"; Result: "+returnValue2.getValue());
        return returnValue2;
    }

    private Operand processOperator(Operator operator, Operand operandA, Operand operandB) {
        operator.addOperand(operandB);
        operator.addOperand(operandA);
        Operand returnValue = operator.process();
        System.out.println("Process: "+operandB.getValue()+" "+ operator.getOperator()+" "+operandA.getValue()+"; Result: "+returnValue.getValue());
        return returnValue;
    }

    private void parseToken(String newStrToken) {
        IToken newToken = null;
        if ((newToken = ChangePrecedence.build(newStrToken)) != null) {
            tokenList.add(newToken);
        } else if ((newToken = Operator.build(newStrToken)) != null) {
            tokenList.add(newToken);
        } else if ((newToken = Operand.build(newStrToken)) != null) {
            ((Operand)newToken).setDataSource(this);
            tokenList.add(newToken);
        } else {
            throw new IllegalArgumentException("couldn't parse the token '"+newStrToken+"'");
        }
    }
    
    
    public static void main(String[] args) {
        LogicalExpression le = new LogicalExpression();
        le.parseInputString("eventID == '123456' or (123 GT 23 AND (123 != 45))");
        
        Map<String, Comparable> parameters = new HashMap<>();
        parameters.put("eventID", "123456");
        
        System.out.println("result: "+le.process(parameters));
        System.out.println("32adf34".matches("[\\p{Alnum}]+"));
    }

    @Override
    public Comparable getValue(String key) {
        return parameters.get(key);
    }
}
