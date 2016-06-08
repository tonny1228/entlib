package org.llama.library.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.*;

/**
 * http request 工具，用于在其他无法获取httprequest的类中获取当前请求的信息。
 * 只能用于与request同一线程的类中获取，新启动的线程无法获得 .
 * 需要在web.xml配置要过滤器HttpRequestUtilsFilter的地址,才能使用
 *
 * @author Tonny
 * @version 1.0
 * @ClassName: HttpRequestUtils
 * @date 2012-4-18 下午4:24:58
 */
public class HttpRequestUtils {

    /**
     * 请求线程池
     */
    public static final ThreadLocal<HttpServletRequest> REQUESTS = new ThreadLocal<HttpServletRequest>();
    /**
     *
     */
    private static final String LOCALHOST = "127.0.0.1";
    /**
     *
     */
    private static final String _0_0_0_0_0_0_0_1 = "0:0:0:0:0:0:0:1";
    /**
     *
     */
    private static final String UNKNOWN = "unknown";
    /**
     *
     */
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    /**
     *
     */
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    /**
     *
     */
    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * 当前线程绑定的请求
     *
     * @return 当前线程绑定的请求
     * @Title: currentRequest
     * @date 2012-4-18 下午4:26:46
     * @author tonny
     * @version 1.0
     */
    public static HttpServletRequest currentRequest() {
        return REQUESTS.get();
    }

    /**
     * 创建bean，并将request中对应的侦值赋值到对象
     *
     * @param request request
     * @param clazz   对象类
     * @return 对象
     * @throws ServletException 异常
     */
    public static Object createBean(HttpServletRequest request, Class clazz) throws ServletException {
        Object o;
        try {
            o = ClassUtils.newInstance(clazz);
            populate(o, request);
        } catch (Exception e) {
            throw new ServletException("RequestBeanWrapper.createBean:" + e.getMessage(), e);
        }
        return o;
    }

    /**
     * 创建bean，并将request中对应的侦值赋值到对象
     *
     * @param request request
     * @param clazz   对象类
     * @return 对象
     * @throws ServletException 异常
     */
    public static void updateBean(HttpServletRequest request, Object object) throws ServletException {
        try {
            populate(object, request);
        } catch (Exception e) {
            throw new ServletException("RequestBeanWrapper.updateBean:" + e.getMessage(), e);
        }
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @param encoding
     * @return
     */
    public static String queryString(HttpServletRequest request, String encoding) {
        if (request.getMethod().equalsIgnoreCase("get")) {
            return request.getQueryString();
        }
        StringBuffer buffer = new StringBuffer();
        Map parameterMap = request.getParameterMap();
        Set keySet = parameterMap.keySet();
        for (Object object : keySet) {
            String[] values = (String[]) parameterMap.get(object);
            for (int i = 0; i < values.length; i++) {
                try {
                    buffer.append(object).append("=").append(URLEncoder.encode(values[i], encoding)).append("&");
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    /**
     * 并将request中对应的侦值赋值到对象
     *
     * @param bean    对象
     * @param request request
     * @throws IllegalAccessException    异常
     * @throws InvocationTargetException 异常
     * @throws NoSuchMethodException
     */
    private static void populate(Object bean, HttpServletRequest request) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        HashMap<String, Object> properties = new HashMap<String, Object>();
        Enumeration<String> names = null;

        String contentType = request.getContentType();
        String method = request.getMethod();

        if ((contentType != null) && (contentType.startsWith("multipart/form-data"))
                && (method.equalsIgnoreCase("POST"))) {
            return;
        }

        names = request.getParameterNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String[] parameterValue = request.getParameterValues(name);
            properties.put(name, parameterValue);
            initProperty(bean, name);
        }
        BeanUtils.populate(bean, properties);
    }

    /**
     * @param bean
     * @param name
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static void initProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        while (name.contains(".")) {
            String field = StringUtils.substringBefore(name, ".");
            Field f = FieldUtils.getDeclaredField(bean.getClass(), field, true);
            Object newInstance = null;
            if (f.getType().equals(Set.class)) {
                Set property = (Set) PropertyUtils.getProperty(bean, field);
                if (property == null) {
                    newInstance = new LinkedHashSet();
                } else {
                    newInstance = property;
                }
                ((Set) newInstance).add(null);
            } else {
                newInstance = ClassUtils.newInstance(f.getType());
            }
            PropertyUtils.setProperty(bean, field, newInstance);
            name = StringUtils.substringAfter(name, ".");
        }
    }

    /**
     * 获取用户真实ip
     *
     * @param request
     * @return
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals(_0_0_0_0_0_0_0_1) ? LOCALHOST : ip;
    }

    public static UserAgent getUserAgent(String userAgent) {
        String os = null, browserName = null, browserVersion = null, browserEngine = null;
        if (userAgent.contains("MSIE")) {
            String[] matchedString = RegularUtils.matchedString(userAgent, "Windows[^;]+");
            if (matchedString != null && matchedString.length > 0) {
                os = matchedString[0];
            }
            browserName = "IE";
            matchedString = RegularUtils.matchedString(userAgent, "MSIE[^;]+");
            if (matchedString != null && matchedString.length > 0) {
                browserVersion = StringUtils.substringBeforeLast(matchedString[0], " ");
            }
            browserEngine = "Trident";
        } else if (userAgent.contains("Chrome")) {
            String[] matchedString = RegularUtils.matchedString(userAgent, "\\([^\\)]+\\)");
            if (matchedString != null && matchedString.length > 0) {
                os = StringUtils.substringBefore(matchedString[0], ";").substring(1);
            }
            browserName = "Chrome";
            matchedString = RegularUtils.matchedString(userAgent, "Chrome/[^\\s]+");
            if (matchedString != null && matchedString.length > 0) {
                browserVersion = StringUtils.substringAfter(matchedString[0], "/");
            }
            browserEngine = "AppleWebKit";
        } else if (userAgent.contains("Firefox")) {
            String[] matchedString = RegularUtils.matchedString(userAgent, "\\([^\\)]+\\)");
            if (matchedString != null && matchedString.length > 0) {
                os = StringUtils.substringBefore(matchedString[0], ";").substring(1);
            }
            browserName = "Firefox";
            matchedString = RegularUtils.matchedString(userAgent, "Firefox/[^\\s]+");
            if (matchedString != null && matchedString.length > 0) {
                browserVersion = StringUtils.substringAfter(matchedString[0], "/");
            }
            browserEngine = "Gecko";
        } else if (userAgent.contains("Opera")) {
            String[] matchedString = RegularUtils.matchedString(userAgent, "\\([^\\)]+\\)");
            if (matchedString != null && matchedString.length > 0) {
                os = StringUtils.substringBefore(matchedString[0], ";").substring(1);
            }
            browserName = "Opera";
            matchedString = RegularUtils.matchedString(userAgent, "Opera/[^\\s]+");
            if (matchedString != null && matchedString.length > 0) {
                browserVersion = StringUtils.substringAfter(matchedString[0], "/");
            }
            browserEngine = "Presto";
        } else if (userAgent.contains("Safari")) {
            String[] matchedString = RegularUtils.matchedString(userAgent, "\\([^\\)]+\\)");
            if (matchedString != null && matchedString.length > 0) {
                os = StringUtils.substringBefore(matchedString[0], ";").substring(1);
            }
            browserName = "Safari";
            matchedString = RegularUtils.matchedString(userAgent, "Safari/[^\\s]+");
            if (matchedString != null && matchedString.length > 0) {
                browserVersion = StringUtils.substringAfter(matchedString[0], "/");
            }
            browserEngine = "AppleWebKit";
        } else if (userAgent.contains("Mozilla")) {
            String[] matchedString = RegularUtils.matchedString(userAgent, "\\([^\\)]+\\)");
            if (matchedString != null && matchedString.length > 0) {
                os = StringUtils.substringBefore(matchedString[0], " ").substring(1);
            }
            if (matchedString != null && matchedString.length > 0) {
                browserVersion = StringUtils.substringAfter(matchedString[0], " ");
                if (StringUtils.isNotEmpty(browserVersion)) {
                    browserVersion = browserVersion.replace(")", "");
                }
            }
            browserEngine = "AppleWebKit";
        } else {
            os = "unknown";
            browserName = "unknown";
            browserVersion = "unknown";
            browserEngine = "unknown";
        }
        return new UserAgent(os, browserName, browserVersion, browserEngine);
    }

    public static class UserAgent {
        private String os;

        private String browserName;

        private String browserVersion;

        private String browserEngine;

        public UserAgent(String os, String browserName, String browserVersion, String browserEngine) {
            super();
            this.os = os;
            this.browserName = browserName;
            this.browserVersion = browserVersion;
            this.browserEngine = browserEngine;
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getBrowserName() {
            return browserName;
        }

        public void setBrowserName(String browserName) {
            this.browserName = browserName;
        }

        public String getBrowserVersion() {
            return browserVersion;
        }

        public void setBrowserVersion(String browserVersion) {
            this.browserVersion = browserVersion;
        }

        public String getBrowserEngine() {
            return browserEngine;
        }

        public void setBrowserEngine(String browserEngine) {
            this.browserEngine = browserEngine;
        }

    }
}
