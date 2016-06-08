package org.llama.library.exception;

/**
 * 将异常替换为另一异常并返回
 * 
 * @author tonny
 * 
 */
public class ReplaceHandler implements ExceptionHandler {
	/**
	 * 处理器名称
	 */
	private String name;

	/**
	 * 替换为的异常类型
	 */
	private String replaceException;

	/**
	 * 替换的异常消息
	 */
	private String replaceExceptionMessage;

	/**
	 * 将原异常替换为新的异常返回
	 */
	@SuppressWarnings("unchecked")
	public <T extends Exception> T handleException(Exception exceptionToHandle, Object[] args) {
		try {
			return (T) Class.forName(replaceException).getConstructor(String.class)
					.newInstance(replaceExceptionMessage);
		} catch (Exception e) {
			throw new ExceptionHandlingException(e);
		}
	}

	public String getReplaceException() {
		return replaceException;
	}

	public void setReplaceException(String replaceException) {
		this.replaceException = replaceException;
	}

	public String getReplaceExceptionMessage() {
		return replaceExceptionMessage;
	}

	public void setReplaceExceptionMessage(String replaceExceptionMessage) {
		this.replaceExceptionMessage = replaceExceptionMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
