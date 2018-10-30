package com.zobgo.common.crawler.utils;

/**
 * Created by zongbo.zhang on 10/25/18.
 */

import com.zobgo.common.crawler.config.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池
 */
public class RedisPool {
    private static JedisPool jedisPool;
    private static Logger logger = LoggerFactory.getLogger(RedisPool.class);
    static {
        initPool();
    }

    /**
     * 初始化线程池
     */
    private static void initPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(Config.getInt("redis_max_idle", 50));
        jedisPoolConfig.setMaxWaitMillis(Config.getInt("redis_max_wait_mills",2000));
        jedisPoolConfig.setMaxTotal(Config.getInt("redis_max_total",500));
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        String host = Config.getString("redis_host");
        int port = Config.getInt("redis_port", 6379);
        int timeout = Config.getInt("redis_timeout", 2000);
        String password = Config.getString("redis_password");
        // 构造连接池
        jedisPool = password == null || password.equals("") ?
                new JedisPool(jedisPoolConfig, host, port, timeout):new JedisPool(jedisPoolConfig, host, port, timeout,password);
    }

    //获取连接
    public static Jedis getConnection() throws SQLException {
        return jedisPool.getResource();
    }

    //回到线程池
    public static void returnResource(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    //释放资源
    public static void returnBrokenResource(Jedis jedis) {
        jedisPool.returnBrokenResource(jedis);
    }

    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getConnection();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            logger.error("redis get Exception", e);
        } finally {
            //返还到连接池
            jedisPool.returnResource(jedis);
        }

        return value;
    }

    public static long del(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            return jedis.del(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            logger.error("redis del Exception", e);
        } finally {
            //返还到连接池
            jedisPool.returnResource(jedis);
        }

        return 0;
    }

    public static String setex(String key, int seconds, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getConnection();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            logger.error("redis setex Exception", e);
        } finally {
            //返还到连接池
            jedisPool.returnResource(jedis);
        }

        return result;
    }

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getConnection();
            result = jedis.set(key, value);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            logger.error("redis setex Exception", e);
        } finally {
            //返还到连接池
            jedisPool.returnResource(jedis);
        }

        return result;
    }

    public static long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            return jedis.incr(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            logger.error("redis setex Exception", e);
        } finally {
            //返还到连接池
            jedisPool.returnResource(jedis);
        }

        return -1;
    }

    public static long ttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getConnection();
            return jedis.ttl(key);
        } catch (Exception e) {
            //释放redis对象
            jedisPool.returnBrokenResource(jedis);
            logger.error("redis setex Exception", e);
        } finally {
            //返还到连接池
            jedisPool.returnResource(jedis);
        }

        return -1;
    }
}
