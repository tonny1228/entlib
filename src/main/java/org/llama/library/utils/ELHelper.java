/**  
 * @Title: ELHelper.java
 * @Package works.tonny.library.utils
 * @author Tonny
 * @date 2012-5-9 下午2:16:21
 */
package org.llama.library.utils;

import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 复杂的el表达式解析
 * 
 * @ClassName: ELHelper
 * @Description:
 * @author Tonny
 * @date 2012-5-9 下午2:16:21
 * @version 1.0
 */
public class ELHelper {
	/**
	 * @Fields SYSDATE :
	 */
	private static final String SYSDATE = "#date.";
	/**
	 * @Fields DOT :
	 */
	private static final String DOT = ".";
	private final static String DELIM_START = "${";
	private final static char DELIM_STOP = '}';
	private final static int DELIM_START_LEN = 2;
	private final static int DELIM_STOP_LEN = 1;
	private final static String _IS_UNDEFINED = "_IS_UNDEFINED";

	/**
	 * 将所有的表达式替换为context中有的数据。表达式格式为${key.property.property}
	 * 
	 * @Title: execute
	 * @param val 待替换的表达式
	 * @param context 数据对象
	 * @return 表达式解析
	 * @date 2012-5-9 下午3:06:40
	 * @author tonny
	 * @version 1.0
	 */
	public static String execute(String val, Map context) {
		StringBuffer sbuf = new StringBuffer();
		int i = 0, j = 0, k = 0;

		while (true) {
			j = val.indexOf(DELIM_START, i);

			if (j == -1 && i == 0) {
				// 从来就没有可匹配的表达式
				return val;
			}
			if (j == -1 && i > 0) {
				// 匹配过，再也没有可匹配的了
				sbuf.append(val.substring(i, val.length()));
				return sbuf.toString();
			}

			// 替换表达式
			sbuf.append(val.substring(i, j));
			k = val.indexOf(DELIM_STOP, j);
			if (k == -1) {
				// 没有结束符
				throw new IllegalArgumentException('"' + val + "\" has no closing brace. Opening brace at position "
						+ j + '.');
			}
			j += DELIM_START_LEN;
			appendReplacement(val, context, sbuf, j, k);
			i = k + DELIM_STOP_LEN;
		}
	}

	/**
	 * 追加解析的表达式
	 * 
	 * @Title: appendReplacement
	 * @param val 表达式字符串
	 * @param context 属性上下文
	 * @param sbuf 缓存
	 * @param delimIndex ${位置
	 * @param delimStopIndex 位置
	 * @date 2012-5-9 下午3:04:32
	 * @author tonny
	 * @version 1.0
	 */
	private static void appendReplacement(String val, Map<String, Object> context, StringBuffer sbuf, int delimIndex,
			int delimStopIndex) {
		String rawKey = val.substring(delimIndex, delimStopIndex);
		String[] extracted = extractDefaultReplacement(rawKey);
		String key = extracted[0];
		String defaultReplacement = extracted[1]; // can be null
		Object value = getProperty(context, key);
		String replacement = value == null ? null : value.toString();
		// then try in System properties
		if (replacement == null) {
			replacement = getSystemProperty(key);
		}
		// 默认值
		if (replacement == null) {
			replacement = defaultReplacement;
		}

		if (replacement != null) {
			String recursiveReplacement = execute(replacement, context);
			sbuf.append(recursiveReplacement);
		} else {
			sbuf.append(key + _IS_UNDEFINED);
		}
	}

	/**
	 * 将表达式转化为context中的值，支持复杂的对象，如果没有对象，返回null
	 * 
	 * @Title: getProperty
	 * @param context 上下文
	 * @param key key
	 * @return
	 * @date 2012-5-9 下午3:02:28
	 * @author tonny
	 * @version 1.0
	 */
	private static Object getProperty(Map<String, Object> context, String key) {
		if (context.containsKey(key)) {
			return context.get(key);
		}
		int m = key.indexOf(DOT);
		if (m < 1 || m == key.length() - 1) {
			// 简单的对象
			return context.get(key);
		} else {
			// 复杂的对象
			String mkey = StringUtils.substringBefore(key, DOT);
			String subKey = StringUtils.substringAfter(key, DOT);
			Object object = context.get(mkey);
			if (object != null) {
				try {
					return PropertyUtils.getProperty(object, subKey);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 读取系统属性
	 * 
	 * @Title: getSystemProperty
	 * @param key
	 * @return
	 * @date 2012-5-9 下午3:16:43
	 * @author tonny
	 * @version 1.0
	 */
	private static String getSystemProperty(String key) {
		try {
			if (key.startsWith(SYSDATE)) {
				return DateFormatUtils.format(new Date(), StringUtils.substringAfter(key, SYSDATE));
			}
			return System.getProperty(key);
		} catch (SecurityException e) {
			return null;
		}
	}

	/**
	 * 将key按:=解析出key与默认值
	 * 
	 * @Title: extractDefaultReplacement
	 * @param key
	 * @return
	 * @date 2012-5-9 下午3:17:01
	 * @author tonny
	 * @version 1.0
	 */
	private static String[] extractDefaultReplacement(String key) {
		String[] result = new String[2];
		result[0] = key;
		int d = key.indexOf(":=");
		if (d != -1) {
			result[0] = key.substring(0, d);
			result[1] = key.substring(d + 2);
		}
		return result;
	}

}
