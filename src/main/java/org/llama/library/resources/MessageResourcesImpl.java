package org.llama.library.resources;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.llama.library.AbstractComponentContainer;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;


/**
 * 资源管理器，用户国际化
 * 
 * @author tonny
 * 
 */
public class MessageResourcesImpl extends AbstractComponentContainer implements MessageResources {

	/**
	 * 资源名称
	 */
	private String resourceName;

	private static Set<String> resourceNames;

	/**
	 * <p>
	 * </p>
	 * 
	 * @param configuration
	 */
	public MessageResourcesImpl(SimpleConfiguration configuration) {
		super(configuration);
		init();
	}

	@Override
	public void init() throws ConfigurationException {
		int cryptor = configuration.size("messageResources");
		for (int i = 0; i < cryptor; i++) {
			String name = configuration.getString("messageResources(" + i + ").name");
			String def = configuration.getString("messageResources(" + i + ").default");
			getResourceNames().add(name);
			if ("true".equalsIgnoreCase(def)) {
				resourceName = name;
			}
		}
	}

	private ResourceBundle getResource(String resourceName, String name) {
		return ResourceBundle.getBundle(resourceName, new Locale(name));
	}

	public ResourceBundle getResource(String name) {
		return getResource(resourceName, name);
	}

	/**
	 * 初始化资源文件名称
	 * 
	 * @Title: getResourceNames
	 * @return
	 * @date 2011-11-8 下午4:25:51
	 * @author tonny
	 * @version 1.0
	 */
	public synchronized static Set<String> getResourceNames() {
		if (resourceNames == null) {
			resourceNames = new HashSet<String>();
		}
		return resourceNames;
	}

	/**
	 * 从所有资源文件中查找资源值
	 * 
	 * @Title: getResourceValue
	 * @param name 资源名称
	 * @return 资源值
	 * @date 2011-11-8 下午4:28:47
	 * @author tonny
	 * @version 1.0
	 */
	public static String getResourceValue(String name) {
		for (String rname : getResourceNames()) {
			String value;
			try {
				value = ResourceBundle.getBundle(rname).getString(name);
			} catch (Exception e) {
				continue;
			}
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	/**
	 * @param name
	 * @return
	 * @see org.llama.library.AbstractComponentContainer#get(java.lang.String)
	 */
	@Override
	public Object get(String name) {
		if (name.contains(".")) {
			return ResourceBundle.getBundle(StringUtils.substringBefore(name, ".")).getString(
					StringUtils.substringAfter(name, "."));
		} else if (resourceName != null) {
			return ResourceBundle.getBundle(resourceName).getString(name);
		} else {
			return getResourceValue(name);
		}
	}

}
