package logicalexpression.exception;

/**
 *
 * @author Henrique Vicentini <henrique.vicentini@woodtechms.com>
 */
public class ParseException extends Exception {

	private static final long serialVersionUID = 1L;

	public ParseException(String description) {
		super(description);
	}

}
