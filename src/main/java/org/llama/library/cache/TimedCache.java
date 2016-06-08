/**
 * 
 */
package org.llama.library.cache;

import java.util.Date;

/**
 * @author 祥栋
 * @date 2013-4-24
 * @version 1.0.0
 */
public class TimedCache {
	private Date date = new Date();
	private int cacheSeconds;
	private Object object;

	/**
	 * @param object
	 * @param cacheSeconds
	 */
	public TimedCache(Object object, int cacheSeconds) {
		super();
		this.object = object;
		this.cacheSeconds = cacheSeconds;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the cacheSeconds
	 */
	public int getCacheSeconds() {
		return cacheSeconds;
	}

	/**
	 * @return the object
	 */
	public Object getObject() {
		return object;
	}

	
}
