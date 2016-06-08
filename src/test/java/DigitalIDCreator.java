import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

/**
 * 
 */

/**
 * 
 * @author 祥栋
 * @date 2013-1-5
 * @version 1.0.0
 */
public class DigitalIDCreator extends TestCase {
	private Set<String> testCreate(int start, int length) {
		Set<String> set = new HashSet<String>();
		StringBuilder builder = new StringBuilder();
		for (int i = start; i < start + length; i++) {
			String h = StringUtils.leftPad(String.valueOf(i), 8, '0');
			for (int j = 0; j < h.length(); j++) {
				builder.append(RandomUtils.nextInt(10));
				builder.append(h.charAt(j));
			}
			set.add(builder.toString());
			builder.delete(0, 16);
		}
		return set;
	}

	public void test() {
		Set<String> testCreate = testCreate(0, 100000);
		for (String string : testCreate) {
			System.out.println(string);
		}
		assertEquals(testCreate.size(), 100000);
	}
}
