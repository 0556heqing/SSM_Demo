package com.heqing.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisXMLUtil {

	// 非切片连接池       
    public JedisPool jedisPool;  
    // 切片连接池   分布式
    public ShardedJedisPool shardedJedisPool; 
      
    /**       
     * 获取数据库连接        
     * @return conn        
     */       
    public Jedis getConnection() {  
        Jedis jedis=null;            
        try {                
            jedis=jedisPool.getResource();            
        } catch (Exception e) {                
            e.printStackTrace();            
        }            
        return jedis;        
    }     
    
    public ShardedJedis getShardedConnection() {  
    	ShardedJedis shardedJedis=null;            
        try {                
        	shardedJedis = shardedJedisPool.getResource();            
        } catch (Exception e) {                
            e.printStackTrace();            
        }            
        return shardedJedis;        
    }
      
    /**        
     * 关闭数据库连接        
     * @param conn        
     */       
    public void closeConnection(Jedis jedis) {            
        if (null != jedis) {                
            try {                    
                jedisPool.returnResource(jedis);                
            } catch (Exception e) {  
                    e.printStackTrace();                
            }            
        }        
    } 
    
    public void closeShardedConnection(ShardedJedis shardedJedis) {            
        if (null != shardedJedis) {                
            try {                    
            	shardedJedisPool.returnResource(shardedJedis);                
            } catch (Exception e) {  
                    e.printStackTrace();                
            }            
        }        
    }
      
    /**        
     * 设置连接池        
     * @param 数据源       
     */       
    public void setJedisPool(JedisPool JedisPool) {  
        this.jedisPool = JedisPool;        
    }    
    
    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {  
        this.shardedJedisPool = shardedJedisPool;        
    } 
      
    /**        
     * 获取连接池        
     * @return 数据源        
     */       
    public JedisPool getJedisPool() {  
        return jedisPool;        
    } 
      
    public ShardedJedisPool getShardedJedisPool() {  
        return shardedJedisPool;        
    } 
}
