package org.example.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentineled;

public class RedisConfig {
    private static JedisPool jedisPool;
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int TIMEOUT_MS = 2000;

    static {
        try {
            JedisPoolConfig poolConfig=new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);
            jedisPool=new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT, TIMEOUT_MS);
        } catch (Exception e) {
            System.err.println("Failed to initialize Redis connection pool: " + e.getMessage());
        }

    }

    public static Jedis getResources(){
        return jedisPool.getResource();
    }


    public  static void close(){
        if(jedisPool!=null){
            jedisPool.close();
        }
    }

}
