package org.llama.library.validation.validator;

import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.OrCompositeValidator;


/**
 * ip多规则验证器
 * 
 * @ClassName: IPValidator
 * @Description:
 * @author Tonny
 * @date 2011-11-15 下午3:49:40
 * @version 1.0
 */
public class IPValidator extends OrCompositeValidator {
	public IPValidator(Validator... validators) {
		super(validators);
	}
}
