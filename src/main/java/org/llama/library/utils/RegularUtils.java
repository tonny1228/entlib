/**
 * 
 */
package org.llama.library.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * 
 * @author 祥栋
 * @date 2013-5-15
 * @version 1.0.0
 */
public class RegularUtils {

	/**
	 * 查询字符串中匹配项
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static String[] matchedString(String str, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		List<String> found = new ArrayList<String>();
		while (matcher.find()) {
			if (matcher.groupCount() > 0) {
				found.add(matcher.group(1));
			} else {
				found.add(matcher.group());
			}
			// buffer.append(matcher.)
		}
		return found.toArray(new String[0]);
	}
}