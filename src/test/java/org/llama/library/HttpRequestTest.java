/**
 * 
 */
package org.llama.library;

import org.junit.Assert;
import org.junit.Test;
import org.llama.library.utils.HttpRequestUtils;

/**
 * 
 * @author tonny
 * @date 2015年6月25日
 * @version 1.0.0
 */
public class HttpRequestTest {

	@Test
	public void test() {
		System.out.println(HttpRequestUtils.getUserAgent(
				"Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0").getOs());
		Assert.assertTrue(HttpRequestUtils
				.getUserAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0").getOs()
				.equals("Windows NT 6.3"));
	}

}
