package org.llama.library.validation.validator;

import java.util.ArrayList;
import java.util.List;

import org.llama.library.validation.ValidationResult;
import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.CompositeValidator;


/**
 * 或组合验证器.只要满足任意球条件的验证就通过验证
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:15
 */
public class OrCompositeValidator extends CompositeValidator {

	public OrCompositeValidator(Validator... validators) {
		super(validators);
	}

	/**
	 * 遍历验证器,当遇到验证通过时
	 * 
	 * @param target
	 * @param validationResults
	 */
	protected void doValidate(Object target, ValidationResults validationResults) {
		List<ValidationResult> childrenValidationResults = new ArrayList<ValidationResult>();

		for (Validator validator : validators) {
			ValidationResults childValidationResults = new ValidationResults();
			validator.validate(target, childValidationResults);
			if (childValidationResults.isValid()) {
				return;
			}
			childrenValidationResults.addAll(childValidationResults.getValidationResults());
		}
		validationResults.addResults(childrenValidationResults);
	}
}