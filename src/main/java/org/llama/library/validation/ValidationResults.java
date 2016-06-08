package org.llama.library.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.llama.library.validation.ValidationResult;


/**
 * 验证结果集合,保存一次验证的所有验证条目
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:16
 */
public class ValidationResults implements Iterable<ValidationResult> {

	private List<ValidationResult> validationResults;

	public ValidationResults() {
		validationResults = new ArrayList<ValidationResult>();
	}

	/**
	 * 往验证结果中添加验证失败记录
	 * 
	 * @param validationResult 验证结果记录
	 */
	public void addResult(ValidationResult validationResult) {
		validationResults.add(validationResult);
	}

	/**
	 * 往验证结果中添加验证失败记录集
	 * 
	 * @param validationResult 验证结果记录集
	 */
	public void addResults(List<ValidationResult> validationResult) {
		validationResults.addAll(validationResult);
	}

	/**
	 * 判断验证结果是否通过验证
	 * 
	 * @return 是否通过验证
	 */
	public boolean isValid() {
		return validationResults.size() == 0;
	}

	public void clear() {
		validationResults.clear();
	}

	public Iterator<ValidationResult> iterator() {
		return validationResults.iterator();
	}

	public List<ValidationResult> getValidationResults() {
		return validationResults;
	}

}