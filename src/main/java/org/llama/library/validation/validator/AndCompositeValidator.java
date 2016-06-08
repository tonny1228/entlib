package org.llama.library.validation.validator;

import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.CompositeValidator;


/**
 * 与组合验证器.只有满足所有条件的验证才通过验证
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:15
 */
public class AndCompositeValidator extends CompositeValidator {

	public AndCompositeValidator(Validator... validators) {
		super(validators);
	}

	/**
	 * 遍历所有验证器进行验证
	 * 
	 * @param target 待验证的数据
	 * @param validationResults 验证结果
	 */
	protected void doValidate(Object target, ValidationResults validationResults) {
		for (int i = 0; i < validators.size(); i++) {
			validators.get(i).validate(target, validationResults);
		}
	}

}