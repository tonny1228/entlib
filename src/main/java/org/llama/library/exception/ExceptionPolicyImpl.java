package org.llama.library.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异常处理策略，策略中包含一组异常处理流程，每一步对流程进行处理
 * 
 * @author tonny
 * @version 1.0
 * @created 23-八月-2011 9:01:11
 */
public class ExceptionPolicyImpl implements ExceptionPolicy {

	/**
	 * 策略名称
	 */
	private String name;

	/**
	 * 策略条目
	 */
	private Map<Class<? extends Exception>, ExceptionPolicyEntry> entrys;

	public ExceptionPolicyImpl(String name, List<ExceptionPolicyEntry> entrys) {
		this.name = name;
		this.entrys = new HashMap<Class<? extends Exception>, ExceptionPolicyEntry>();
		for (ExceptionPolicyEntry entry : entrys) {
			this.entrys.put(entry.getExceptionType(), entry);
		}
	}

	/**
	 * 对异常按策略条目进行处理
	 * 
	 * @param exceptionToHandle 待处理的异常
	 * @param args 异常处理的参数
	 */
	public <T extends Exception> T handleException(Exception exceptionToHandle, Object... args) {
		ExceptionPolicyEntry entry = getPolicyEntry(exceptionToHandle);
		if (entry == null) {
			return null;
		}
		return entry.handle(exceptionToHandle, args);
	}

	/**
	 * 根据异常类取得映射的条目
	 * 
	 * @param exceptionToHandle 异常类
	 * @return 异常处理条目
	 */
	private ExceptionPolicyEntry getPolicyEntry(Exception exceptionToHandle) {
		Class<? extends Exception> type = exceptionToHandle.getClass();
		ExceptionPolicyEntry entry = getPolicyEntry(type);
		while (entry == null && type != null) {
			type = (Class<? extends Exception>) type.getSuperclass();
			entry = getPolicyEntry(type);
		}
		return entry;
	}

	/**
	 * 根据类取映射的处理条目，没有返回空
	 * 
	 * @param type 异常类型
	 * @return 异常处理条目
	 */
	private ExceptionPolicyEntry getPolicyEntry(Class<? extends Exception> type) {
		return entrys.get(type);
	}

}