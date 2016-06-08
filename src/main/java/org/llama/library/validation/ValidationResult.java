package org.llama.library.validation;

import org.llama.library.validation.Validator;

/**
 * 验证失败结果,一条验证失败后产生一条结果,存放验证失败信息,值等
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:16
 */
public class ValidationResult {

	/**
	 * 验证失败的消息
	 */
	private String message;
	/**
	 * 验证的数据
	 */
	private Object target;
	/**
	 * 使用的验证器
	 */
	private Validator validator;

	/**
	 * 初始化验证结果信息
	 * 
	 * @param message 验证的消息
	 * @param target 对象
	 * @param validator 验证器
	 */
	public ValidationResult(String message, Object target, Validator validator) {
		this.message = message;
		this.target = target;
		this.validator = validator;
	}

	public String getMessage() {
		return message;
	}

	public Object getTarget() {
		return target;
	}

	public Validator getValidator() {
		return validator;
	}

	public String toString() {
		return super.toString() + "[" + message + "]";
	}

}