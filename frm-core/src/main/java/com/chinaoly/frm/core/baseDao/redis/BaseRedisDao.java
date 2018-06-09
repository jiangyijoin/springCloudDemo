/**
 * redis基准DAO封装
 */
package com.chinaoly.frm.core.baseDao.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.data.redis.support.collections.DefaultRedisList;
import org.springframework.data.redis.support.collections.DefaultRedisMap;
import org.springframework.data.redis.support.collections.DefaultRedisSet;
import org.springframework.data.redis.support.collections.DefaultRedisZSet;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.data.redis.support.collections.RedisMap;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.data.redis.support.collections.RedisZSet;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
@Component
public class BaseRedisDao {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	protected StringRedisTemplate getRedisTemplate() {
		return stringRedisTemplate;
	}

	/**
	 * 获取String操作项
	 *
	 * @return
	 */
	public ValueOperations<String, String> valueOps() {
		return getRedisTemplate().opsForValue();
	}

	/**
	 * 一次性查询多个key的map(自动转换为目标object)
	 *
	 * @param keys
	 * @param mapper
	 * @param T
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <T> Map<String, T> mGetMap(String[] keys,
			HashMapper<T, String, String> mapper, Class<?> T) {
		Map<String, T> map = new HashMap<String, T>();
		if (keys != null && keys.length > 0) {
			// 由于spring data redis pipeline关闭有问题，故直接使用jedis进行操作
			// 直连，不使用连接池
			JedisConnectionFactory factory = (JedisConnectionFactory) getRedisTemplate()
					.getConnectionFactory();
			Jedis jedis = new Jedis(factory.getShardInfo());
			try {
				jedis.connect();
				jedis.select(factory.getDatabase());
				Set<byte[]> fields = jedis.hkeys(keys[0].getBytes());
				byte[][] fs = new byte[fields.size()][];
				int i = 0;
				for (byte[] f : fields) {
					fs[i] = f;
					i += 1;
				}
				Pipeline pipeline = jedis.pipelined();
				for (String key : keys) {
					pipeline.hmget(key.getBytes(), fs);
				}
				List<Object> l = pipeline.syncAndReturnAll();
				for (int n = 0; n < l.size(); n++) {
					List arr = (List) l.get(n);
					Map<String, String> obj = new HashMap<String, String>();
					for (int j = 0; j < arr.size(); j++) {
						obj.put(new String(fs[j]), arr.get(j).toString());
					}
					map.put(keys[n], mapper.fromHash(obj));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jedis.quit();
			}
		}
		return map;
	}

	/**
	 * 获取Set操作项
	 *
	 * @return
	 */
	public RedisSet<String> redisSet(String key) {
		return new DefaultRedisSet<String>(key, getRedisTemplate());
	}

	/**
	 * 获取ZSet操作项
	 *
	 * @param key
	 * @return
	 */
	public RedisZSet<String> redisZSet(String key) {
		return new DefaultRedisZSet<String>(key, getRedisTemplate());
	}

	/**
	 * 获取MAP操作项
	 *
	 * @return
	 */
	public RedisMap<String, String> redisMap(String key) {
		return new DefaultRedisMap<String, String>(key, getRedisTemplate());
	}

	/**
	 * 获取List操作项
	 *
	 * @return
	 */
	public RedisList<String> redisList(String key) {
		return new DefaultRedisList<String>(key, getRedisTemplate());
	}

	/**
	 * 查找String key
	 *
	 * @param key
	 * @return
	 */
	public String findKey(String key) {
		return valueOps().get(key);
	}

	/**
	 * 模糊查询匹配的keys
	 *
	 * @param pattern
	 * @return
	 */
	public Set<String> fuzzyGetKeys(String pattern) {
		return getRedisTemplate().keys(pattern);
	}

	/**
	 * List(key)中在头部新增一条value数据
	 * @param key
	 * @param value
	 */
	public void addListOne(String key, String value) {
		redisList(key).addFirst(value);
	}

	/**
	 * 新增一个字符串类型的值
	 * @param key 键
	 * @param value 值
	 */
	public void setValue(String key, String value) {
		valueOps().set(key, value);
	}

	/**
	 * 根据key键获得字符串类型的值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return valueOps().get(key);
	}

	/**
	 * 判断key是否存在
	 *
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key) {
		return getRedisTemplate().hasKey(key);
	}

	/**
	 * 通过keys删除数据
	 *
	 * @param keys
	 */
	public void deleteKeys(Collection<String> keys) {
		getRedisTemplate().delete(keys);
	}

	/**
	 * 通过key删除数据
	 *
	 * @param key
	 */
	public void deleteKey(String key) {
		getRedisTemplate().delete(key);
	}

	/**
	 * This 批量的batchHashGetAll
	 *
	 * @param <K>
	 * @param keys
	 * @return
	 */
	public List<Map<String, Object>> batchHashGetAll(Set<String> keys) {

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (String key : keys) {
			Set<Map.Entry<String, String>> entrys = redisMap(key).entrySet();
			Map<String, Object> values = new HashMap<String, Object>();
			for (Entry<String, String> entry : entrys) {
				values.put(entry.getKey(), entry.getValue());
			}
			dataList.add(values);
		}
		return dataList;
	}

	/**
	 * 获取自增索引
	 *
	 * @param key
	 * @return
	 */
	public String incrementAndGet(String key) {
		if (StringUtils.isNotEmpty(key)) {
			RedisAtomicLong entityIdCounter = new RedisAtomicLong(key,
					getRedisTemplate().getConnectionFactory());
			return String.valueOf(entityIdCounter.incrementAndGet());
		} else
			return "";
	}

	/**
	 * 清空当前数据库中的所有KEY
	 */
	public void flushDB() {
		getRedisTemplate().getConnectionFactory().getConnection().flushDb();
	}

	/**
	 * 返回当前数据库的KEY的数量
	 *
	 * @return
	 */
	public long dbSize() {
		return getRedisTemplate().getConnectionFactory().getConnection()
				.dbSize();
	}

	/**
	 * 选择数据库，默认是0数据库
	 *
	 * @param index
	 */
	public void selectDB(int index) {
		getRedisTemplate().getConnectionFactory().getConnection().select(index);
	}

	/**
	 * 设置过期时间
	 *
	 *  @param key
	 *  @param timeout  过期时间（秒）
	 */
	public void setExpire(String key , int timeout) {
		getRedisTemplate().expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置过期时间
	 *
	 *  @param key
	 *  @param timeout  过期时间（秒）
	 */
	public void setExpire(String key , Date date) {
		getRedisTemplate().expireAt(key, date);
	}
}
