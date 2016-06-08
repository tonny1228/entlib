package org.llama.library.validation;

import org.llama.library.EnterpriseApplication;
import org.llama.library.validation.ValidationManager;
import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;


/**
 * 验证器工具
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:16
 */
public abstract class ValidatorUtils {
	static ValidationManager manager = (ValidationManager) EnterpriseApplication.getComponent("validator");

	public static Validator getValidator(String name) {
		return manager.getValidator(name);
	}

	public static ValidationResults validate(Object target) {
		return manager.validate(target);
	}

	/*
	 * @see
	 * works.tonny.library.validation.ValidationManager#validate(java.lang.String,
	 * java.lang.Object)
	 */
	public static ValidationResults validate(String name, Object target) {
		return manager.validate(name, target);
	}

}