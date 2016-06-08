package org.llama.library.sqlmapping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.llama.library.AbstractComponentContainer;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.configuration.XMLConfig;
import org.llama.library.log.Log;
import org.llama.library.setting.ApplicationSettingImpl;
import org.llama.library.utils.ELHelper;
import org.llama.library.utils.WebAppPath;

/**
 * sql 配置文件载入工具，配置文件路径为classpath:SQLMapping.xml
 *
 * @author tonny
 */
public class SQLMappingManagerImpl extends AbstractComponentContainer implements SQLMappingManager {
    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

    /**
     * 通过名称储存sql配置
     */
    static Map<String, Query> mappings = new HashMap<String, Query>();

    private static String dbms;

    /**
     * <p>
     * </p>
     *
     * @param configuration
     */
    public SQLMappingManagerImpl(SimpleConfiguration configuration) {
        super(configuration);
        init();
    }

    public void init() throws SQLMappingException {
        try {
            dbms = ELHelper.execute(configuration.getString("SQLMappings.dbms"), ApplicationSettingImpl.settings);
            List<String> list = configuration.getList("SQLMappings.file");
            for (String file : list) {
                if (file.contains("*")) {
                    log.info("scanning for " + file);
                    String folder = StringUtils.substringBeforeLast(file, "/");
                    String filename = StringUtils.substringAfterLast(file, "/");
                    String prefix = StringUtils.substringBeforeLast(filename, "*");
                    String suffix = StringUtils.substringAfterLast(filename, "*");
                    if (StringUtils.isNotEmpty(folder)) {
                        File f = new File(WebAppPath.classes(), folder);
                        String[] subs = f.list();
                        if (subs != null)
                            for (String sf : subs) {
                                if (sf.startsWith(prefix)
                                        && ((sf.indexOf(".") == sf.lastIndexOf(".") && sf.endsWith(suffix)))) {
                                    loadMapping(folder + "/" + sf);
                                }
                            }
                    }
                } else {
                    loadMapping(file);
                }
            }
        } catch (Exception e) {
            throw new SQLMappingException(e);
        }
    }

    /**
     * 载入默认的配置文件
     *
     * @throws ConfigurationException
     * @throws IOException
     */
    public static void loadMapping(String file) throws ConfigurationException {
        // File f = new File(file);
        // if (f.exists()) {
        // file = file.replace(".xml", "." + suffix + ".xml");
        // }
        try {
            Enumeration<URL> resources = SQLMappingManagerImpl.class.getClassLoader().getResources(file);
            while (resources.hasMoreElements()) {
                URL url = (URL) resources.nextElement();
                Log.info("load from " + url);
                SimpleConfiguration config = new XMLConfig(url.toString());
                int packageCount = config.size("package");
                for (int i = 0; i < packageCount; i++) {
                    loadPackages(config, "package(" + i + ")");
                }
            }
        } catch (IOException e) {
            throw new ConfigurationException("配置文件载入失败");
        }
    }

    /**
     * 加载配置文件中的包
     *
     * @param config 配置
     * @param key    键
     */
    private static void loadPackages(SimpleConfiguration config, String key) {
        int queryCount = config.size(key + ".query");
        for (int i = 0; i < queryCount; i++) {
            String name = config.getString(key + ".name");
            loadQueries(config, key + ".query(" + i + ")", name);
        }

    }

    /**
     * 载入包中的查询
     *
     * @param config      配置
     * @param key         键
     * @param packageName 包名
     */
    private static void loadQueries(SimpleConfiguration config, String key, String packageName) {
        Query query = new Query();
        String name = config.getString(key + ".name");
        query.name = packageName + "." + name;
        String dbmsSql = config.getString(key + ".sql." + dbms);
        if (dbmsSql != null) {
            query.sql = dbmsSql.replaceAll("\\s\\s+", " ");
        } else {
            query.sql = config.getString(key + ".sql").replaceAll("\\s\\s+", " ");
        }

        mappings.put(query.name, query);
        int appenderCount = config.size(key + ".appender");
        for (int i = 0; i < appenderCount; i++) {
            String appenderName = config.getString(key + ".appender(" + i + ")" + ".name");
            if (query.mapedAppender == null) {
                query.mapedAppender = new HashMap<String, String>();
            }
            String dbmsAppender = config.getString(key + ".appender(" + i + ")." + dbms);
            if (dbmsAppender != null)
                query.mapedAppender.put(appenderName, dbmsAppender);
            else
                query.mapedAppender.put(appenderName, config.getString(key + ".appender(" + i + ")"));
        }

    }

    /**
     * 通过名称获得配置 语句
     *
     * @param name 包+查询的名称
     * @return 配置语句
     */
    public Query getQuery(String name) {
        try {
            return (Query) mappings.get(name).clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * @param name
     * @return
     * @see org.llama.library.AbstractComponentContainer#get(java.lang.String)
     */
    @Override
    public Object get(String name) {
        try {
            return mappings.get(name).clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
