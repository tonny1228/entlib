package org.llama.library.log;

import java.net.URL;
import java.util.HashMap;

import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.log.CommonsLogger;
import org.llama.library.log.JDKLogger;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.log.SLFLogger;


/**
 * 日志工厂类，产生日志
 * <p>
 * 日志根据环境去判断使用什么日志框架去输出
 * <ul>
 * <li>如果classpath中存在logback.xml使用slf4j+logback输出日志</li>
 * <li>如果classpath中存在log4j.xml使用commons-log+log4j输出日志</li>
 * <li>否则使用jdk log输出日志</li>
 * <ul>
 * </p>
 * 
 * @author 刘祥栋
 * 
 */
public class LogFactory {

	private static final String ERROR_MESSAGE = "初始化日志组件出错{0}";

	private static final String LOG4J_PROPERTIES = "log4j.properties";

	private static final String LOGBACK_XML = "logback.xml";

	private static final String JDK_LOG = "logging.properties";

	private static Class<? extends Logger> logClass;

	private static HashMap<String, Class<? extends Logger>> mappingedAdapter;

	private static SimpleConfiguration configuration;

	static {
		// 初始化日志框架
		mappingedAdapter = new HashMap<String, Class<? extends Logger>>();
		mappingedAdapter.put(LOGBACK_XML, SLFLogger.class);
		mappingedAdapter.put(LOG4J_PROPERTIES, CommonsLogger.class);
		mappingedAdapter.put(JDK_LOG, JDKLogger.class);
		logClass = guessLogFramework();
	}

	/**
	 * 根据名称创建日志实例
	 * 
	 * @param name 日志名称
	 * @return 日志实例
	 */
	public static Logger getLogger(String name) {
		try {
			return logClass.getConstructor(String.class).newInstance(name);
		} catch (Exception e) {
			Logger adapter = new JDKLogger(name);
			adapter.warn(ERROR_MESSAGE, logClass, e);
			return adapter;
		}
	}

	/**
	 * 根据类名创建日志实例
	 * 
	 * @param clazz 类
	 * @return 日志实例
	 */
	public static Logger getLogger(Class clazz) {
		try {
			return logClass.getConstructor(Class.class).newInstance(clazz);
		} catch (Exception e) {
			Logger adapter = new JDKLogger(clazz);
			adapter.warn(ERROR_MESSAGE, logClass, e);
			return adapter;
		}
	}

	/**
	 * 猜测用户使用了什么日志框架
	 * <ul>
	 * <li>如果classpath中存在logback.xml使用slf4j+logback输出日志</li>
	 * <li>如果classpath中存在log4j.xml使用commons-log+log4j输出日志</li>
	 * <li>否则使用jdk log输出日志</li>
	 * <ul>
	 * 
	 * @return 日志输出类
	 */
	private static Class<? extends Logger> guessLogFramework() {
		try {
			if (configuration != null) {
				String clz = configuration.getString("logging.class");
				return (Class<? extends Logger>) Class.forName(clz);
			}
		} catch (Exception e) {
		}

		Class<? extends Logger> clazz = verifySettings(LOGBACK_XML);
		if (clazz != null)
			return clazz;
		clazz = verifySettings(LOG4J_PROPERTIES);
		if (clazz != null)
			return clazz;
		return mappingedAdapter.get(JDK_LOG);
	}

	/**
	 * 根据日志配置文件查询日志输出类。配置文件存在于classpath中则返回
	 * 
	 * @param settingFile 配置文件名称
	 * @return 日志输出类
	 */
	protected static Class<? extends Logger> verifySettings(String settingFile) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(settingFile);
		if (url != null) {
			return mappingedAdapter.get(settingFile);
		} else {
			return null;
		}
	}

	public static void setConfiguration(SimpleConfiguration configuration) {
		LogFactory.configuration = configuration;
	}
}
