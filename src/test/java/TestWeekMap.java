import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.llama.library.utils.RegularUtils;

import java.util.*;

/**
 *
 */

/**
 * @author 祥栋
 * @version 1.0.0
 * @date 2013-1-10
 */
public class TestWeekMap extends TestCase {

    public void test() throws InterruptedException {
        System.out.println(Arrays.asList(StringUtils.splitByWholeSeparator("a>?df", "?")));
        System.out.println(Arrays.asList(RegularUtils.matchedString("sdfsd s>(:hsdf) and df=: sdfsdf sdf sd", ":\\s*(\\w+)")));
        List<Map> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String, Integer> map = new WeakHashMap<String, Integer>();
            map.put(new String("1"), new Integer(1));
            list.add(map);

            if (i % 100 == 0) {
                //a = null;
                System.gc();
            }
            System.out.print(i + ": ");
            int t = 0;
            for (int j = 0; j < list.size(); j++) {
                t += list.get(0).size();
            }
            System.out.println(t);
            Thread.sleep(100);

        }

    }

    public void test2() throws InterruptedException {
        Map<String, Integer> map = new WeakHashMap<String, Integer>();
        map.put(new String("1"), new Integer(1));

        for (int i = 0; i < 10; i++) {
            if (i % 100 == 99) {
                //a = null;
                System.gc();
            }
            System.out.print(i + ": ");
            System.out.println(map);
            Thread.sleep(1000);
        }
    }


    public void test3() throws InterruptedException {
        Map<String, List> map = new WeakHashMap<String, List>();
        //主要是是这个new string才导致key不被引用，才生效
        map.put(new String("q"), new ArrayList());
        for (int i = 0; i < 10; i++) {
            if (i % 100 == 99) {
                //a = null;
                System.gc();
            }
            System.out.print(i + ": ");
            System.out.println(map);
            Thread.sleep(100);
        }
    }

}
