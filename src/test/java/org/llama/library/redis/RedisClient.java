package org.llama.library.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

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

}
