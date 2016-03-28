/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.model;

/**
 *
 * @author Shulander
 */
public interface ITokenIdentifier {

	boolean isOperand();

	boolean isOperator();

	boolean isChangePrecedence();
}
