package org.llama.library.log;

import java.text.MessageFormat;

import org.llama.library.log.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * slf4j日志适配器
 * <p>
 * 本日志与slf4j级别对应关系如下:
 * <ul>
 * <li>debug-debug</li>
 * <li>info-info</li>
 * <li>warn-warn</li>
 * <li>error-error</li>
 * <li>fatal-error</li>
 * </ul>
 * </p>
 * 
 * @author tonny
 * 
 */
public class SLFLogger implements Logger {
	/**
	 * slf日志
	 */
	private org.slf4j.Logger log;

	/**
	 * 初始化日志
	 * 
	 * @param name 日志名称
	 */
	public SLFLogger(String name) {
		log = LoggerFactory.getLogger(name);
	}

	/**
	 * 创建新的日志
	 * 
	 * @param clazz 类名
	 */
	public SLFLogger(Class clazz) {
		log = LoggerFactory.getLogger(clazz);
	}

	/**
	 * debug 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	public void debug(Object message, Throwable e) {
		if (!isDebugEnabled()) {
			return;
		}
		if (message != null) {
			log.debug(message.toString(), e);
		} else {
			log.debug(null, e);
		}
	}

	/**
	 * debug 信息
	 * 
	 * @param message 日志信息
	 * @param params 信息参数
	 */
	public void debug(String message, Object... params) {
		if (!isDebugEnabled()) {
			return;
		}
		if (message != null) {
			log.debug(MessageFormat.format(message, params));
		} else {
			log.debug(null);
		}
	}

	/**
	 * debug 信息
	 * 
	 * @param message 日志信息
	 */
	public void debug(Object message) {
		if (!isDebugEnabled()) {
			return;
		}
		if (message != null) {
			log.debug(message.toString());
		} else {
			log.debug(null);
		}
	}

	/**
	 * error 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	public void error(Object message, Throwable e) {
		if (!isErrorEnabled()) {
			return;
		}
		if (message != null)
			log.error(message.toString(), e);
		else
			log.error(e.getMessage(), e);
	}
	
	/**
	 * error 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	public void error(Throwable e) {
		if (!isErrorEnabled()) {
			return;
		}
		log.error(e.getMessage(), e);
	}


	/**
	 * error 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */
	public void error(String message, Object... params) {
		if (!isErrorEnabled()) {
			return;
		}
		if (message != null)
			log.error(MessageFormat.format(message, params));
		else
			log.error(null);
	}

	/**
	 * error 信息
	 * 
	 * @param message 日志信息
	 */
	public void error(Object message) {
		if (!isErrorEnabled()) {
			return;
		}
		if (message != null)
			log.error(message.toString());
		else
			log.error(null);
	}

	/**
	 * fatal 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	public void fatal(Object message, Throwable e) {
		if (!isFatalEnabled()) {
			return;
		}
		if (message != null)
			log.error(message.toString(), e);
		else
			log.error(null, e);
	}

	/**
	 * fatal 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */
	public void fatal(String message, Object... params) {
		if (!isFatalEnabled()) {
			return;
		}
		if (message != null)
			log.error(MessageFormat.format(message, params));
		else
			log.error(null);
	}

	/**
	 * fatal 信息
	 * 
	 * @param message 日志信息
	 */
	public void fatal(Object message) {
		if (!isFatalEnabled()) {
			return;
		}
		if (message != null)
			log.error(message.toString());
		else
			log.error(null);
	}

	/**
	 * info 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	public void info(Object message, Throwable e) {
		if (!isInfoEnabled()) {
			return;
		}
		if (message != null)
			log.info(message.toString(), e);
		else
			log.info(null, e);
	}

	/**
	 * info 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */
	public void info(String message, Object... params) {
		if (!isInfoEnabled()) {
			return;
		}
		if (message != null)
			log.info(MessageFormat.format(message, params));
		else
			log.info(null);
	}

	/**
	 * info 信息
	 * 
	 * @param message 日志信息
	 */
	public void info(Object message) {
		if (!isInfoEnabled()) {
			return;
		}
		if (message != null)
			log.info(message.toString());
		else
			log.info(null);
	}

	/**
	 * warn 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	public void warn(Object message, Throwable e) {
		if (!isWarnEnabled()) {
			return;
		}
		if (message != null)
			log.warn(message.toString(), e);
		else
			log.warn(null);
	}

	/**
	 * warn 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */
	public void warn(String message, Object... params) {
		if (!isWarnEnabled()) {
			return;
		}
		if (message != null)
			log.warn(MessageFormat.format(message, params));
		else
			log.warn(null);
	}

	/**
	 * warn 信息
	 * 
	 * @param message 日志信息
	 */
	public void warn(Object message) {
		if (!isWarnEnabled()) {
			return;
		}
		if (message != null)
			log.warn(message.toString());
		else
			log.warn(null);
	}

	/**
	 * 是否支持debug
	 * 
	 * @return 是否支持debug
	 */
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	/**
	 * 是否支持error
	 * 
	 * @return 是否支持输出错误
	 */
	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}

	/**
	 * 是否支持warn
	 * 
	 * @return 是否支持warn
	 */
	public boolean isFatalEnabled() {
		return log.isErrorEnabled();
	}

	/**
	 * 是否支持info
	 * 
	 * @return 是否支持info
	 */
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	/**
	 * 是否支持fatal
	 * 
	 * @return 是否支持fatal
	 */
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	/**
	 * 添加配置属性
	 * 
	 * @param key 属性名
	 * @param value 属性值
	 */
	public void put(String key, String value) {
		MDC.put(key, value);
	}

	/**
	 * 删除配置属性
	 * 
	 * @param key 属性名
	 */
	public void remove(String key) {
		MDC.remove(key);
	}

}
