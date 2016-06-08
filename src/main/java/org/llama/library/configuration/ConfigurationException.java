package org.llama.library.configuration;

/**
 * 配置异常
 * 
 * @author tonny
 * 
 */
public class ConfigurationException extends RuntimeException {

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(Throwable cause) {
		super(cause);
	}

}
