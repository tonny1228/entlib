/**
 * 
 */
package org.llama.library.utils;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtilsBean;

/**
 * @author 祥栋
 * @date 2014-6-26
 * @version 1.0.0
 */
public class ConvertUtils {
	static ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
	static {
		convertUtilsBean.register(new org.llama.library.validation.validator.DateConverter(), Date.class);
	}

	public static <T> T convert(Class clz, Object value) {
		return (T) convertUtilsBean.convert(value, clz);
	}
}
