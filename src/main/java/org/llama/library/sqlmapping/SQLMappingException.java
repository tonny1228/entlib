package org.llama.library.sqlmapping;

import org.llama.library.configuration.ConfigurationException;

/**
 * sql映射异常
 * 
 * @author tonny
 * 
 */
public class SQLMappingException extends ConfigurationException {

	public SQLMappingException() {
		super();
	}

	public SQLMappingException(String message, Throwable cause) {
		super(message, cause);
	}

	public SQLMappingException(String message) {
		super(message);
	}

	public SQLMappingException(Throwable cause) {
		super(cause);
	}

}
