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
import java.util.StringTokenizer;
import logicalexpression.model.ChangePrecedence;
import logicalexpression.model.IToken;
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
        return true;
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
        le.parseInputString("1234 ne 4321");
        
        System.out.println("32adf34".matches("[\\p{Alnum}]+"));
    }

    @Override
    public Comparable getValue(String key) {
        return parameters.get(key);
    }
}
