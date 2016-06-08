/**
 * 
 */
package org.llama.library.log;

/**
 * @author 祥栋
 * @date 2013-4-16
 * @version 1.0.0
 */
public class Log {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#debug(java.lang.Object, java.lang.Throwable)
	 */
	public static void debug(Object message, Throwable e) {
		getLogger().debug(message, e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#debug(java.lang.String, java.lang.Object[])
	 */
	public static void debug(String message, Object... params) {
		getLogger().debug(message, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#debug(java.lang.Object)
	 */
	public static void debug(Object message) {
		getLogger().debug(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#info(java.lang.Object, java.lang.Throwable)
	 */
	public static void info(Object message, Throwable e) {
		getLogger().info(message, e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#info(java.lang.String, java.lang.Object[])
	 */
	public static void info(String message, Object... params) {
		getLogger().info(message, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#info(java.lang.Object)
	 */
	public static void info(Object message) {
		getLogger().info(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#warn(java.lang.Object, java.lang.Throwable)
	 */
	public static void warn(Object message, Throwable e) {
		getLogger().warn(message, e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#warn(java.lang.String, java.lang.Object[])
	 */
	public static void warn(String message, Object... params) {
		getLogger().warn(message, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#warn(java.lang.Object)
	 */
	public static void warn(Object message) {
		getLogger().warn(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#error(java.lang.Object, java.lang.Throwable)
	 */
	public static void error(Object message, Throwable e) {
		getLogger().error(message, e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#error(java.lang.Throwable)
	 */
	public static void error(Throwable e) {
		getLogger().error(e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#error(java.lang.String, java.lang.Object[])
	 */
	public static void error(String message, Object... params) {
		getLogger().error(message, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#error(java.lang.Object)
	 */
	public static void error(Object message) {
		getLogger().error(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#fatal(java.lang.Object, java.lang.Throwable)
	 */
	public static void fatal(Object message, Throwable e) {
		getLogger().fatal(message, e);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#fatal(java.lang.String, java.lang.Object[])
	 */
	public static void fatal(String message, Object... params) {
		getLogger().fatal(message, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.log.Logger#fatal(java.lang.Object)
	 */
	public static void fatal(Object message) {
		getLogger().fatal(message);
	}

	public static Logger getLogger() {
		return LogFactory.getLogger(getClassName());
	}

	private static String getClassName() {
		StackTraceElement[] stackTraceElements = Thread.getAllStackTraces().get(Thread.currentThread());
		return stackTraceElements[5].getClassName() + "." + stackTraceElements[5].getMethodName() + "("
				+ stackTraceElements[5].getLineNumber() + ")";
	}
}
