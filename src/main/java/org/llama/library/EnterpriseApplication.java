package org.llama.library;

import org.llama.library.cache.Cache;
import org.llama.library.cache.CacheManager;
import org.llama.library.cache.CacheManagerImpl;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.configuration.XMLConfig;
import org.llama.library.cryptography.CryptographyManagerImpl;
import org.llama.library.exception.ExceptionManagerImpl;
import org.llama.library.resources.MessageResourcesImpl;
import org.llama.library.setting.ApplicationSettingImpl;
import org.llama.library.sqlmapping.SQLMappingManagerImpl;
import org.llama.library.validation.ValidationManagerImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用服务接口，创建各组件的工厂类
 *
 * @author tonny
 */
public class EnterpriseApplication {
    private static final Map<String, AbstractComponentContainer> MODULES = new HashMap<String, AbstractComponentContainer>();

    public static final SimpleConfiguration CONFIGURATION = new XMLConfig("enterprise-library.xml");

    static {
        MODULES.put("setting", new ApplicationSettingImpl(CONFIGURATION));
        MODULES.put("cache", new CacheManagerImpl(CONFIGURATION));
        MODULES.put("security", new CryptographyManagerImpl(CONFIGURATION));
        MODULES.put("exception", new ExceptionManagerImpl(CONFIGURATION));
        MODULES.put("message", new MessageResourcesImpl(CONFIGURATION));
        MODULES.put("sql", new SQLMappingManagerImpl(CONFIGURATION));
        MODULES.put("validator", new ValidationManagerImpl(CONFIGURATION));
    }

    /**
     * 获得配置的组件管理器
     *
     * @param name 组件管理器名称
     * @return 组件管理器
     * @Title: getComponent
     * @date 2011-12-14 下午2:37:05
     * @author tonny
     * @version 1.0
     */
    public static AbstractComponentContainer getComponent(String name) {
        return MODULES.get(name);
    }


}
