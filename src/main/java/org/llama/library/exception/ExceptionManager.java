package org.llama.library.exception;

/**
 * 异常处理器，通过异常处理策略进行异常处理，是否返回新的异常
 * 
 * @author tonny
 * @version 1.0
 * @created 23-八月-2011 9:01:10
 */
public interface ExceptionManager {

	/**
	 * 通过异常处理策略进行异常处理，是否返回新的异常
	 * 
	 * <pre>
	 * Exception e = exceptionManager.process(exception, &quot;policy&quot;);
	 * if (e != null) {
	 * 	throw e;
	 * }
	 * </pre>
	 * 
	 * @param e 要处理的异常
	 * @param policy 策略名称
	 * @param args 异常处理的参数
	 */
	public <T extends Exception> T process(Exception e, String policy, Object... args);

}