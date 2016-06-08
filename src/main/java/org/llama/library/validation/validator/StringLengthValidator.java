package org.llama.library.validation.validator;

import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.RangeBoundaryType;
import org.llama.library.validation.validator.RangeChecker;


/**
 * 字符串长度验证器
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:16
 */
public class StringLengthValidator extends Validator<String> {

	/**
	 * 最小长度,负数为不验证
	 */
	private int min = -1;
	/**
	 * 最大长度,负数为不验证
	 */
	private int max = -1;

	public StringLengthValidator(String messageTemplate) {
		super(messageTemplate);
	}

	public StringLengthValidator(String messageTemplate, int min, int max) {
		super(messageTemplate);
		this.min = min;
		this.max = max;
	}

	/**
	 * 判断字符串长度是否在边界范围内
	 * 
	 * @param target 要验证的字符串,null时按0字符串验证
	 * @param validationResults 验证结果
	 */
	protected void doValidate(String target, ValidationResults validationResults) {
		if (target == null) {
			if (min > 0) {
				addValidatorResult(target, validationResults, min, max);
			}
			return;
		}
		RangeBoundaryType lowerType = makeBoundaryType(min);
		RangeBoundaryType upperType = makeBoundaryType(max);

		RangeChecker<Integer> checker = new RangeChecker<Integer>(min, lowerType, max, upperType);
		if (!checker.isInRange(target.length())) {
			addValidatorResult(target, validationResults, min, max);
		}
	}

	/**
	 * 根据值产生边界类型.负数为忽略边界,其它为包含边界
	 * 
	 * @param value 边界值
	 * @return 边界类型
	 */
	protected RangeBoundaryType makeBoundaryType(int value) {
		RangeBoundaryType type = null;
		if (value < 0) {
			type = RangeBoundaryType.IGNORE;
		} else {
			type = RangeBoundaryType.INCLUSIVE;
		}
		return type;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public String toString() {
		return super.toString() + "[" + min + "-" + max + "]";
	}
}