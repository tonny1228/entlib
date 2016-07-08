package org.llama.library.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Tonny on 2016/6/8.
 */
public class RedisClient {
    private Jedis jedis;//非切片额客户端连接
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedis shardedJedis;//切片额客户端连接
    private ShardedJedisPool shardedJedisPool;//切片连接池

    public RedisClient() {
        initialPool();
        jedis = jedisPool.getResource();


    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000l);
        config.setTestOnBorrow(true);

        jedisPool = new JedisPool(config, "192.168.0.242", 6379);
    }


    @Test
    public void show() {
        jedis.set("name", "tonny");
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key + ":");
            System.out.println(jedis.ttl(key));
            System.out.println(jedis.persist(key));
            System.out.println(jedis.ttl(key));
            System.out.println(jedis.type(key));
        }

    }


    @Test
    public void testMap() {
        Map map = new HashMap();
        map.put("name", "xinxin");
        map.put("age", 1);
        map.put("qq", "123456");
        jedis.hmset("user", map);
        List<String> rsmap = jedis.hmget("user", "name");
        System.out.println(rsmap);
        System.out.println(jedis.hkeys("user"));
    }


    @Test
    public void testList() {
        jedis.lpush("java framework","spring");
        jedis.lpush("java framework","struts");
        jedis.rpush("java framework","hibernate");
        System.out.println(jedis.lrange("java framework",0,-1));
    }
}
