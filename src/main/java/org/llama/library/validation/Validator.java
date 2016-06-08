package org.llama.library.validation;

import java.text.MessageFormat;

import org.llama.library.validation.ValidationResult;
import org.llama.library.validation.ValidationResults;


/**
 * 抽象验证器,定义了基本验证方法
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:16
 */
public abstract class Validator<T> {

	/**
	 * 验证消息模板
	 */
	protected String messageTemplate;

	/**
	 * 
	 * @param messageTemplate
	 */
	public Validator(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	/**
	 * 验证对象是否通过验证
	 * 
	 * @param target 要验证的数据
	 * @param validationResults 验证结果
	 */
	public void validate(T target, ValidationResults validationResults) {
		doValidate(target, validationResults);
	}

	/**
	 * 根据消息模板和参数产生消息
	 * 
	 * @param params 参数
	 * @return 消息
	 */
	protected String getMessage(Object... params) {
		return MessageFormat.format(messageTemplate, params);
	}

	protected void addValidatorResult(Object target, ValidationResults validationResults, Object... params) {
		validationResults.addResult(new ValidationResult(getMessage(params), target, this));
	}

	/**
	 * 验证对象是否通过验证
	 * 
	 * @param target 要验证的数据
	 * @param validationResults 验证结果
	 */
	protected abstract void doValidate(T target, ValidationResults validationResults);

}