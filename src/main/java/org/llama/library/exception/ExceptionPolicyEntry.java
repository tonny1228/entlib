package org.llama.library.exception;

import java.util.List;

/**
 * 策略条目，根据异常类型确定异常处理方式
 * 
 * @author tonny
 * @version 1.0
 * @created 23-八月-2011 9:01:11
 */
public class ExceptionPolicyEntry {

	/**
	 * 部署的处理器
	 */
	private List<ExceptionHandler> handlers;

	/**
	 * 映射的异常类型
	 */
	private Class<? extends Exception> exceptionType;

	private HandlerType type;

	public ExceptionPolicyEntry(List<ExceptionHandler> handlers, Class<? extends Exception> clazz, HandlerType type) {
		this.handlers = handlers;
		this.exceptionType = clazz;
		this.type = type;
	}

	/**
	 * 通过各节点处理异常，并根据类型返回异常
	 * 
	 * @param exceptionToHandle 异常
	 * @param args 异常处理的参数
	 */
	@SuppressWarnings("unchecked")
	public <T extends Exception> T handle(Exception exceptionToHandle, Object[] args) {
		T exception = null;
		for (ExceptionHandler handler : handlers) {
			exception = handler.handleException(exceptionToHandle, args);
		}
		if (type == HandlerType.NONE) {
			return null;
		}
		if (type == HandlerType.RETHROW) {
			return (T) exceptionToHandle;
		}
		if (type == HandlerType.THROW_NEW_EXCEPTION) {
			if (exception == null) {
				exception = (T) new ExceptionHandlingException("异常处理", exceptionToHandle);
			}
		}
		return exception;
	}

	public Class<? extends Exception> getExceptionType() {
		return exceptionType;
	}

}