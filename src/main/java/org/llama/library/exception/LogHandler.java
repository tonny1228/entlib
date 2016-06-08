package org.llama.library.exception;

import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

/**
 * 将异常输出到日志
 * 
 * @author tonny
 * @version 1.0
 * @created 23-八月-2011 9:01:10
 */
public class LogHandler implements ExceptionHandler {

	/**
	 * 处理器名称
	 */
	private String name;

	/**
	 * 将异常输出到日志
	 * 
	 * @param exceptionToHandle
	 * @param args 异常处理的参数
	 */
	@SuppressWarnings("unchecked")
	public <T extends Exception> T handleException(Exception exceptionToHandle, Object[] args) {
		StackTraceElement[] elements = exceptionToHandle.getStackTrace();
		Logger log = LogFactory.getLogger(elements[0].getClassName());
		log.error(exceptionToHandle.getMessage(), exceptionToHandle);
		return (T) exceptionToHandle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}