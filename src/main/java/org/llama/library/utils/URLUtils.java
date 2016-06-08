package org.llama.library.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIUtils;
import org.llama.library.utils.ClassUtils;
import org.llama.library.utils.URLUtils;

/**
 * http请求工具类，提供访问接口
 *
 * @author 刘祥栋
 * @ClassName: HttpHelper
 * @date 2010-11-1 下午03:29:07
 */
public class URLUtils {

    /**
     *
     */
    private static final String QUERY_CONCAT = "&";

    /**
     *
     */
    private static final String EQUALS = "=";

    /**
     *
     */
    private static final String PATH_SEPARATOR = "/";

    /**
     *
     */
    private static final String QUERY_SEPARATOR = "?";

    /**
     *
     */
    public static final String SCHEME_SEPARATOR = "://";

    /**
     * 转码编码
     */
    public static final String CHARSET = "GBK";

    /**
     * =号
     */
    public static final String EQUAL = EQUALS;

    /**
     * &
     */
    public static final String AND = QUERY_CONCAT;

    /**
     * &amp;
     */
    public static final String AND_AMP = "&amp;";

    public static final String PARAMETER_APPENDER = QUERY_SEPARATOR;

    public static final String URL_ENCODER = "utf-8";

    private static final String PARENT_DIR = "../";

    private static final String CURRENT_DIR = "./";

    private static final String D_OBLIQUE_LINE = "//";

    private static final String OBLIQUE_LINE = PATH_SEPARATOR;

    private static final String URL_BLANK = "%20";

    private static final String BLANK = " ";

    private static final String HTTPS = "https://";

    /**
     * 地址前缀
     */
    private static final String HTTP = "http://";

    /**
     * 将request参数转为字符串
     *
     * @param request 请求
     * @return 参数拼接的字符串
     */
    public static String parameterToString(HttpServletRequest request) {
        StringBuffer buffer = new StringBuffer();
        try {
            Enumeration<String> keys = request.getParameterNames();
            while (keys.hasMoreElements()) {
                String k = keys.nextElement();
                buffer.append(k).append(EQUAL);
                String value = request.getParameter(k);
                if (value != null) {
                    buffer.append(URLEncoder.encode(request.getParameter(k), CHARSET)).append(AND);
                } else {
                    buffer.append(StringUtils.EMPTY).append(AND);
                }
            }

            if (buffer.length() > 0) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 将参数拆分为map
     *
     * @param params 参数串
     * @return map
     */
    public static Map<String, String> queryStringToMap(String params) {
        String[] param = params.split(AND);
        Map<String, String> map = new HashMap<String, String>();
        try {
            for (int i = 0; i < param.length; i++) {
                map.put(StringUtils.substringBefore(param[i], EQUAL),
                        URLDecoder.decode(StringUtils.substringAfter(param[i], EQUAL), URLUtils.CHARSET));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将请求的参数追加到地址后
     *
     * @param url   请求地址
     * @param param 新的参数
     * @return 新的地址
     */
    public static String appendParameters(String url, String param) {
        if (url == null) {
            return PARAMETER_APPENDER + param;
        }
        if (StringUtils.isEmpty(param)) {
            return url;
        }
        if (url.indexOf(PARAMETER_APPENDER) >= 0) {
            url = url + AND;
        } else {
            url = url + PARAMETER_APPENDER;
        }
        return url + param;
    }

    /**
     * 根据系统的相对地址和当前路径得到系统的真实地址
     *
     * @param url     目标路径
     * @param baseURL 当前地址
     * @return 完整地址
     */
    public static String wholeURL(String url, String baseURL) {
        try {
            if (ClassUtils.isClassExist("org.apache.http.client.utils.URIUtils")) {
                return URIUtils.resolve(new URI(baseURL), url.replaceAll(BLANK, URL_BLANK)).toString();
            } else {
                return buildURL(url, baseURL);
            }
        } catch (URISyntaxException e) {
            return buildURL(url, baseURL);
        }

    }

    /**
     * @param url
     * @param baseUrl
     * @return
     * @Title: buildURL
     * @date 2012-4-18 下午3:00:51
     * @author tonny
     * @version 1.0
     */
    private static String buildURL(String url, String baseUrl) {
        if (url.toLowerCase().startsWith(HTTP) || url.toLowerCase().startsWith(HTTPS)) {
            return url.replaceAll(BLANK, URL_BLANK);
        }
        if (url.startsWith(OBLIQUE_LINE)) {
            return (baseUrl.substring(0,
                    baseUrl.substring(baseUrl.indexOf(D_OBLIQUE_LINE) + 2).indexOf(OBLIQUE_LINE) + 7) + url)
                    .replaceAll(BLANK, URL_BLANK);
        }
        if (url.startsWith(CURRENT_DIR)) {
            return baseUrl.substring(0, baseUrl.lastIndexOf(OBLIQUE_LINE) + 1)
                    + url.substring(2).replaceAll(BLANK, URL_BLANK);
        }
        if (url.startsWith(PARENT_DIR)) {
            return getParentBaseURL(url, baseUrl).replaceAll(BLANK, URL_BLANK);
        }

        return (baseUrl.substring(0, baseUrl.lastIndexOf(OBLIQUE_LINE) + 1) + url).replaceAll(BLANK, URL_BLANK);
    }

    /**
     * 获得上级路径
     *
     * @param url     相对路径
     * @param baseURL 当前地址
     * @return 上级地址
     */
    private static String getParentBaseURL(String url, String baseURL) {
        if (!url.startsWith(PARENT_DIR)) {
            return baseURL.substring(0, baseURL.lastIndexOf(OBLIQUE_LINE) + 1) + url;
        }
        if (baseURL.lastIndexOf(OBLIQUE_LINE) > baseURL.substring(baseURL.indexOf(D_OBLIQUE_LINE) + 2).indexOf(
                OBLIQUE_LINE)
                + baseURL.indexOf(D_OBLIQUE_LINE) + 2) {
            return getParentBaseURL(
                    url.substring(3),
                    baseURL.substring(0,
                            baseURL.substring(0, baseURL.lastIndexOf(OBLIQUE_LINE)).lastIndexOf(OBLIQUE_LINE) + 1));
        } else {
            return getParentBaseURL(url.substring(3), baseURL);
        }
    }

    /**
     * 返回编码的url,不只是编码参数，还对地址进行编码
     *
     * @param url
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getURLEncoded(String url, String charset) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        String host = StringUtils.substringBefore(url, QUERY_SEPARATOR);
        host = StringUtils.substringBefore(host, ";");
        String uri = StringUtils.substringAfter(StringUtils.substringAfter(host, SCHEME_SEPARATOR), PATH_SEPARATOR);
        builder.append(StringUtils.substringBefore(host, SCHEME_SEPARATOR))
                .append(SCHEME_SEPARATOR)
                .append(StringUtils.substringBefore(StringUtils.substringAfter(host, SCHEME_SEPARATOR), PATH_SEPARATOR))
                .append(PATH_SEPARATOR);
        String[] split = StringUtils.split(uri, PATH_SEPARATOR);
        for (int i = 0; i < split.length - 1; i++) {
            for (int j = 0; j < split[i].length(); j++) {
                char c = split[i].charAt(j);

                if (c == '%' && split[i].substring(j, j + 3).matches("%[A-F0-9]{2}]")) {
                    builder.append(c);
                } else if (c < 300)
                    builder.append(URLEncoder.encode(String.valueOf(c), charset));
                else
                    builder.append(c);
            }
            builder.append(PATH_SEPARATOR);
        }
        if (split.length > 0) {
            for (int j = 0; j < split[split.length - 1].length(); j++) {
                char c = split[split.length - 1].charAt(j);
                if (c == '%' && split[split.length - 1].substring(j, j + 3).matches("%[A-F0-9]{2}]")) {
                    builder.append(c);
                } else if (c == 32) {
                    builder.append("%20");
                } else if (c < 300)
                    builder.append(URLEncoder.encode(String.valueOf(c), charset));
                else
                    builder.append(c);
            }
        }
        String queryString = StringUtils.substringAfter(url, QUERY_SEPARATOR);
        String[] querySplit = queryString.split(QUERY_CONCAT);
        if (url.indexOf(";") > 0)
            builder.append(";").append(StringUtils.substringBetween(url, ";", QUERY_SEPARATOR));
        if (querySplit.length > 0 && StringUtils.isNotEmpty(querySplit[0])) {
            builder.append(QUERY_SEPARATOR).append(StringUtils.substringBefore(querySplit[0], EQUALS));
            if (querySplit[0].contains(EQUALS)) {
                builder.append(EQUALS);
            }
            builder.append(URLEncoder.encode(StringUtils.substringAfter(querySplit[0], EQUALS), charset));
        }
        for (int i = 1; i < querySplit.length; i++) {
            builder.append(QUERY_CONCAT).append(StringUtils.substringBefore(querySplit[i], EQUALS));
            if (querySplit[i].contains(EQUALS)) {
                builder.append(EQUALS);
            }
            builder.append(URLEncoder.encode(StringUtils.substringAfter(querySplit[i], EQUALS), charset));
        }
        return builder.toString();
    }


    public static void main(String[] args) throws UnsupportedEncodingException {

    }
}
