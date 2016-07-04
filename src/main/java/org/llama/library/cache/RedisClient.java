package org.llama.library.cache;

import redis.clients.jedis.Jedis;

/**
 * redis的缓存客户端
 * Created by Tonny on 2016/6/12.
 */
public class RedisClient implements Cache {

    private Jedis jedis;

    public void putInCache(String key, Object value) {
        if (value instanceof String)
            set(key, (String) value);
    }

    private String set(String key, String value) {
        return jedis.set(key, value);
    }


    public void putInCache(String key, Object value, int seconds) {

    }

    public <T> T getFromCache(String key) {
        return null;
    }

    public <T> T getFromCache(String key, int seconds) {
        return null;
    }

    public <T> T getFromCache(String key, Failover failover) {
        return null;
    }

    public void remove(String key) {

    }

    public void removeAll() {

    }

    public String getName() {
        return null;
    }
}