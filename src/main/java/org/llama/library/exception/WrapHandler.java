package org.llama.library.exception;

/**
 * 将原异常封装为新的异常并返回
 * 
 * @author tonny
 * 
 */
public class WrapHandler implements ExceptionHandler {
	/**
	 * 处理器名称
	 */
	private String name;

	/**
	 * 新的异常类名
	 */
	private String wrapException;

	/**
	 * 新的异常消息
	 */
	private String wrapExceptionMessage;

	/**
	 * 将原异常封装为新的异常并返回
	 */
	@SuppressWarnings("unchecked")
	public <T extends Exception> T handleException(Exception exceptionToHandle, Object[] args) {
		try {
			if (args != null && args.length == 1 && args[0] instanceof String) {
				return (T) Class.forName(wrapException).getConstructor(String.class, Throwable.class)
						.newInstance(args[0].toString(), exceptionToHandle);
			} else {
				return (T) Class.forName(wrapException).getConstructor(String.class, Throwable.class)
						.newInstance(wrapExceptionMessage, exceptionToHandle);
			}
		} catch (Exception e) {
			throw new ExceptionHandlingException(e);
		}
	}

	public String getWrapException() {
		return wrapException;
	}

	public void setWrapException(String wrapException) {
		this.wrapException = wrapException;
	}

	public String getWrapExceptionMessage() {
		return wrapExceptionMessage;
	}

	public void setWrapExceptionMessage(String wrapExceptionMessage) {
		this.wrapExceptionMessage = wrapExceptionMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
