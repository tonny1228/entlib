package org.llama.library.validation.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;


/**
 * 组合验证器.满足所有子验证器的一定条件的验证才通过验证
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:15
 */
public abstract class CompositeValidator extends Validator {

	/**
	 * 所有的子验证器
	 */
	protected List<Validator> validators;

	private CompositeValidator(String messageTemplate) {
		super(messageTemplate);
	}

	public CompositeValidator(Validator... validators) {
		super(null);
		this.validators = new ArrayList<Validator>();
		CollectionUtils.addAll(this.validators, validators);
	}

	public void addValidator(Validator... validators) {
		CollectionUtils.addAll(this.validators, validators);
	}

	public String toString() {
		return super.toString() + "[" + validators + "]";
	}
}