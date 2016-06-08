package org.llama.library.validation.validator;

import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;

/**
 * 不为空验证器
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:15
 */
public class NotNullValidator extends Validator<Object> {

	public NotNullValidator(String messageTemplate) {
		super(messageTemplate);
	}

	/**
	 * 判断要验证的数据不为空
	 * 
	 * @param target 待验证的数据
	 * @param validationResults 验证结果
	 */
	protected void doValidate(Object target, ValidationResults validationResults) {
		if (target == null) {
			addValidatorResult(target, validationResults);
		}
	}

}