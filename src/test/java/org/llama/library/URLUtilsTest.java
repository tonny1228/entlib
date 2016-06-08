package org.llama.library;

import junit.framework.TestCase;
import org.llama.library.utils.URLUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by tonny on 2015/10/26.
 */
public class URLUtilsTest extends TestCase {

    public void test() throws UnsupportedEncodingException {
        System.out.println(URLUtils.getURLEncoded("http://192.168.1.231:8081/userpro/download.php?url=L2ZpbGUvNy82MjQzMS8xODcyOTU0OS%40%604%5E1%40Qwr2oIE1pY3Jvc29mdCBXb3JkIM7EtbUuZG9j", "utf-8"));

    }
}
