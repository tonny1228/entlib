package org.llama.library.validation.validator;

import org.llama.library.validation.validator.RangeBoundaryType;

public class RangeChecker<T extends Comparable<T>> {
	private T lowerBound;
	private RangeBoundaryType lowerBoundType;
	private T upperBound;
	private RangeBoundaryType upperBoundType;

	/**
	 * 初始化边界数据
	 * 
	 * @param lowerBound 下边界值
	 * @param lowerBoundType 下边界类型
	 * @param upperBound 上边界值
	 * @param upperBoundType 上边界类型
	 */
	public RangeChecker(T lowerBound, RangeBoundaryType lowerBoundType, T upperBound, RangeBoundaryType upperBoundType) {
		if (upperBound == null && upperBoundType != RangeBoundaryType.IGNORE)
			throw new IllegalArgumentException("");
		if (lowerBound == null && lowerBoundType != RangeBoundaryType.IGNORE)
			throw new IllegalArgumentException("");

		if (lowerBoundType != RangeBoundaryType.IGNORE && upperBoundType != RangeBoundaryType.IGNORE
				&& upperBound.compareTo(lowerBound) < 0)
			throw new IllegalArgumentException("");
		this.lowerBound = lowerBound;
		this.lowerBoundType = lowerBoundType;
		this.upperBound = upperBound;
		this.upperBoundType = upperBoundType;
	}

	/**
	 * 判断数据是否在边界范围内.
	 * 
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean isInRange(T target) {
		if (this.lowerBoundType.compareTo(RangeBoundaryType.IGNORE) > 0) {
			int lowerBoundComparison = this.lowerBound.compareTo(target);
			if (lowerBoundComparison > 0)
				return false;
			if (this.lowerBoundType == RangeBoundaryType.EXCLUSIVE && lowerBoundComparison == 0)
				return false;
		}
		if (this.upperBoundType.compareTo(RangeBoundaryType.IGNORE) > 0) {
			int upperBoundComparison = this.upperBound.compareTo(target);
			if (upperBoundComparison < 0)
				return false;
			if (this.upperBoundType == RangeBoundaryType.EXCLUSIVE && upperBoundComparison == 0)
				return false;
		}

		return true;
	}

}
