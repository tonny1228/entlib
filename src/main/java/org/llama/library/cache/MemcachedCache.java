/**
 * 
 */
package org.llama.library.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author tonny
 * @date 2015-1-15
 * @version 1.0.0
 */
public class MemcachedCache implements Cache {
	private static final int FOREVER = 2592000;

	private Log log = LogFactory.getLog(getClass());
	/**
	 * 用户会话对象缓存池
	 */
	protected MemcachedClient memcachedClient;

	private String name;

	public MemcachedCache(String name, String address) {
		this.name = name;
		final List<InetSocketAddress> addresses = new ArrayList<InetSocketAddress>();
		for (final String hostname : address.split(",")) {
			String[] hostPort = hostname.split(":");
			addresses.add(new InetSocketAddress(hostPort[0], Integer.parseInt(hostPort[1])));
		}
		try {
			memcachedClient = new MemcachedClient(addresses);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#putInCache(java.lang.String,
	 * java.lang.Object)
	 */
	public void putInCache(String key, Object value) {
		memcachedClient.set(key, FOREVER, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#putInCache(java.lang.String,
	 * java.lang.Object, int)
	 */
	public void putInCache(String key, Object value, int seconds) {
		memcachedClient.set(key, seconds, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#getFromCache(java.lang.String)
	 */
	public <T> T getFromCache(String key) {
		try {
			return (T) memcachedClient.get(key);
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#getFromCache(java.lang.String, int)
	 */
	public <T> T getFromCache(String key, int seconds) {
		return (T) memcachedClient.getAndTouch(key, seconds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#getFromCache(java.lang.String,
	 * org.llama.library.cache.Failover)
	 */
	public <T> T getFromCache(String key, Failover failover) {
		T object = getFromCache(key);
		if (object == null) {
			object = (T) failover.getObject(key);
			putInCache(key, object);
		}
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#remove(java.lang.String)
	 */
	public void remove(String key) {
		memcachedClient.delete(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#removeAll()
	 */
	public void removeAll() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#getName()
	 */
	public String getName() {
		return name;
	}

}
