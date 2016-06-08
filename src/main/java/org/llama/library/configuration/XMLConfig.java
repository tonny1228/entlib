package org.llama.library.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.llama.library.log.Log;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;

/**
 * xml配置工具
 * 
 * @author tonny
 */
public class XMLConfig implements SimpleConfiguration {
	private Logger log = LogFactory.getLogger(getClass());
	private XMLConfiguration config;

	/**
	 * 初始化配置文件
	 * 
	 * @param file 文件路径，可以是相对于classpath中的相对路径
	 * @throws ConfigurationException 配置异常
	 */
	public XMLConfig(String file) throws ConfigurationException {
		try {
			File f = new File(file);
			if (f.exists()) {
				this.config = new XMLConfiguration(file);
			} else {
				if (file.contains(":")) {
					try {
						this.config = new XMLConfiguration(new URL(file));
					} catch (MalformedURLException e) {
						this.config = new XMLConfiguration(file);
					}
				} else {
					this.config = new XMLConfiguration(file);
				}
			}
			// config.setDelimiterParsingDisabled(true);
			config.setDefaultListDelimiter((char) 0);
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			log.error(file + "解析错误:" + e.getMessage());
			throw new ConfigurationException(e);
		}
		config.setAutoSave(true);
	}

	/**
	 * 通过key获取配置字符串信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	public String getString(String key) {
		String value = config.getString(key);
		if (value != null || config.getMaxIndex(key) >= 0) {
			return value;
		}
		if (!key.contains(".")) {
			return value;
		}
		String attr = StringUtils.substringAfterLast(key, ".");
		String parentKey = StringUtils.substringBeforeLast(key, ".");
		value = config.configurationAt(parentKey).getString("[@" + attr + "]");
		return value;
	}

	/**
	 * 通过key获取配置整型信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	public int getInt(String key) {
		return NumberUtils.toInt(getString(key));
	}

	/**
	 * 通过key获取配置float型信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	public float getFloat(String key) {
		return NumberUtils.toFloat(getString(key));
	}

	/**
	 * 匹配key的配置的数量
	 * 
	 * @param key 关键字
	 * @return 匹配key的配置的数量
	 */
	public int size(String key) {
		return config.getMaxIndex(key) + 1;
	}

	/**
	 * 通过key获取配置字符串集合信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	public List<String> getList(String key) {
		return config.getList(key);
	}

	/**
	 * 设置为key的值
	 * 
	 * @param key 关键字
	 * @param value 值
	 */
	public void setProperty(String key, String value) {
		config.setProperty(key, value);
	}

	/**
	 * 列出此附合此键的所有子键
	 * 
	 * @param key 键
	 * @return 子键集合
	 */
	public List<String> keys(String key) {
		Iterator<String> keys = config.getKeys(key);
		List<String> subKeys = new ArrayList<String>();
		while (keys.hasNext()) {
			String subkey = keys.next();
			if (subkey.indexOf("[@") >= 0) {
				subkey = StringUtils.substringBetween(subkey, "[@", "]");
			}
			subKeys.add(subkey);
		}
		return subKeys;
	}

	/**
	 * 通过key获取配置字符串信息
	 * 
	 * @param parentKey 父键
	 * @param subKey 子键
	 * @param index 第index个父键
	 * @return 配置信息
	 */
	public String getString(String parentKey, String subKey, int index) {
		return getString(parentKey + "(" + index + ")." + subKey);
	}

	/*
	 * @see works.tonny.library.configuration.SimpleConfiguration#rootKeys()
	 */
	public List<String> rootKeys() {
		List<Node> roots = config.getRoot().getChildren();
		List<String> subKeys = new ArrayList<String>();
		for (Node node : roots) {
			subKeys.add(node.getName());
		}
		return subKeys;
	}
}
