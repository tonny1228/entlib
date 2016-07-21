package org.llama.library.cache;

import org.apache.commons.lang.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.io.Serializable;

/**
 * redis的缓存客户端
 * Created by Tonny on 2016/6/12.
 */
public class RedisClient implements Cache {

    private Jedis jedis;

    public void putInCache(String key, Object value) {
        jedis.set(key.getBytes(), SerializationUtils.serialize((Serializable) value));
    }

    public void putInCache(String key, Object value, int seconds) {
        jedis.set(key.getBytes(), SerializationUtils.serialize((Serializable) value));
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