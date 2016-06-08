/**  
 * http request 工具，用于在其他无法获取httprequest的类中获取当前请求的信息。
 * 只能用于与request同一线程的类中获取，新启动的线程无法获得 需要在web.xml配置要过滤的地址,才能使用
 * @Title: HttpRequestUtilsFilter.java
 * @Package works.tonny.library.web
 * @author Tonny
 * @date 2012-4-18 下午4:30:51
 */
package org.llama.library.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.llama.library.utils.HttpRequestUtils;


/**
 * @ClassName: HttpRequestUtilsFilter
 * @Description:
 * @author Tonny
 * @date 2012-4-18 下午4:30:51
 * @version 1.0
 */
public class HttpRequestUtilsFilter implements Filter {

	/**
	 * 启动过滤器，请求前先绑定请求，请求后解除
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpRequestUtils.REQUESTS.set((HttpServletRequest) request);
		chain.doFilter(request, response);
		HttpRequestUtils.REQUESTS.remove();
	}

	/**
	 * @param filterConfig
	 * @throws ServletException
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/**
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		HttpRequestUtils.REQUESTS.remove();
	}
}
