/**
 * 
 */
package org.llama.library;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.URLUtils;

import junit.framework.TestCase;

/**
 * 
 * @author tonny
 * @date 2015年5月15日
 * @version 1.0.0
 */
public class URLTest extends TestCase {

	public void test() {
		String url = "http://re.jd.com/cps/item/1218053.html?cu=true&utm_source=www.smzdm.com&utm_medium=tuiguang&utm_campaign=t_274461020_smzdm850&utm_term=92c2fbffbfa148c487b42f63701ca333";
		// String url = "http://re.jd.com/cps/item/1218053.html";
		// String url = "http://re.jd.com/cps/item/";/

		Pattern pattern = Pattern.compile("\\w+://(([^/]*/)+[^\\?]*)\\??(([^=]+=[^&]*)?(&[^=]+=[^&]*)*)");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			int c = matcher.groupCount();
			for (int i = 0; i < c; i++) {
				System.out.println(matcher.group(i));
			}
		}

	}

	public void test2() throws UnsupportedEncodingException {
		for (int i = 0; i < 300; i++) {
			char c = (char) i;
			System.out.println(i + ":" + c + ":" + URLEncoder.encode(String.valueOf(c)));
		}
		System.out.println("abc".substring(0, 1));
		String url = "http://re.jd.com/cps/item/1218053.html?cu=true&&utm_source=www.smzdm.com&hello&utm_medium=tuiguang&test=&utm_campaign=我当然是中文的了&utm_term=92c2fbffbfa148c487b42f63701ca333";
		url(url);
		url("http://192.168.0.242:9000/apps/admin_main.jsp;jessionid=sdfsdfsdfsdfsdfsdfsdfsd?username=1");
		url("http://192.168.0.242:9000/apps/魏书中文、 的腧分腧.docx;jessionid=sdfsdfsdfsdfsdfsdfsdfsd?username=1");
		url("http://192.168.0.242:9000/apps/admin_main.jsp");
		url("http://192.168.0.242:9000/apps/");
		url("http://192.168.0.242:9000/");
		url("http://218.61.30.150:8208//file/547/0/299/盘锦市县区、 直属单位名录2014.2.24.doc");
	}

	private void url(String url) throws UnsupportedEncodingException {

		System.out.println(URLUtils.getURLEncoded(url, "utf-8"));
		if (true) {
			return;
		}
		StringBuilder builder = new StringBuilder();
		String host = StringUtils.substringBefore(url, "?");
		host = StringUtils.substringBefore(host, ";");
		String uri = StringUtils.substringAfter(StringUtils.substringAfter(host, "://"), "/");
		builder.append(StringUtils.substringBefore(host, "://")).append("://")
				.append(StringUtils.substringBefore(StringUtils.substringAfter(host, "://"), "/")).append("/");
		String[] split = StringUtils.split(uri, "/");
		for (int i = 0; i < split.length - 1; i++) {
			builder.append(URLEncoder.encode(split[i], "utf-8")).append("/");
		}
		if (split.length > 0)
			builder.append(URLEncoder.encode(split[split.length - 1], "utf-8"));
		String queryString = StringUtils.substringAfter(url, "?");
		String[] querySplit = queryString.split("&");
		if (url.indexOf(";") > 0)
			builder.append(";").append(StringUtils.substringBetween(url, ";", "?"));
		if (querySplit.length > 0 && StringUtils.isNotEmpty(querySplit[0])) {
			builder.append("?").append(StringUtils.substringBefore(querySplit[0], "="));
			if (querySplit[0].contains("=")) {
				builder.append("=");
			}
			builder.append(URLEncoder.encode(StringUtils.substringAfter(querySplit[0], "="), "utf-8"));
		}
		for (int i = 1; i < querySplit.length; i++) {
			builder.append("&").append(StringUtils.substringBefore(querySplit[i], "="));
			if (querySplit[i].contains("=")) {
				builder.append("=");
			}
			builder.append(URLEncoder.encode(StringUtils.substringAfter(querySplit[i], "="), "utf-8"));
		}
	}
}
