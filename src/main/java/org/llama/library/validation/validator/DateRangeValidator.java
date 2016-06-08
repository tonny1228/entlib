package org.llama.library.validation.validator;

import java.util.Date;

import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.RangeBoundaryType;
import org.llama.library.validation.validator.RangeChecker;


public class DateRangeValidator extends Validator<Date> {

	/**
	 * 最小值
	 */
	protected Date lowerBound;
	/**
	 * 最大值
	 */
	protected Date upperBound;
	/**
	 * 边界类型
	 */
	protected RangeBoundaryType lowerType;

	/**
	 * 边界类型
	 */
	protected RangeBoundaryType upperType;

	public DateRangeValidator(String messageTemplate) {
		super(messageTemplate);
	}

	protected void doValidate(Date target, ValidationResults validationResults) {
		RangeChecker<Date> checker = new RangeChecker(lowerBound, lowerType, upperBound, upperType);
		if (!checker.isInRange(target)) {
			addValidatorResult(target, validationResults, lowerBound, upperBound);
		}
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

	public Date getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Date lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Date getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Date upperBound) {
		this.upperBound = upperBound;
	}

}