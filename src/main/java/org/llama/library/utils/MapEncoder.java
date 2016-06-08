/**
 * 
 */
package org.llama.library.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 将map序列化为字符串
 * 
 * @author 祥栋
 * @date 2014-6-16
 * @version 1.0.0
 */
public class MapEncoder {
	/**
	 * 将map序列化为字符串 map:[a=b,b=c] keySpliter: groupSpliter; = a:b;b:c;
	 * 
	 * @param map
	 * @param keySpliter
	 * @param groupSpliter
	 * @return
	 */
	public static String encode(Map<Object, Object> map, String keySpliter, String groupSpliter) {
		if (map == null) {
			return null;
		}
		if (map.isEmpty()) {
			return "";
		}

		Set<Entry<Object, Object>> entrySet = map.entrySet();
		StringBuffer buffer = new StringBuffer();
		for (Entry<Object, Object> entry : entrySet) {
			buffer.append(entry.getKey()).append(keySpliter).append(entry.getValue()).append(groupSpliter);
		}
		return buffer.toString();
	}

	/**
	 * 将map序列化为字符串 map:[a=b,b=c] keySpliter: groupSpliter; = a:b;b:c;
	 * 
	 * @param map
	 * @param keySpliter
	 * @param groupSpliter
	 * @return
	 */
	public static String encode(Map<?, ?> map, String keySpliter, String groupSpliter, String charset) {
		if (map == null) {
			return null;
		}
		if (map.isEmpty()) {
			return "";
		}

		Set<?> entrySet = map.entrySet();
		StringBuffer buffer = new StringBuffer();
		for (Object object : entrySet) {
			Map.Entry entry = (Entry) object;
			try {
				buffer.append(entry.getKey())
						.append(keySpliter)
						.append(URLEncoder.encode(entry.getValue() == null ? "" : entry.getValue().toString(), charset))
						.append(groupSpliter);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	/**
	 * 将字符串转换为map
	 * 
	 * @param map
	 * @param keySpliter
	 * @param groupSpliter
	 * @return
	 */
	public static Map<String, ? extends Object> decode(String str, String keySpliter, String groupSpliter) {
		if (str == null) {
			return null;
		}
		Map<String, String> map = new LinkedHashMap<String, String>();
		String[] split = StringUtils.split(str, groupSpliter);
		for (String string : split) {
			if (StringUtils.isEmpty(string)) {
				continue;
			}
			map.put(StringUtils.substringBefore(string, keySpliter), StringUtils.substringAfter(string, keySpliter));
		}
		return map;
	}

	/**
	 * 将字符串转换为map
	 * 
	 * @param map
	 * @param keySpliter
	 * @param groupSpliter
	 * @return
	 */
	public static <T> Map<String, T> decode(String str, String keySpliter, String groupSpliter, String charset, T type) {
		if (str == null) {
			return null;
		}
		Map<String, T> map = new LinkedHashMap<String, T>();
		String[] split = StringUtils.split(str, groupSpliter);
		for (String string : split) {
			if (StringUtils.isEmpty(string)) {
				continue;
			}
			map.put(StringUtils.substringBefore(string, keySpliter).trim(), (T) StringUtils.substringAfter(string, keySpliter));
		}
		return map;
	}
}