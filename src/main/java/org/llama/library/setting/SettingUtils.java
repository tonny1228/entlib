/**  
 * @Title: ExceptionUtils.java
 * @Package works.tonny.masp.utils
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 */
package org.llama.library.setting;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 异常处理工具
 * 
 * @ClassName: ExceptionUtils
 * @Description:
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 * @version 1.0
 */
public class SettingUtils {

	public static String getSetting(String name) {
		return ApplicationSettingImpl.settings.get(name);
	}

	public static int getIntSetting(String name) {
		return NumberUtils.toInt(ApplicationSettingImpl.settings.get(name));
	}
}
