package org.llama.library.exception;

/**
 * 异常处理策略，策略中包含一组异常处理流程，每一步对流程进行处理
 * 
 * @author tonny
 * @version 1.0
 * @created 23-八月-2011 9:01:11
 */
public interface ExceptionPolicy {

	/**
	 * 对异常按策略条目进行处理
	 * 
	 * @param exceptionToHandle 待处理的异常
	 * @param args 异常处理的参数
	 */
	<T extends Exception> T handleException(Exception exceptionToHandle, Object... args);

}