package org.llama.library.utils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.llama.library.resources.MessageResourcesImpl;
import org.llama.library.utils.Assert;

/**
 * 对象声明器
 * 
 * @ClassName: Assert
 * @Description: 声明对象的属性特征，如果特征不附，抛出异常。一般用户对象非空等特征的检查。
 * @author Tonny
 * @date 2011-10-28 上午9:29:51
 * @version 1.0
 */
public abstract class Assert {
	private static Log log = LogFactory.getLog(Assert.class);

	private static int loaded = -1;

	/**
	 * 
	 * @Title: getMessage
	 * @param message
	 * @return
	 * @date 2011-11-8 下午4:29:45
	 * @author tonny
	 * @version 1.0
	 */
	private static String getMessage(String message, Object... args) {
		if (loaded == -1) {
			try {
				ResourceBundle.getBundle("assert").getString("hello");
				loaded = 1;
			} catch (MissingResourceException e) {
				log.info("assert.properties not found, load library config message resources");
				loaded = 0;
			}
		}
		if (!message.matches("[a-zA-Z0-9\\._]+")) {
			return MessageFormat.format(message, args);
		}

		String value = null;
		if (loaded == 1) {
			message = "assert." + message;
			value = ResourceBundle.getBundle("assert").getString(message);
		} else {
			value = MessageResourcesImpl.getResourceValue(message);
		}
		if (value == null) {
			value = message;
		}
		value = MessageFormat.format(value, args);
		if (value != null) {
			return value;
		} else {
			return message;
		}
	}

	/**
	 * Assert a boolean expression, throwing
	 * <code>IllegalArgumentException</code> if the test result is
	 * <code>false</code>.
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0, &quot;The value must be greater than zero&quot;);
	 * </pre>
	 * 
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if expression is <code>false</code>
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert a boolean expression, throwing
	 * <code>IllegalArgumentException</code> if the test result is
	 * <code>false</code>.
	 * 
	 * <pre class="code">
	 * Assert.isTrue(i &gt; 0);
	 * </pre>
	 * 
	 * @param expression a boolean expression
	 * @throws IllegalArgumentException if expression is <code>false</code>
	 */
	public static void isTrue(boolean expression) {
		isTrue(expression, "istrue");
	}

	/**
	 * Assert that an object is <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.isNull(value, &quot;The value must be null&quot;);
	 * </pre>
	 * 
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object is not <code>null</code>
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that an object is <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.isNull(value);
	 * </pre>
	 * 
	 * @param object the object to check
	 * @throws IllegalArgumentException if the object is not <code>null</code>
	 */
	public static void isNull(Object object) {
		isNull(object, "isnull");
	}

	/**
	 * Assert that an object is not <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz, &quot;The class must not be null&quot;);
	 * </pre>
	 * 
	 * @param object the object to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object is <code>null</code>
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that an object is not <code>null</code> .
	 * 
	 * <pre class="code">
	 * Assert.notNull(clazz);
	 * </pre>
	 * 
	 * @param object the object to check
	 * @throws IllegalArgumentException if the object is <code>null</code>
	 */
	public static void notNull(Object object) {
		notNull(object, "notnull");
	}

	/**
	 * Assert that the given String is not empty; that is, it must not be
	 * <code>null</code> and not the empty String.
	 * 
	 * <pre class="code">
	 * Assert.hasLength(name, &quot;Name must not be empty&quot;);
	 * </pre>
	 * 
	 * @param text the String to check
	 * @param message the exception message to use if the assertion fails
	 * @see StringUtils#hasLength
	 */
	public static void hasLength(String text, String message) {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that the given String is not empty; that is, it must not be
	 * <code>null</code> and not the empty String.
	 * 
	 * <pre class="code">
	 * Assert.hasLength(name);
	 * </pre>
	 * 
	 * @param text the String to check
	 * @see StringUtils#hasLength
	 */
	public static void hasLength(String text) {
		hasLength(text, "haslength");
	}

	/**
	 * Assert that the given String has valid text content; that is, it must not
	 * be <code>null</code> and must contain at least one non-whitespace
	 * character.
	 * 
	 * <pre class="code">
	 * Assert.hasText(name, &quot;'name' must not be empty&quot;);
	 * </pre>
	 * 
	 * @param text the String to check
	 * @param message the exception message to use if the assertion fails
	 * @see StringUtils#hasText
	 */
	public static void hasText(String text, String message) {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that the given String has valid text content; that is, it must not
	 * be <code>null</code> and must contain at least one non-whitespace
	 * character.
	 * 
	 * <pre class="code">
	 * Assert.hasText(name, &quot;'name' must not be empty&quot;);
	 * </pre>
	 * 
	 * @param text the String to check
	 * @see StringUtils#hasText
	 */
	public static void hasText(String text) {
		hasText(text, "hasText");
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * 
	 * <pre class="code">
	 * Assert.doesNotContain(name, &quot;rod&quot;, &quot;Name must not contain 'rod'&quot;);
	 * </pre>
	 * 
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 * @param message the exception message to use if the assertion fails
	 */
	public static void doesNotContain(String textToSearch, String substring, String message) {
		if (StringUtils.isNotBlank(textToSearch) && StringUtils.isNotBlank(substring)
				&& textToSearch.indexOf(substring) != -1) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that the given text does not contain the given substring.
	 * 
	 * <pre class="code">
	 * Assert.doesNotContain(name, &quot;rod&quot;);
	 * </pre>
	 * 
	 * @param textToSearch the text to search
	 * @param substring the substring to find within the text
	 */
	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring, "doesNotContain");
	}

	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(array, &quot;The array must have elements&quot;);
	 * </pre>
	 * 
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object array is <code>null</code>
	 *             or has no elements
	 */
	public static void notEmpty(Object[] array, String message) {
		if (ArrayUtils.isEmpty(array)) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that an array has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(array);
	 * </pre>
	 * 
	 * @param array the array to check
	 * @throws IllegalArgumentException if the object array is <code>null</code>
	 *             or has no elements
	 */
	public static void notEmpty(Object[] array) {
		notEmpty(array, "notEmpty");
	}

	/**
	 * Assert that an array has no null elements. Note: Does not complain if the
	 * array is empty!
	 * 
	 * <pre class="code">
	 * Assert.noNullElements(array, &quot;The array must have non-null elements&quot;);
	 * </pre>
	 * 
	 * @param array the array to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the object array contains a
	 *             <code>null</code> element
	 */
	public static void noNullElements(Object[] array, String message) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					throw new IllegalArgumentException(getMessage(message));
				}
			}
		}
	}

	/**
	 * Assert that an array has no null elements. Note: Does not complain if the
	 * array is empty!
	 * 
	 * <pre class="code">
	 * Assert.noNullElements(array);
	 * </pre>
	 * 
	 * @param array the array to check
	 * @throws IllegalArgumentException if the object array contains a
	 *             <code>null</code> element
	 */
	public static void noNullElements(Object[] array) {
		noNullElements(array, "noNullElements");
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
	 * </pre>
	 * 
	 * @param collection the collection to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the collection is <code>null</code>
	 *             or has no elements
	 */
	public static void notEmpty(Collection collection, String message) {
		if (CollectionUtils.isEmpty(collection)) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that a collection has elements; that is, it must not be
	 * <code>null</code> and must have at least one element.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(collection, &quot;Collection must have elements&quot;);
	 * </pre>
	 * 
	 * @param collection the collection to check
	 * @throws IllegalArgumentException if the collection is <code>null</code>
	 *             or has no elements
	 */
	public static void notEmpty(Collection collection) {
		notEmpty(collection, "notEmpty");
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(map, &quot;Map must have entries&quot;);
	 * </pre>
	 * 
	 * @param map the map to check
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalArgumentException if the map is <code>null</code> or has
	 *             no entries
	 */
	public static void notEmpty(Map map, String message) {
		if (MapUtils.isEmpty(map)) {
			throw new IllegalArgumentException(getMessage(message));
		}
	}

	/**
	 * Assert that a Map has entries; that is, it must not be <code>null</code>
	 * and must have at least one entry.
	 * 
	 * <pre class="code">
	 * Assert.notEmpty(map);
	 * </pre>
	 * 
	 * @param map the map to check
	 * @throws IllegalArgumentException if the map is <code>null</code> or has
	 *             no entries
	 */
	public static void notEmpty(Map map) {
		notEmpty(map, "notEmpty");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * 
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo);
	 * </pre>
	 * 
	 * @param clazz the required class
	 * @param obj the object to check
	 * @throws IllegalArgumentException if the object is not an instance of
	 *             clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class clazz, Object obj) {
		isInstanceOf(clazz, obj, "isInstanceOf");
	}

	/**
	 * Assert that the provided object is an instance of the provided class.
	 * 
	 * <pre class="code">
	 * Assert.instanceOf(Foo.class, foo);
	 * </pre>
	 * 
	 * @param type the type to check against
	 * @param obj the object to check
	 * @param message a message which will be prepended to the message produced
	 *            by the function itself, and which may be used to provide
	 *            context. It should normally end in a ": " or ". " so that the
	 *            function generate message looks ok when prepended to it.
	 * @throws IllegalArgumentException if the object is not an instance of
	 *             clazz
	 * @see Class#isInstance
	 */
	public static void isInstanceOf(Class type, Object obj, String message) {
		notNull(type, "checktype");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(getMessage(message, (obj != null ? obj.getClass().getName() : "null"),
					type));
		}
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is
	 * <code>true</code>.
	 * 
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass);
	 * </pre>
	 * 
	 * @param superType the super type to check
	 * @param subType the sub type to check
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class superType, Class subType) {
		isAssignable(superType, subType, "isAssignable");
	}

	/**
	 * Assert that <code>superType.isAssignableFrom(subType)</code> is
	 * <code>true</code>.
	 * 
	 * <pre class="code">
	 * Assert.isAssignable(Number.class, myClass);
	 * </pre>
	 * 
	 * @param superType the super type to check against
	 * @param subType the sub type to check
	 * @param message a message which will be prepended to the message produced
	 *            by the function itself, and which may be used to provide
	 *            context. It should normally end in a ": " or ". " so that the
	 *            function generate message looks ok when prepended to it.
	 * @throws IllegalArgumentException if the classes are not assignable
	 */
	public static void isAssignable(Class superType, Class subType, String message) {
		notNull(superType, "checktype");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(getMessage(message, subType, superType));
		}
	}

	/**
	 * Assert a boolean expression, throwing <code>IllegalStateException</code>
	 * if the test result is <code>false</code>. Call isTrue if you wish to
	 * throw IllegalArgumentException on an assertion failure.
	 * 
	 * <pre class="code">
	 * Assert.state(id == null, &quot;The id property must not already be initialized&quot;);
	 * </pre>
	 * 
	 * @param expression a boolean expression
	 * @param message the exception message to use if the assertion fails
	 * @throws IllegalStateException if expression is <code>false</code>
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(getMessage(message));
		}
	}

	/**
	 * Assert a boolean expression, throwing {@link IllegalStateException} if
	 * the test result is <code>false</code>.
	 * <p>
	 * Call {@link #isTrue(boolean)} if you wish to throw
	 * {@link IllegalArgumentException} on an assertion failure.
	 * 
	 * <pre class="code">
	 * Assert.state(id == null);
	 * </pre>
	 * 
	 * @param expression a boolean expression
	 * @throws IllegalStateException if the supplied expression is
	 *             <code>false</code>
	 */
	public static void state(boolean expression) {
		state(expression, "state");
	}

}
