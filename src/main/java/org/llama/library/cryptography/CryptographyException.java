package org.llama.library.cryptography;

import org.llama.library.configuration.ConfigurationException;

/**
 * 密码加密、解密异常
 * 
 * @author tonny
 * 
 */
public class CryptographyException extends ConfigurationException {

	public CryptographyException() {
		super();
	}

	public CryptographyException(String message, Throwable cause) {
		super(message, cause);
	}

	public CryptographyException(String message) {
		super(message);
	}

	public CryptographyException(Throwable cause) {
		super(cause);
	}

}
