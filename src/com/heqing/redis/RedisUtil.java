package com.heqing.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisUtil {

    //Redis服务器IP
    private static String ADDR = "127.0.0.1";
    
    //Redis的端口号
    private static int PORT = 6379;
    
    //访问密码
    private static String AUTH = "heqing";
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    
    private static int TIMEOUT = 10000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    private static ShardedJedisPool shardedJedisPool = null;
    
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
//            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);   //有密码加入AUTH
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);
            
            List<JedisShardInfo> jdsInfoList =new ArrayList<JedisShardInfo>(1);
            JedisShardInfo infoA = new JedisShardInfo(ADDR, PORT);
//            infoA.setPassword(AUTH);    //有密码加入AUTH
            jdsInfoList.add(infoA);
            shardedJedisPool = new ShardedJedisPool(config, jdsInfoList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
            	jedisPool = new JedisPool(new JedisPoolConfig(), "127.0.0.1"); 
                return jedisPool.getResource();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public synchronized static ShardedJedis getShardedJedis() {
        try {
            if (shardedJedisPool != null) {
            	ShardedJedis shardedJedis = shardedJedisPool.getResource();
                return shardedJedis;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
    
    public static void returnResource(final ShardedJedis shardedJedis) {
        if (shardedJedis != null) {
        	shardedJedisPool.returnResource(shardedJedis);
        }
    }
}
