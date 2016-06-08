package org.llama.library.setting;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.llama.library.AbstractComponentContainer;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.sqlmapping.SQLMappingManagerImpl;

/**
 * 读取系统配置属性。配置文件为中的appSettings.key
 * 
 * @author tonny
 */
public class ApplicationSettingImpl extends AbstractComponentContainer implements ApplicationSetting {
	public static Map<String, String> settings = new HashMap<String, String>();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param configuration
	 */
	public ApplicationSettingImpl(SimpleConfiguration configuration) {
		super(configuration);
		init();
	}

	/**
	 * 读取配置文件中的配置
	 * 
	 * @throws ConfigurationException 配置异常
	 */
	public void init() throws ConfigurationException {
		int keys = configuration.size("appSettings");
		for (int i = 0; i < keys; i++) {
			String location = configuration.getString("appSettings(" + i + ").location");
			if (StringUtils.isNotEmpty(location)) {
				try {
					Enumeration<URL> resources = SQLMappingManagerImpl.class.getClassLoader().getResources(location);
					while (resources.hasMoreElements()) {
						URL url = (URL) resources.nextElement();
						log.info("load properties from " + url);
						PropertiesConfiguration config = new PropertiesConfiguration(url.toString());
						Iterator<String> keys2 = config.getKeys();
						while (keys2.hasNext()) {
							String key = keys2.next();
							settings.put(key, config.getString(key));
						}
					}
				} catch (org.apache.commons.configuration.ConfigurationException e) {
					log.error(e);
				} catch (IOException e) {
					log.error(e);
				}
				continue;
			}
			keys = configuration.size("appSettings(" + i + ").key");
			for (int j = 0; j < keys; j++) {
				String key = "appSettings(" + i + ").key(" + j + ")";
				if (StringUtils.isNotEmpty(configuration.getString(key + ".value"))) {
					settings.put(configuration.getString(key + ".name"), configuration.getString(key + ".value"));
				} else {
					settings.put(configuration.getString(key + ".name"), configuration.getString(key));
				}
			}
		}

	}

	/*
	 * @see works.tonny.library.setting.ApplicationSetting#getSetting(java.lang.String)
	 */
	public String getSetting(String name) {
		return settings.get(name);
	}

	/*
	 * @see works.tonny.library.setting.ApplicationSetting#getIntSetting(java.lang.String )
	 */
	public int getIntSetting(String name) {
		return NumberUtils.toInt(name);
	}

	/*
	 * @see works.tonny.library.setting.ApplicationSetting#getFloatSetting(java.lang .String)
	 */
	public float getFloatSetting(String name) {
		return NumberUtils.toFloat(name);
	}

	/**
	 * @param name
	 * @return
	 * @see org.llama.library.AbstractComponentContainer#get(java.lang.String)
	 */
	@Override
	public Object get(String name) {
		return getSetting(name);
	}
}
