package com.chinaoly.frm.login.util;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** 
* @author : shul
* @date：2018年6月1日 下午3:53:02  
*/
public class RedisUtil {
	protected Logger log = LoggerFactory.getLogger(getClass().getName());
	private JedisPool pool = null;
	private Jedis redis = null;
	
	//构造函数,创建对象时进行初始化 
	public RedisUtil(){
		/*if(pool == null){ 
            // 池基本配置 
            JedisPoolConfig config = new JedisPoolConfig(); 
            // 最大连接数, 默认8个 
            config.setMaxTotal(20); 
            // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。 
            config.setMaxIdle(5); 
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException； 
            config.setMaxWaitMillis(10000); 
            // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的； 
            config.setTestOnBorrow(false);              
            //创建redis连接池  
            this.pool = new JedisPool(config,"",6379);
            //获取Jedis实例  
            this.redis = pool.getResource();
            log.info("Connection to redis server sucessfully");           
		}*/
		//获取Jedis实例 
		 this.redis = new Jedis("192.168.1.146",6379);
		 log.info("Connection to redis server sucessfully"); 
	} 
	
	/** 
     * 关闭连接 
     */ 
	public void quitConnection(Jedis redis){
		if(redis != null){
			redis.quit();
		}
	}
	
	/** 
     * 获取key对应的value 
     *  
     * 说明：方法中目前只针对key数据类型为String的情况作了处理，其他数据类型可根据需要进行扩展即可 
     * @param key 
     * @return 
     */ 
	public String getValue(String key){
		String value = null;
		try {
			if(redis == null || !redis.exists(key)){
				log.info("key:"+key+"is not found");
				quitConnection(redis);
				return value;
			}
			//获取key对应的数据类型
			String type = redis.type(key);
			log.info("key:" + key + " 的类型为：" + type); 
			if(type.equals("string")){
				//get(key)方法返回key所关联的字符串值 
				value = redis.get(key);
			}
			/*if(type.equals("hash")){
				List<String> list = redis.hvals(key);//hvals(key)返回哈希表 key中所有域的值
				Iterator<String> it=list.iterator(); 
				while(it.hasNext()){
					value = it.next();
				}
			}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭连接  
            quitConnection(redis); 
		}
		return value;
	}

}
