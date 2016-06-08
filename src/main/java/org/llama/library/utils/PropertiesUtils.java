package org.llama.library.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 属性复制工具
 * 
 * @author tonny
 */
public class PropertiesUtils {
	private static Log log = LogFactory.getLog(PropertiesUtils.class);

	/**
	 * 复制对象中非空的属性
	 * 
	 * @param dest
	 * @param orig
	 * @param names
	 */
	public static void copyNotNullProperties(Object dest, Object orig, String... names) {
		if (names == null || names.length == 0) {
			Field[] declaredFields = dest.getClass().getDeclaredFields();
			names = new String[declaredFields.length];
			for (int i = 0; i < declaredFields.length; i++) {
				if (!PropertyUtils.isWriteable(dest, declaredFields[i].getName())) {
					continue;
				}
				names[i] = declaredFields[i].getName();
			}
		}

		for (String name : names) {
			try {
				if (name == null) {
					continue;
				}
				Object value = PropertyUtils.getProperty(orig, name);
				if (value != null) {
					PropertyUtils.setProperty(dest, name, value);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	/**
	 * 复制对象中指定的属性
	 * 
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            原始对象
	 * @param names
	 *            属性名
	 */
	public static void copyProperties(Object dest, Object orig, String... names) {
		for (String name : names) {
			try {
				Object value = PropertyUtils.getProperty(orig, name);
				PropertyUtils.setProperty(dest, name, value);
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	/**
	 * 复制对象中的属性，指定的除外
	 * 
	 * @param dest
	 *            目标对象
	 * @param orig
	 *            原始对象
	 * @param names
	 *            属性名
	 */
	public static void copyExcludesProperties(Object dest, Object orig, String... excludes) {
		List<String> list = new ArrayList<String>();
		for (String string : excludes) {
			list.add(string);
		}
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(orig);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			if (!list.contains(propertyDescriptor.getName())) {
				try {
					Object value = PropertyUtils.getProperty(orig, propertyDescriptor.getName());
					PropertyUtils.setProperty(dest, propertyDescriptor.getName(), value);
				} catch (IllegalAccessException e) {
					log.warn(e);
					continue;
				} catch (InvocationTargetException e) {
					log.warn(e);
					continue;
				} catch (NoSuchMethodException e) {
					log.warn(e);
					continue;
				}
			}
		}
	}
}
