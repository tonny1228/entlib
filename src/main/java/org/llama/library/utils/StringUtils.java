/**
 * 
 */
package org.llama.library.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.text.StrBuilder;

/**
 * 
 * @author tonny
 * @date 2015-1-19
 * @version 1.0.0
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	/**
	 * 连接集合中所有元素值的某个属性
	 * 
	 * @param collection
	 * @param fieldName
	 * @param separator
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String join(Collection collection, String fieldName, String separator) {
		// two or more elements
		StrBuilder buf = null;
		try {
			if (collection == null) {
				return null;
			}
			if (collection.isEmpty()) {
				return EMPTY;
			}
			final Iterator iterator = collection.iterator();
			Object first = iterator.next();
			if (!iterator.hasNext()) {
				return BeanUtils.getProperty(first, fieldName);
			}

			buf = new StrBuilder(256);
			// too small
			if (first != null) {
				buf.append(BeanUtils.getProperty(first, fieldName));
			}

			while (iterator.hasNext()) {
				if (separator != null) {
					buf.append(separator);
				}
				Object obj = iterator.next();
				if (obj != null) {
					buf.append(BeanUtils.getProperty(first, fieldName));
				}
			}
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		}
		return buf.toString();
	}
}