package org.llama.library.log;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.log4j.MDC;
import org.apache.log4j.PropertyConfigurator;
import org.llama.library.utils.WebAppPath;

/**
 * commons-log日志适配器，通过commons-log组件输出日志
 * <p>
 * 各日志级别与commons-log级别正好匹配，不再详细说明
 * </p>
 * 
 * @author tonny
 */
public class CommonsLogger implements Logger, Serializable {
	/**
	 * slf日志
	 */
	private Log log;

	static {
		//PropertyConfigurator.configure(WebAppPath.classesPath() + "/log4j.properties");
	}

	/**
	 * 初始化日志
	 * 
	 * @param name 日志名称
	 */
	public CommonsLogger(String name) {
		log = org.apache.commons.logging.LogFactory.getLog(name);
	}

	/**
	 * 创建新的日志
	 * 
	 * @param clazz 类名
	 */
	public CommonsLogger(Class clazz) {
		log = org.apache.commons.logging.LogFactory.getLog(clazz);
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
			log.debug(message, e);
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
			log.debug(MessageFormat.format(message.toString(), params));
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
			log.debug(message);
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
			log.error(message, e);
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
			log.error(message);
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
			log.fatal(message, e);
		else
			log.fatal(null, e);
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
			log.fatal(MessageFormat.format(message, params));
		else
			log.fatal(null);
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
			log.fatal(message);
		else
			log.fatal(null);
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
			log.info(message, e);
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
			log.info(message);
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
