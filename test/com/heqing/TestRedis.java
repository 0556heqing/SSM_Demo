package com.heqing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heqing.redis.RedisMsgPubSubListener;
import com.heqing.redis.RedisUtil;
import com.heqing.redis.RedisXMLUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Transaction;

/**
 * 参考文档
 * http://blog.csdn.net/ithomer/article/details/9213185
 * http://www.runoob.com/redis/redis-tutorial.html
 * http://www.cnblogs.com/edisonfeng/p/3571870.html
 */
@RunWith(SpringJUnit4ClassRunner.class)		// 表示继承了 SpringJUnit4ClassRunner 类
@ContextConfiguration("classpath:redis-config.xml")
public class TestRedis {
	
	//redis-server.exe redis.windows.conf

    private Jedis jedis; // 获取数据库的连接,非切片客户端连接
    private ShardedJedis  shardedJedis; //切片客户端  (分布式)
    
    @Resource
	private RedisXMLUtil redisXMLUtil;
    
    @Before
    public void setup() {
        //连接redis服务器，127.0.0.1:6379
//        jedis = new Jedis("127.0.0.1", 6379);
        //权限认证
//      jedis.auth("heqing"); 
    	
//      jedis = RedisUtil.getJedis();
//      shardedJedis = RedisUtil.getShardedJedis();
    	
        jedis = redisXMLUtil.getConnection(); 
        shardedJedis = redisXMLUtil.getShardedConnection();  
    }
    
//    @Test
    public void testSharded() {
    	System.out.println("清空库中所有数据："+jedis.flushDB());
    	
    	//非切片客户端连接
    	System.out.println("新增name,heqing键值对："+jedis.set("name", "heqing")); 
    	System.out.println("非切片  根据 key 取值："+jedis.get("name")); 
    	
    	//切片(分布式)客户端连接
    	System.out.println("新增qq,975656343键值对："+shardedJedis.set("qq", "975656343")); 	
    	System.out.println("切片  根据 key 取值："+shardedJedis.get("qq")); 
    }
    
//	@Test
	public void testKey() {
		System.out.println("======================key=========================="); 
        // 清空数据 
        System.out.println("清空库中所有数据："+jedis.flushDB());
        // 判断key否存在 
        System.out.println("判断name键是否存在："+jedis.exists("name")); 
        System.out.println("新增name,heqing键值对："+jedis.set("name", "heqing")); 
        System.out.println("判断name键是否存在："+jedis.exists("name")); 
        // 输出系统中所有的key
        Set<String> keys = jedis.keys("*");
        Iterator<String> it=keys.iterator() ;   
        while(it.hasNext()) {
      	  String key = it.next();
      	  System.out.println(key +" 的类型:"+ jedis.type(key));   
        }  
        
        // 删除某个key,若key不存在，则忽略该命令。
        System.out.println("系统中删除name: "+jedis.del("name"));
        System.out.println("判断name是否存在："+jedis.exists("name"));

       // 设置 qq的过期时间
        System.out.println("判断qq是否存在："+jedis.set("qq", "975656343"));
        System.out.println("设置 qq的过期时间为3秒:"+jedis.expire("qq", 3));
        try{ 
            Thread.sleep(4000); 
        } 
        catch (InterruptedException e){ 
        } 
        System.out.println("判断qq是否存在："+jedis.exists("qq"));
        // 查看某个key的剩余生存时间,单位【秒】.永久生存或者不存在的都返回-1
        System.out.println("查看qq的剩余生存时间："+jedis.ttl("qq"));
        // 移除某个key的生存时间
        System.out.println("移除qq的生存时间："+jedis.persist("qq"));
	}
  
    /**
     * redis存储字符串
     */
//    @Test
    public void testString() {
    	System.out.println("清空库中所有数据："+jedis.flushDB());
    	
    	System.out.println("=============增=============");
    	jedis.set("name","heqing");
        jedis.set("age","26");
        Set<String> keys = jedis.keys("*"); 
        System.out.println("已新增的"+keys.size()+"个键值对如下：");
        System.out.println(jedis.get("name"));
        System.out.println(jedis.get("age"));
        
        System.out.println("=============改=============");
        /** 
         * set 直接覆盖原来的数据
         * append 在原来的数据后增加 
         * incr 进行加1操作,并返回增加后的值
         */ 
        System.out.println("直接覆盖name原来的数据："+jedis.set("name","heqing-update"));
        System.out.println("获取name对应的新值："+jedis.get("name"));
        System.out.println("在name原来值后面追加："+jedis.append("name","-append"));
        System.out.println("获取name对应的新值："+jedis.get("name")); 
        System.out.println("在age进行加1操作："+jedis.incr("age"));
        System.out.println("获取age对应的新值："+jedis.get("age"));
        
        System.out.println("=============删=============");  
        System.out.println("删除name键值对："+jedis.del("name"));  
        System.out.println("获取name键对应的值："+jedis.get("name"));
        
        System.out.println("=============增，删，查（多个）=============");
        /** 
         * mset,mget同时新增，修改，查询多个键值对 
         * 等价于：
         * jedis.set("name","ssss"); 
         * jedis.set("jarorwar","xxxx"); 
         */  
        System.out.println("一次性新增name,age及其对应值："+jedis.mset("name","heqing","age","26"));  
        System.out.println("一次性获取name,age各自对应的值："+jedis.mget("name","age"));
        System.out.println("一次性删除name,age："+jedis.del(new String[]{"name","age"}));
        System.out.println("一次性获取name,age各自对应的值："+jedis.mget("name","age")); 
    
        System.out.println("=============新增键值对时防止覆盖原先值=============");
        /**
         * jedis.setnx("name","***") 不存在便增加，存在不会修改原来的值
         * jedis.set("name","***")   不存在便增加，存在会修改原来的值
         */
        System.out.println("原先age不存在时，新增age："+jedis.setnx("age", "26"));
        System.out.println("当age存在时，尝试新增age："+jedis.setnx("age", "27"));
        System.out.println("获取age对应的值："+jedis.get("age"));
        
        System.out.println("=============获取原值，更新为新值一步完成=============");
        System.out.println("name原值："+jedis.getSet("name", "heqing-new"));
        System.out.println("name新值："+jedis.get("name"));
        
        System.out.println("=============获取子串=============");
        System.out.println("获取name对应值中的子串："+jedis.getrange("name", 3, 5)); 
        
        System.out.println("=============超过有效期键值对被删除=============");
        // 设置key的有效期，并存储数据 
        System.out.println("新增age，并指定过期时间为2秒"+jedis.setex("age", 2, "26")); 
        System.out.println("获取age对应的值："+jedis.get("age")); 
        try{ 
            Thread.sleep(3000); 
        } 
        catch (InterruptedException e){ 
        	e.printStackTrace();
        } 
        System.out.println("3秒之后，获取name对应的值："+jedis.get("age"));
    }
    
    /** 
     * jedis操作List 
     */  
//    @Test  
    public void testList(){  
    	// 清空数据 
        System.out.println("清空库中所有数据："+jedis.flushDB()); 
        System.out.println("=============增=============");
        /**
         * Lpush 命令将一个或多个值插入到列表头部(最左边)。
         *        如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 当 key 存在但不是列表类型时，返回一个错误
         * Rpush 命令用于将一个或多个值插入到列表的尾部(最右边)。 
         * lrange 是按范围取出第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有  
         **/
        jedis.lpush("java","1");  
        jedis.lpush("java","2");  
        jedis.lpush("java","3"); 
        System.out.println("所有元素-java："+jedis.lrange("java", 0, -1));
        jedis.rpush("j2ee","test1");  
        jedis.rpush("j2ee","test2");  
        jedis.rpush("j2ee","test3"); 
        System.out.println("所有元素-java："+jedis.lrange("j2ee", 0, -1));

        System.out.println("=============删=============");
        /**
         * lrem 根据参数 COUNT 的值，移除列表中与参数 VALUE 相等的元素 。
         *      count>0:从表头开始向表尾搜索，count<0:从表尾开始向表头搜索，count = 0:移除表中所有与 VALUE 相等的值
         * Ltrim 让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
         *       0 表示列表的第一个元素，以 1 表示列表的第二个元素1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素
         * lpop 命令删除，并返回保存列表在key的第一个元素  
         **/
        // 删除列表指定的值 ，第二个参数为删除的个数（有重复时），后add进去的值先被删，类似于出栈
        System.out.println("成功删除指定元素个数-java："+jedis.lrem("j2ee", 1, "test1")); 
        System.out.println("删除指定元素之后-java："+jedis.lrange("j2ee", 0, -1));
        // 删除区间以外的数据 
        System.out.println("删除下标0-1区间之外的元素："+jedis.ltrim("j2ee", 0, 1));
        System.out.println("删除指定区间之外元素后-java："+jedis.lrange("j2ee", 0, -1));
        // 列表元素出栈 
        System.out.println("出栈元素："+jedis.lpop("j2ee")); 
        System.out.println("元素出栈后-java："+jedis.lrange("j2ee", 0, -1));
        
        System.out.println("=============改=============");
        // 修改列表中指定下标的值 
        jedis.lset("j2ee", 0, "test0"); 
        System.out.println("下标为0的值修改后-j2ee："+jedis.lrange("j2ee", 0, -1));
        
        System.out.println("=============查=============");
        // 数组长度 
        System.out.println("长度-stringlists："+jedis.llen("java"));
        
        System.out.println("=============排序=============");
        /*
         * list中存字符串时必须指定参数为alpha，如果不使用SortingParams，而是直接使用sort("list")，
         * 会出现"ERR One or more scores can't be converted into double"
         * 
         * 排序之后返回元素的数量可以通过limit修饰符进行限制，修饰符接受offset和count两个参数。
         * 	 offset：指定要跳过的元素数量，即起始位置。count：指定跳过offset个指定的元素之后，要返回多少个对象。
         * sort命令默认排序对象为数字，当需要对字符串进行排序时，需要显式地在sort命令之后添加alpha修饰符。
         * ace返回键值从小到大排序的结果。      desc：返回键值从大到小排序的结果
         * lrange 子串：  start为元素下标，end也为元素下标；-1代表倒数一个元素，-2代表倒数第二个元素
         * lindex 获取列表指定下标的值 
         */
        System.out.println("Java原始排序："+jedis.lrange("java", 0, -1));
        SortingParams sortingParameters = new SortingParams();
        sortingParameters.alpha();
        sortingParameters.limit(1, -1);
        sortingParameters.desc();
        System.out.println("返回排序后的结果："+jedis.sort("java",sortingParameters)); 
        System.out.println("返回排序后的结果-java："+jedis.sort("java"));
        System.out.println("子串-第二个开始到结束："+jedis.lrange("java", 1, -1));
        System.out.println("获取下标为1的元素："+jedis.lindex("java", 1)+"\n");
    }  
    
    /** 
     * jedis操作Set 
     */  
//    @Test  
    public void testSet(){  
    	System.out.println("======================set=========================="); 
        // 清空数据 
        System.out.println("清空库中所有数据："+jedis.flushDB());
        
        System.out.println("=============增=============");
        System.out.println("向user集合中加入元素name："+jedis.sadd("user", "name")); 
        System.out.println("向user集合中加入元素age："+jedis.sadd("user", "age"));
        System.out.println("向user集合中加入元素age："+jedis.sadd("user", "qq"));
        System.out.println("查看user集合中的所有元素:"+jedis.smembers("user")); 
    	
        System.out.println("=============删=============");
        System.out.println("集合user中删除元素qq："+jedis.srem("user", "qq"));
        System.out.println("查看user集合中的所有元素:"+jedis.smembers("user"));
        
        System.out.println("=============查=============");
        System.out.println("判断name是否在集合user中："+jedis.sismember("user", "name"));
        System.out.println("循环查询获取sets中的每个元素：");
        Set<String> set = jedis.smembers("user");   
        Iterator<String> it=set.iterator() ;   
        while(it.hasNext()){   
            Object obj=it.next();   
            System.out.println(obj);   
        }
        
        System.out.println("=============其他=============");
        System.out.println(jedis.sismember("user", "who"));//判断 who 是否是user集合的元素  
        System.out.println(jedis.srandmember("user"));    //随机返回key集合中的一个元素.
        System.out.println(jedis.scard("user"));//返回集合的元素个数  
        
        System.out.println("=============集合运算=============");
        System.out.println("user1中添加元素name："+jedis.sadd("user1", "name")); 
        System.out.println("user1中添加元素age："+jedis.sadd("user1", "age")); 
        System.out.println("user1中添加元素qq："+jedis.sadd("user1", "qq")); 
        System.out.println("user2中添加元素name："+jedis.sadd("user2", "name")); 
        System.out.println("user2中添加元素age："+jedis.sadd("user2", "age")); 
        System.out.println("user2中添加元素e_nail："+jedis.sadd("user2", "e_nail"));
        System.out.println("查看user1集合中的所有元素:"+jedis.smembers("user1"));
        System.out.println("查看user2集合中的所有元素:"+jedis.smembers("user2"));
        System.out.println("user1和user2交集："+jedis.sinter("user1", "user2"));
        System.out.println("user1和user2并集："+jedis.sunion("user1", "user2"));
        System.out.println("user1和user2差集："+jedis.sdiff("user1", "user2"));
    }  
    
    /** 
     * jedis操作SortedSet   有序集合
     */  
//    @Test  
    public void testSortedSet(){ 
    	System.out.println("======================zset=========================="); 
        // 清空数据 
    	System.out.println("清空库中所有数据："+jedis.flushDB());
        
        System.out.println("=============增=============");
        System.out.println("user中添加元素name："+jedis.zadd("user", 2.0, "name")); 
        System.out.println("user中添加元素age："+jedis.zadd("user", 5.0, "age")); 
        System.out.println("user中添加元素qq："+jedis.zadd("user", 8.0, "qq")); 
        System.out.println("user集合中的所有元素："+jedis.zrange("user", 0, -1));//按照权重值排序  由小到大
        
        System.out.println("=============查=============");
        System.out.println("统计zset集合中的元素中个数："+jedis.zcard("user"));
        System.out.println("统计zset集合中权重某个范围内（1.0——5.0），元素的个数："+jedis.zcount("user", 1.0, 5.0));
        System.out.println("查看zset集合中user的权重："+jedis.zscore("user", "qq"));
        System.out.println("查看下标1到2范围内的元素值："+jedis.zrange("user", 1, 2));
        
        System.out.println("=============删=============");
        System.out.println("user中删除元素age："+jedis.zrem("user", "age"));
        System.out.println("user集合中的所有元素："+jedis.zrange("user", 0, -1));
    }
    
    /**
     * redis操作Map
     */
//    @Test
    public void testMap() {
    	System.out.println("======================map=========================="); 
        // 清空数据 
    	System.out.println("清空库中所有数据："+jedis.flushDB());
    	
        System.out.println("=============增============="); 
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "heqing");
        map.put("age", "26");
        map.put("qq", "975656343");
        jedis.hmset("user",map);
        Iterator<String> iter=jedis.hkeys("user").iterator();  
        while (iter.hasNext()){  
            String key = iter.next();  
            System.out.println("user集合中的键："+key+"，值:"+jedis.hmget("user",key));
        } 
        
        System.out.println("=============查=============");
        System.out.println("user集合中所有的键："+jedis.hkeys("user"));//返回map对象中的所有key  
        System.out.println("user集合中所有的值："+jedis.hvals("user"));//返回map对象中的所有value
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List  
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数  
        List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
        System.out.println("user集合中name，age，qq："+rsmap);  
  
        System.out.println("=============删=============");  
        System.out.println("删除前的值为："+jedis.hmget("user", "age"));
        jedis.hdel("user","age");
        System.out.println("删除后的值为："+jedis.hmget("user", "age")); //因为删除了，所以返回的是null  
        System.out.println("user中键的个数为："+jedis.hlen("user")); //返回key为user的键中存放的值的个数2 
        System.out.println("是否存在key为user："+jedis.exists("user"));//是否存在key为user的记录 返回true  
    }
    
//    @Test
    public void HashOperate() {
    	System.out.println("======================hash==========================");
        //清空数据 
        System.out.println("清空库中所有数据："+jedis.flushDB()); 
        
        System.out.println("=============增=============");
        System.out.println("user中添加name和heqing键值对："+jedis.hset("user", "name", "heqing")); 
        System.out.println("user中添加qq和975656343键值对："+jedis.hset("user", "qq", "975656343"));
        System.out.println("新增age和26的整型键值对："+jedis.hincrBy("user", "age", 26));
        System.out.println("user中的所有值："+jedis.hvals("user"));

        System.out.println("=============删=============");
        System.out.println("user中删除qq键值对："+jedis.hdel("user", "qq"));
        System.out.println("user中的所有值："+jedis.hvals("user"));
        
        System.out.println("=============改=============");
        System.out.println("age整型键值的值增加100："+jedis.hincrBy("user", "age", 100l));
        System.out.println("user中的所有值："+jedis.hvals("user"));
        
        System.out.println("=============查=============");
        System.out.println("判断name是否存在："+jedis.hexists("user", "name"));
        System.out.println("获取name对应的值："+jedis.hget("user", "name"));
        System.out.println("批量获取age和qq对应的值："+jedis.hmget("user", "age", "qq")); 
        System.out.println("获取user中所有的key："+jedis.hkeys("user"));
        System.out.println("获取user中所有的value："+jedis.hvals("user"));
    }
    
//    @Test
    public void testRedisPool() {
        RedisUtil.getJedis().set("test", "中文测试");
        System.out.println(RedisUtil.getJedis().get("test"));
    }
    
//  @Test  
  public void testSubscribe() throws Exception{  
      RedisMsgPubSubListener listener = new RedisMsgPubSubListener();   
	  //可以订阅多个频道  
	  //订阅得到信息在lister的onMessage(...)方法中进行处理  
      jedis.subscribe(listener, "redisChatTest");   
      jedis.subscribe(listener, "test1", "test2");  
  	  //也用数组的方式设置多个频道  
      jedis.subscribe(listener, new String[]{"hello_foo","hello_test"}); 
      
	  //这里启动了订阅监听，线程将在这里被阻塞  
	  //订阅得到信息在lister的onPMessage(...)方法中进行处理  
      jedis.psubscribe(listener, new String[]{"hello_*"});//使用模式匹配的方式设置频道
      jedis.publish("redisChatTest", "heqing"); 
  }  
    
    /** 
     * 事物主要目的是保障，一个client发起的事务中的命令可以连续的执行，而中间不会插入其他client的命令。
     * jedis事物 以 MULTI 开始一个事务， 然后将多个命令入队到事务中， 最后由 EXEC 命令触发事务
     */
//    @Test
    public void testTrans() {
        long start = System.currentTimeMillis();
        Transaction tx = jedis.multi();
        for (int i = 0; i < 100000; i++) {
//            tx.set("t" + i, "t" + i);
            tx.del("t" + i);
        }
        List<Object> results = tx.exec();
        for (Object rt : results) System. out.println(rt.toString());

        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start)/1000.0) + " s");
        jedis.disconnect();
    }
    
    /** 
     * 管道采用异步方式，一次发送多个指令，不同步等待其返回结果。提高执行效率
     */
//    @Test
    public void testPipelined() {
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
//            pipeline.set("p" + i, "p" + i);
        	pipeline.del("p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " s");
        jedis.disconnect();
    }
    
    /** 
     * 管道中使用事务
     * 经测试运行时间：Pipelined < Pipelined transaction < Transaction
     */
//    @Test
    public void testCombPipelineTrans() {
        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        for (int i = 0; i < 100000; i++) {
//            pipeline.set("" + i, "" + i);
            pipeline.del("" + i);
        }
        pipeline.exec();
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined transaction: " + ((end - start)/1000.0) + " s");
        jedis.disconnect();
    }
    
    /** 
     * 分布式直接连接，并且是同步调用，每步执行都返回执行结果
     */
//    @Test
    public void testShardNormal() {

        ShardedJedis sharding = redisXMLUtil.getShardedConnection();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
//            String result = sharding.set("sn" + i, "n" + i);
            sharding.del("sn" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple@Sharing SET: " + ((end - start)/1000.0) + " seconds");

        sharding.disconnect();
    }
    
    /** 
     * 分布式直接连接，并且是异步调用，每步执行都返回执行结果
     */
//    @Test
    public void testShardpipelined() {
    	
        ShardedJedis sharding = redisXMLUtil.getShardedConnection();

        ShardedJedisPipeline pipeline = sharding.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sp" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined@Sharing SET: " + ((end - start)/1000.0) + " seconds");

        sharding.disconnect();
    }
    
    /** 
     * 分布式调用代码是运行在线程中，那么上面两个直连调用方式就不合适了，因为直连方式是非线程安全的.必须选择连接池调用
     * 同步调用
     */
//    @Test
    public void testShardSimplePool() {
    	ShardedJedisPool pool = redisXMLUtil.shardedJedisPool;
        ShardedJedis one = pool.getResource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
//            String result = one.set("spn" + i, "n" + i);
            one.del("spn" + i);
        }
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Simple@Pool SET: " + ((end - start)/1000.0) + " seconds");

        pool.destroy();
    }
    
    /** 
     * 分布式连接池异步调用
     */
//    @Test
    public void testShardPipelinedPool() {

        ShardedJedisPool pool = redisXMLUtil.shardedJedisPool;
        ShardedJedis one = pool.getResource();
        ShardedJedisPipeline pipeline = one.pipelined();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sppn" + i, "n" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Pipelined@Pool SET: " + ((end - start)/1000.0) + " seconds");
        pool.destroy();
    }
    
}
