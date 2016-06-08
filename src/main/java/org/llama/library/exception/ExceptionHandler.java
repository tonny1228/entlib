package org.llama.library.exception;

/**
 * 异常处理节点
 * 
 * @author tonny
 * @version 1.0
 * @created 23-八月-2011 9:01:10
 */
public interface ExceptionHandler {

	/**
	 * 通过异常进行处理
	 * 
	 * @param exceptionToHandle 待处理的异常
	 * @param args 异常处理的参数
	 */
	public <T extends Exception> T handleException(Exception exceptionToHandle, Object[] args);

}