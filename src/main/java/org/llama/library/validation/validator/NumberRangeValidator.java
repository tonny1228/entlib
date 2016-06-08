package org.llama.library.validation.validator;

import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.RangeBoundaryType;
import org.llama.library.validation.validator.RangeChecker;


public class NumberRangeValidator extends Validator<Number> {

	/**
	 * 最小值
	 */
	protected Float lowerBound;
	/**
	 * 最大值
	 */
	protected Float upperBound;
	/**
	 * 边界类型
	 */
	protected RangeBoundaryType lowerType;

	/**
	 * 边界类型
	 */
	protected RangeBoundaryType upperType;

	public NumberRangeValidator(String messageTemplate) {
		super(messageTemplate);
	}

	protected void doValidate(Number target, ValidationResults validationResults) {
		RangeChecker<Float> checker = new RangeChecker(lowerBound, lowerType, upperBound, upperType);
		if (!checker.isInRange(target.floatValue())) {
			addValidatorResult(target, validationResults, lowerBound, upperBound);
		}
	}

	public Float getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Float lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Float getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Float upperBound) {
		this.upperBound = upperBound;
	}

	public RangeBoundaryType getLowerType() {
		return lowerType;
	}

	public void setLowerType(RangeBoundaryType lowerType) {
		this.lowerType = lowerType;
	}

	public RangeBoundaryType getUpperType() {
		return upperType;
	}

	public void setUpperType(RangeBoundaryType upperType) {
		this.upperType = upperType;
	}

}