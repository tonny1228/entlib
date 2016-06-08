package org.llama.library.validation.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;


/**
 * 正则表达式验证器
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:16
 */
public class RegexValidator extends Validator<String> {

	/**
	 * 正则表达式
	 */
	private String regex;

	public RegexValidator(String messageTemplate) {
		super(messageTemplate);
	}

	public RegexValidator(String messageTemplate, String pattern) {
		super(messageTemplate);
		this.regex = pattern;
	}

	/**
	 * 通过正则表达式验证数据格式
	 * 
	 * @param target 数据
	 * @param validationResults 验证结果
	 */
	protected void doValidate(String target, ValidationResults validationResults) {
		if (!target.matches(regex)) {
			addValidatorResult(target, validationResults);
		}
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

}