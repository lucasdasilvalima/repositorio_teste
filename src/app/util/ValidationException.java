package app.util;

/**
 * An validation exception.
 */
public class ValidationException extends Exception {
	public ValidationException() {
	}

	public ValidationException(String message){
		super(message);
	}
}
