package org.llama.library.validation;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.AndCompositeValidator;
import org.llama.library.validation.validator.CompositeValidator;


public class ClassValidator {

	private Map<String, Validator> fieldValidators = new HashMap<String, Validator>();

	public void setFieldValidator(String field, Validator validator) {
		fieldValidators.put(field, validator);
	}

	public ValidationResults validate(Object target) {
		ValidationResults results = new ValidationResults();
		try {
			for (String field : fieldValidators.keySet()) {
				Object value = PropertyUtils.getProperty(target, field);
				fieldValidators.get(field).validate(value, results);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("配置类型错误", e);
		}
		return results;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + fieldValidators + "]";
	}
}
