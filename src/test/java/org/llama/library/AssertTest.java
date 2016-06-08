/**  
 * @Title: AssertTest.java
 * @Package works.tonny.library
 * @author Tonny
 * @date 2012-4-19 上午8:47:06
 */
package org.llama.library;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.llama.library.EnterpriseApplication;
import org.llama.library.utils.ELHelper;
import org.llama.library.validate.TestBean;

import junit.framework.TestCase;


/**
 * @ClassName: AssertTest
 * @Description:
 * @author Tonny
 * @date 2012-4-19 上午8:47:06
 * @version 1.0
 */
public class AssertTest extends TestCase {

	private String hello = "s";

	public void h() {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			System.out.println(field.getModifiers());
			System.out.println();
		}
	}

	public void testAssert() {
//		EnterpriseApplication.getComponent("");
//		// Assert.isTrue(false);
//		// Assert.isTrue(false, "must be true");
//		// Assert.isTrue(false, "notnull");
//
//		class hello extends AssertTest {
//
//			private String tt = "s";
//
//			public void testAssert() {
//				h();
//			}
//		}
//
//		hello h = new hello();
//		h.testAssert();
	}

	public void testEL() {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("name", "Tonny");
		context.put("full name", "${name} Liu");
		context.put("test", new TestBean("${name} Liu", 20));
		assertEquals(ELHelper.execute("hello", context), "hello");
		assertEquals(ELHelper.execute("hello ${name}", context), "hello Tonny");
		assertEquals(ELHelper.execute("hello ${full name}", context), "hello Tonny Liu");
		assertEquals(ELHelper.execute("hello ${test.name}", context), "hello Tonny Liu");
		assertEquals(ELHelper.execute("hello ${test.name} ${test.age}", context), "hello Tonny Liu 20");
		assertEquals(ELHelper.execute("hello ${test.map.child.name}", context), "hello my child");
		assertEquals(ELHelper.execute("hello ${test.child.name}", context), "hello my child");
	}
}
