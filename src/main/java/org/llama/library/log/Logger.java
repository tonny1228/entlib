package org.llama.library.log;

/**
 * 日志记录适配器
 * <p>
 * 适配各日志框架，如commons-log log4j slf4j jdkLog等。支持debug info warn error fatal
 * 5种级别对应各日志框架的相应的级别
 * </p>
 * 
 * @author tonny
 * 
 */
public interface Logger {

	/**
	 * 添加配置属性。某些框架支持根据属性过滤日志输出
	 * 
	 * @param key 属性名
	 * @param value 属性值
	 */
	void put(String key, String value);

	/**
	 * 删除配置属性
	 * 
	 * @param key 属性名
	 */
	void remove(String key);

	/**
	 * debug级别 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	void debug(Object message, Throwable e);

	/**
	 * debug级别 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */

	void debug(String message, Object... params);

	/**
	 * debug 信息
	 * 
	 * @param message 日志信息
	 */
	void debug(Object message);

	/**
	 * info 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	void info(Object message, Throwable e);

	/**
	 * info 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */

	void info(String message, Object... params);

	/**
	 * info 信息
	 * 
	 * @param message 日志信息
	 */
	void info(Object message);

	/**
	 * warn 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	void warn(Object message, Throwable e);

	/**
	 * warn 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */

	void warn(String message, Object... params);

	/**
	 * warn 信息
	 * 
	 * @param message 日志信息
	 */
	void warn(Object message);

	/**
	 * error 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	void error(Object message, Throwable e);

	/**
	 * error 错误信息
	 * 
	 * @param e 错误信息
	 */
	void error(Throwable e);

	/**
	 * error 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */
	void error(String message, Object... params);

	/**
	 * error 信息
	 * 
	 * @param message 日志信息
	 */
	void error(Object message);

	/**
	 * fatal 错误信息
	 * 
	 * @param message 日志信息
	 * @param e 错误信息
	 */
	void fatal(Object message, Throwable e);

	/**
	 * fatal 信息
	 * 
	 * @param message 日志板式化信息，可使用{0}{1}输出后面对数
	 * @param params 信息参数
	 */

	void fatal(String message, Object... params);

	/**
	 * fatal 信息
	 * 
	 * @param message 日志信息
	 */
	void fatal(Object message);

	/**
	 * 是否支持debug
	 * 
	 * @return 是否支持debug
	 */
	boolean isDebugEnabled();

	/**
	 * 是否支持error
	 * 
	 * @return 是否支持输出错误
	 */
	boolean isErrorEnabled();

	/**
	 * 是否支持info
	 * 
	 * @return 是否支持info
	 */
	boolean isInfoEnabled();

	/**
	 * 是否支持warn
	 * 
	 * @return 是否支持warn
	 */
	boolean isWarnEnabled();

	/**
	 * 是否支持fatal
	 * 
	 * @return 是否支持fatal
	 */
	boolean isFatalEnabled();
}
