package com.chinaoly.frm.core.baseDao.redis;

import com.chinaoly.frm.core.common.DateJsonValueProcessor;
import com.google.common.collect.Lists;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.data.redis.support.collections.RedisZSet;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
/**
 * @author Zhaohmz
 *
 */
@Component
public class RedisKeyValuesDao extends BaseRedisDao {

	public static final String DATEFORMAT = "yyyy-MM-dd hh:mm:ss";
	
	//模糊查询所有key对应的string类型值
	public HashMap<String,String> getKeyValuesForString(String regexp) {
		//模糊查询，获取所有的keys
		Set<String> sets = fuzzyGetKeys(regexp);
		HashMap<String,String> result = new HashMap<String,String>();
		if(sets.isEmpty()) return result;

		//根据keys批量获得值
		List<String> stringValues = valueOps().multiGet(sets);
		List<String> keys = Lists.newArrayList(sets);

		int size = keys.size();
		for(int i=0;i<size;i++){
			String rkey = keys.get(i);
			String value = stringValues.get(i);
			if (value != null && value.length()>1000) {
				value = value.substring(0, 1000);
			}
			result.put(rkey, value);
		}
		return result;
	}

	public HashMap<String,Map<String, Object>> getKeyValuesForMap(String regexp){
		//模糊查询，获取所有的keys
		Set<String> keys = fuzzyGetKeys(regexp);
		HashMap<String,Map<String, Object>> result = new HashMap<String,Map<String, Object>>();
		if(keys.isEmpty()) return result;

		List<Map<String, Object>> hashValues = batchHashGetAll(keys);
		List<String> list = Lists.newArrayList(keys);

		int size = list.size();
		for(int i=0;i<size;i++){
			String rkey = list.get(i);
			Map<String, Object> value = hashValues.get(i);
			result.put(rkey, value);
		}
		return result;
	}


	public HashMap<String,RedisList<String>> getKeyValuesForList(String regexp){
		Set<String> keys = fuzzyGetKeys(regexp);
		HashMap<String,RedisList<String>> result =  new HashMap<String,RedisList<String>>();
		if(keys.isEmpty()) return result;

		for(String key : keys){
			RedisList<String> values= redisList(key);
			result.put(key, values);
		}
		return result;
	}

	public HashMap<String,RedisZSet<String>> getKeyValuesForZSet(String regexp){
		Set<String> keys = fuzzyGetKeys(regexp);
		HashMap<String,RedisZSet<String>> result =  new HashMap<String,RedisZSet<String>>();
		if(keys.isEmpty()) return result;

		for(String key : keys){
			RedisZSet<String> values= redisZSet(key);
			result.put(key, values);
		}
		return result;
	}

	public HashMap<String,RedisSet<String>> getKeyValuesForSet(String regexp){
		Set<String> keys = fuzzyGetKeys(regexp);
		HashMap<String,RedisSet<String>> result =  new HashMap<String,RedisSet<String>>();
		if(keys.isEmpty()) return result;

		for(String key : keys){
			RedisSet<String> values= redisSet(key);
			result.put(key, values);
		}
		return result;
	}

	
	public boolean setKeyValue(String type, String key, Object value)throws Exception{
        JsonConfig jf = new JsonConfig();
        jf.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor(DATEFORMAT));
        jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor(DATEFORMAT));
        
		if("String".equalsIgnoreCase(type)) {
			if( key instanceof Object) { setValue(key, JSONArray.fromObject(value, jf).toString()); }
			else { setValue(key, value.toString()); }			
			return true;
			
		}else if("Set".equalsIgnoreCase(type)) {
			redisSet(key).add(JSONArray.fromObject(value, jf).toString());
			return true;
			
		}else if("List".equalsIgnoreCase(type)) {
			redisList(key).add(JSONArray.fromObject(value, jf).toString());
			return true;
			
		}else if("ZSet".equalsIgnoreCase(type)) {
			redisZSet(key).add(JSONArray.fromObject(value, jf).toString());
			return true;
			
		}else if("Map".equalsIgnoreCase(type)) {
			Field[] fields = value.getClass().getDeclaredFields();
			if (fields == null) return false;
			Map<String,String> map = new HashMap<String,String>();
			for(Field f : fields) {
				f.setAccessible(true);
				map.put(f.getName(), String.valueOf(f.get(value)));
			}
			redisMap(key).putAll(map);
			return true;
		}
		return false;

	}
	
	/**
	 * 模糊删除
	 * key格式：	prex + "*" + "suffix"
	 * 
	 * @param prex
	 * @param suffix
	 */
	public void deleteFuzzyKeys(String prex, String suffix) {
		Set<String> keys = getRedisTemplate().keys(prex + "*" + suffix);
		getRedisTemplate().delete(keys);
	}
	
	



	Jedis jedis;
	JsonConfig jf;
	
	private Pipeline getPipeline() {
		
		
		JedisConnectionFactory factory = (JedisConnectionFactory) getRedisTemplate()
				.getConnectionFactory();
		jedis = new Jedis(factory.getShardInfo());
		jedis.connect();
		jedis.select(factory.getDatabase());
		Pipeline pipe = jedis.pipelined();
		return pipe;
	}
	
	public <T> void batchSet(String type, List<String> keys, List<T> values) throws  Exception {
		JsonConfig jf = new JsonConfig();
		jf.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor(DATEFORMAT));
	    jf.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor(DATEFORMAT));
		System.out.println("******************  "+keys.size() + "   "+values.size()+"  *******************");
		if(keys.size() != values.size()) return;
		System.out.println("*************************************");
		Pipeline pipe = getPipeline();	
		for(int i = 0; i < keys.size(); i++){
			if("String".equalsIgnoreCase(type)) {
				if( values.get(i) instanceof Object) { 
					pipe.set(keys.get(i),JSONArray.fromObject(values.get(i), jf).toString());
				}else { 
					pipe.set(keys.get(i), values.get(i).toString());
				}
			}else if("Set".equalsIgnoreCase(type)) {
				pipe.sadd(keys.get(i),JSONArray.fromObject(values.get(i), jf).toString());				
			}else if("List".equalsIgnoreCase(type)) {
				pipe.lpush(keys.get(i),JSONArray.fromObject(values.get(i), jf).toString());					
			}else if("Map".equalsIgnoreCase(type)) {
				Field[] fields = values.get(i).getClass().getDeclaredFields();
				if (fields == null) return ;
				Map<String,String> map = new HashMap<String,String>();
				for(Field f : fields) {
					f.setAccessible(true);
					map.put(f.getName(), String.valueOf(f.get(values.get(i))));
				}
				pipe.hmset(keys.get(i), map);
			}
			
		}
		System.out.println("====================begin to execute sync()======================");
		pipe.sync();
		jedis.quit();
		System.out.println("====================jedis.quit()======================");
		
	}
	
}
