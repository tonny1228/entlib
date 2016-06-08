package org.llama.library.log;

import java.text.MessageFormat;
import java.util.logging.Logger;
import java.util.logging.Level;

;
/**
 * jdk日志适配器
 * <p>
 * jdk1.4以上都支持jdk log。jdk log支持7级输出，本日志与jdk级别对应关系如下:
 * <ul>
 * <li>debug-config</li>
 * <li>info-info</li>
 * <li>warn-warning</li>
 * <li>error-severe</li>
 * <li>fatal-severe</li>
 * </ul>
 * </p>
 * 
 * @author tonny
 * 
 */
public class JDKLogger implements org.llama.library.log.Logger {
	/**
	 * slf日志
	 */
	private Logger log;

	/**
	 * 初始化日志
	 * 
	 * @param name 日志名称
	 */
	public JDKLogger(String name) {
		log = Logger.getLogger(name);
	}

	/**
	 * 创建新的日志
	 * 
	 * @param clazz 类名
	 */
	public JDKLogger(Class clazz) {
		log = Logger.getLogger(clazz.getName());
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
			log.log(Level.CONFIG, message.toString(), e);
		} else {
			log.log(Level.CONFIG, null, e);
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
			log.config(MessageFormat.format(message, params));
		} else {
			log.config(params.toString());
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
			log.config(message.toString());
		} else {
			log.config(null);
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
			log.log(Level.SEVERE, message.toString(), e);
		else
			log.log(Level.SEVERE, null, e);
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
		log.log(Level.SEVERE, null, e);
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
			log.severe(MessageFormat.format(message, params));
		else
			log.severe(params.toString());
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
			log.severe(message.toString());
		else
			log.severe(null);
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
			log.log(Level.SEVERE, message.toString(), e);
		else
			log.log(Level.SEVERE, null, e);
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
			log.severe(MessageFormat.format(message, params));
		else
			log.severe(params.toString());
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
			log.severe(message.toString());
		else
			log.severe(null);
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
			log.log(Level.INFO, message.toString(), e);
		else
			log.log(Level.INFO, null, e);
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
			log.log(Level.WARNING, message.toString(), e);
		else
			log.log(Level.WARNING, null, e);
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
			log.warning(MessageFormat.format(message, params));
		else
			log.warning(null);
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
			log.warning(message.toString());
		else
			log.warning(null);
	}

	/**
	 * 是否支持debug
	 * 
	 * @return 是否支持debug
	 */
	public boolean isDebugEnabled() {
		return getLogLevel() <= Level.CONFIG.intValue();
	}

	/**
	 * @return
	 */
	protected int getLogLevel() {
		if (log.getLevel() == null) {
			return Level.ALL.intValue();
		}
		return log.getLevel().intValue();
	}

	/**
	 * 是否支持error
	 * 
	 * @return 是否支持输出错误
	 */
	public boolean isErrorEnabled() {
		return getLogLevel() <= Level.SEVERE.intValue();
	}

	/**
	 * 是否支持warn
	 * 
	 * @return 是否支持warn
	 */
	public boolean isFatalEnabled() {
		return getLogLevel() <= Level.SEVERE.intValue();
	}

	/**
	 * 是否支持info
	 * 
	 * @return 是否支持info
	 */
	public boolean isInfoEnabled() {
		return getLogLevel() <= Level.INFO.intValue();
	}

	/**
	 * 是否支持fatal
	 * 
	 * @return 是否支持fatal
	 */
	public boolean isWarnEnabled() {
		return getLogLevel() <= Level.WARNING.intValue();
	}

	/**
	 * 添加配置属性
	 * 
	 * @param key 属性名
	 * @param value 属性值
	 */
	public void put(String key, String value) {

	}

	/**
	 * 删除配置属性
	 * 
	 * @param key 属性名
	 */
	public void remove(String key) {

	}

}
