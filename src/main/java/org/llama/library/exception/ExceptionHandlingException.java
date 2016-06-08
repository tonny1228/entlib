package org.llama.library.exception;

/**
 * 异常处理异常
 * 
 * @author tonny
 * 
 */
public class ExceptionHandlingException extends RuntimeException {

	public ExceptionHandlingException() {
		super();
	}

	public ExceptionHandlingException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExceptionHandlingException(String message) {
		super(message);
	}

	public ExceptionHandlingException(Throwable cause) {
		super(cause);
	}

}
