package org.llama.library.validation.validator;

import java.util.HashMap;
import java.util.Map;

import org.llama.library.validation.validator.RangeBoundaryType;


/**
 * 边界类型,是包含,不包含还是忽略
 * 
 * @author tonny
 * @version 1.0
 * @created 15-九月-2011 14:22:16
 */
public class RangeBoundaryType implements Comparable<RangeBoundaryType> {
	/**
	 * 包含边界值
	 */
	public static final RangeBoundaryType INCLUSIVE = new RangeBoundaryType(1);
	/**
	 * 不包含边界值
	 */
	public static final RangeBoundaryType EXCLUSIVE = new RangeBoundaryType(2);
	/**
	 * 忽略
	 */
	public static final RangeBoundaryType IGNORE = new RangeBoundaryType(0);

	private static final Map<String, RangeBoundaryType> NAMES = new HashMap<String, RangeBoundaryType>();

	static {
		NAMES.put("ignore", IGNORE);
		NAMES.put("exclusive", EXCLUSIVE);
		NAMES.put("inclusive", INCLUSIVE);
	}

	/**
	 * 类型值
	 */
	private Integer type;

	private RangeBoundaryType(int type) {
		this.type = type;
	}

	/**
	 * 比较两个类型的大小
	 */
	public int compareTo(RangeBoundaryType target) {
		return type.compareTo(target.type);
	}

	public static RangeBoundaryType parse(String name) {
		return NAMES.get(name.toLowerCase());
	}

}