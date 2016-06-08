/**
 * 
 */
package org.llama.library;

import java.util.TimerTask;

import org.apache.commons.lang.NullArgumentException;
import org.llama.library.utils.TimerUtils;

import junit.framework.TestCase;

/**
 * 
 * @author tonny
 * @date 2015-1-4
 * @version 1.0.0
 */
public class TimerTest extends TestCase {

	public void testTimerUtils() throws InterruptedException {
		TimerUtils.schedule(new TimerTask() {
			@Override
			public void run() {
				throw new NullArgumentException("");
			}
		}, 100);
		Thread.sleep(1000);
		TimerUtils.schedule(new TimerTask() {
			@Override
			public void run() {
				throw new NullArgumentException("");
			}
		}, 100);
		Thread.sleep(2000);
	}
}
