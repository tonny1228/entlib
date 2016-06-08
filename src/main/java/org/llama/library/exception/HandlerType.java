package org.llama.library.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理类型，分为不抛出异常，重新抛出异常和抛出新的异常
 * 
 * @author tonny
 * 
 */
public class HandlerType {
	public static final HandlerType NONE = new HandlerType("none");

	public static final HandlerType RETHROW = new HandlerType("rethrow");

	public static final HandlerType THROW_NEW_EXCEPTION = new HandlerType("throwNewException");

	private static final Map<String, HandlerType> TYPES = new HashMap<String, HandlerType>();

	static {
		TYPES.put("none", NONE);
		TYPES.put("rethrow", RETHROW);
		TYPES.put("thrownewexception", THROW_NEW_EXCEPTION);
	}

	private String name;

	private HandlerType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * 根据名称生成处理类型
	 * 
	 * @param name 类型名称
	 * @return 处理类型
	 */
	public static HandlerType parse(String name) {
		HandlerType type = TYPES.get(name.toLowerCase());
		if (type == null) {
			throw new ExceptionHandlingException("没有找到处理类型:" + name);
		}
		return type;
	}
}
