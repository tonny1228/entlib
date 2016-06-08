package org.llama.library.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 数据表达式计算器，提供四则运算
 * 
 * @author tonny
 * 
 */
public class Calculator {
	/**
	 * 格式化数字
	 * 
	 * @param obj 数字对象
	 * @param format 格式化字符串
	 * @return 四则运算值
	 * @author 刘祥栋 2009-8-26
	 */
	public static String formatNumber(Object obj, String format) {
		if (obj == null) {
			return StringUtils.EMPTY;
		}

		String s = String.valueOf(obj);
		if (format == null || "".equals(format.trim())) {
			format = "#.00";
		}
		try {
			if (obj instanceof Double || obj instanceof Float) {
				if (format.contains("%")) {
					NumberFormat numberFormat = NumberFormat.getPercentInstance();
					s = numberFormat.format(obj);
				} else {
					DecimalFormat decimalFormat = new DecimalFormat(format);
					s = decimalFormat.format(obj);
				}
			} else {
				NumberFormat numberFormat = NumberFormat.getInstance();
				s = numberFormat.format(obj);
			}
		} catch (Exception e) {
			return s;
		}
		return s;
	}

	/**
	 * 计算字符串四则运算表达式
	 * 
	 * @param string 表达式
	 * @return 运行值
	 */
	public static String computeString(String string) {
		String regexCheck = "[\\(\\)\\d\\+\\-\\*/\\.]*"; // 是否是合法的表达式

		if (!Pattern.matches(regexCheck, string)) {
			return string;
		}

		Matcher matcher = null;
		String temp = "";
		int index = -1;
		String regex = "\\([\\d\\.\\+\\-\\*/]+\\)"; // 提取括号表达式
		string = string.replaceAll("\\s", ""); // 去除空格
		try {
			Pattern pattern = Pattern.compile(regex);
			// 循环计算所有括号里的表达式
			while (pattern.matcher(string).find()) {
				matcher = pattern.matcher(string);
				while (matcher.find()) {
					temp = matcher.group();
					index = string.indexOf(temp);
					string = string.substring(0, index) + computeStirngNoBracket(temp)
							+ string.substring(index + temp.length());
				}
			}
			// 最后计算总的表达式结果
			string = computeStirngNoBracket(string);
			if (Float.parseFloat(string) % 1.0 == 0) {
				string = string.substring(0, string.indexOf("."));
			}
		} catch (NumberFormatException e) {
			return e.getMessage();
		}
		return string;
	}

	/**
	 * 计算不包含括号的表达式
	 * 
	 * @param string 表达式
	 * @return 运算值
	 */
	private static String computeStirngNoBracket(String string) {
		string = string.replaceAll("(^\\()|(\\)$)", "");
		String regexMultiAndDivision = "[\\d\\.]+(\\*|\\/)[\\d\\.]+";
		String regexAdditionAndSubtraction = "(^\\-)?[\\d\\.]+(\\+|\\-)[\\d\\.]+";

		String temp = "";
		int index = -1;

		// 解析乘除法
		Pattern pattern = Pattern.compile(regexMultiAndDivision);
		Matcher matcher = null;
		while (pattern.matcher(string).find()) {
			matcher = pattern.matcher(string);
			if (matcher.find()) {
				temp = matcher.group();
				index = string.indexOf(temp);
				string = string.substring(0, index) + doMultiAndDivision(temp)
						+ string.substring(index + temp.length());
			}
		}

		// 解析加减法
		pattern = Pattern.compile(regexAdditionAndSubtraction);
		while (pattern.matcher(string).find()) {
			matcher = pattern.matcher(string);
			if (matcher.find()) {
				temp = matcher.group();
				index = string.indexOf(temp);
				if (temp.startsWith("-")) {
					string = string.substring(0, index) + doNegativeOperation(temp)
							+ string.substring(index + temp.length());
				} else {
					string = string.substring(0, index) + doAdditionAndSubtraction(temp)
							+ string.substring(index + temp.length());
				}
			}
		}

		return string;
	}

	/**
	 * 执行乘除法
	 * 
	 * @param string 表达式
	 * @return 运算值
	 */
	private static String doMultiAndDivision(String string) {
		String value = "";
		double d1 = 0;
		double d2 = 0;
		String[] temp = null;
		if (string.contains("*")) {
			temp = string.split("\\*");
		} else {
			temp = string.split("/");
		}

		if (temp.length < 2) {
			return string;
		}

		d1 = Double.valueOf(temp[0]);
		d2 = Double.valueOf(temp[1]);
		if (string.contains("*")) {
			value = String.valueOf(d1 * d2);
		} else {
			value = String.valueOf(d1 / d2);
		}

		return value;
	}

	/**
	 * 执行加减法
	 * 
	 * @param string 表达式
	 * @return 运算值
	 * @author ZYWANG 2009-8-31
	 */
	private static String doAdditionAndSubtraction(String string) {
		double d1 = 0;
		double d2 = 0;
		String[] temp = null;
		String value = "";
		if (string.contains("+")) {
			temp = string.split("\\+");
		} else {
			temp = string.split("\\-");
		}

		if (temp.length < 2) {
			return string;
		}

		d1 = Double.valueOf(temp[0]);
		d2 = Double.valueOf(temp[1]);
		if (string.contains("+")) {
			value = String.valueOf(d1 + d2);
		} else {
			value = String.valueOf(d1 - d2);
		}

		return value;
	}

	/**
	 * 执行负数运算
	 * 
	 * @param string 表达式
	 * @return 运算值
	 */
	private static String doNegativeOperation(String string) {
		String temp = string.substring(1);
		if (temp.contains("+")) {
			temp = temp.replace("+", "-");
		} else {
			temp = temp.replace("-", "+");
		}
		temp = doAdditionAndSubtraction(temp);
		if (temp.startsWith("-")) {
			temp = temp.substring(1);
		} else {
			temp = "-" + temp;
		}
		return temp;
	}

}
