package org.llama.library.cache;

import org.llama.library.configuration.ConfigurationException;

/**
 * 缓存框架配置异常
 * 
 * @author tonny
 * 
 */
public class CacheConfigurationException extends ConfigurationException {

	public CacheConfigurationException() {
	}

	public CacheConfigurationException(String message) {
		super(message);
	}

	public CacheConfigurationException(Throwable cause) {
		super(cause);
	}

	public CacheConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

}
