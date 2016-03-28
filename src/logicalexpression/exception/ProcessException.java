/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.exception;

/**
 *
 * @author Henrique Vicentini <henrique.vicentini@woodtechms.com>
 */
public class ProcessException extends Exception {

	private static final long serialVersionUID = 1L;

	public ProcessException(String description) {
		super(description);
	}

}
