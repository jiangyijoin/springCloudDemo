package com.chinaoly.frm.core.utils.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.Pool;
import redis.clients.util.SafeEncoder;

import com.chinaoly.frm.common.utils.ListUtils;
/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class RedisProxy {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("jedisPool")
	private Pool<Jedis> jedisPool;
	private int maxBatchSize = 1000;
	private int maxHllBatchSize = 200;
	private int multiThreads = 0;
	private int expireSec;// redis2 key expire seconds
	private int expireSec2;
	private int expireSec3;
	private PipelineReader pipelineReader;
	private MultiReader multiReader;
	private int maxWBatchSize = 200;
	private int maxWHllBatchSize = 50;

	public int getExpireSec2() {
		return expireSec2;
	}

	public void setExpireSec2(int expireSec2) {
		this.expireSec2 = expireSec2;
	}

	public int getExpireSec3() {
		return expireSec3;
	}

	public void setExpireSec3(int expireSec3) {
		this.expireSec3 = expireSec3;
	}

	public void setJedisPool(Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}

	public Pool<Jedis> getJedisPool() {
		return jedisPool;
	}

	public void setMaxBatchSize(int maxBatchSize) {
		this.maxBatchSize = maxBatchSize;
	}

	public int getMaxBatchSize() {
		return maxBatchSize;
	}

	public int getMaxHllBatchSize() {
		return maxHllBatchSize;
	}

	public void setMaxHllBatchSize(int maxHllBatchSize) {
		this.maxHllBatchSize = maxHllBatchSize;
	}

	public void setMultiThreads(int multiThreads) {
		this.multiThreads = multiThreads;
	}

	public PipelineReader getPipelineReader() {
		return pipelineReader;
	}

	public MultiReader getMultiReader() {
		return multiReader;
	}

	public void setExpireSec(int expireSec) {
		this.expireSec = expireSec;
	}

	public void setPipelineReader(PipelineReader pipelineReader) {
		this.pipelineReader = pipelineReader;
	}

	public void init() {
		pipelineReader = new PipelineReader(jedisPool);
		multiReader = new MultiReader(pipelineReader, maxBatchSize,
				maxHllBatchSize, multiThreads);
	}

	// string
	public boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			boolean result = false;
			if (jedis.exists(key)) {
				result = true;
			}
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.expire(key, seconds);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void expire(byte[] key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.expire(key, seconds);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public byte[] get(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] result = jedis.get(key);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public String get(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String result = jedis.get(key);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public String rpop(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String result = jedis.rpop(key);
			jedis.close();
			return result;
		} catch (JedisException e) {
			if (jedis != null) {
				jedis.close();
			}
			throw e;
		}
	}

	public byte[] dump(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] result = jedis.dump(key);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public String restore(String key, int ttl, byte[] dump) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String result = jedis.restore(key, ttl, dump);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void set(byte[] key, byte[] data) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, data);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void set(String key, String data) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, data);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void del(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void lpush(String queue, String... values) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.lpush(queue, values);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void lpushByte(String queue, byte[]... values) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.lpush(SafeEncoder.encode(queue), values);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	// set
	public Long sadd(String key, String... members) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long result = jedis.sadd(key, members);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public Long srem(String key, String... members) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long result = jedis.srem(key, members);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public Set<String> smembers(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<String> set = jedis.smembers(key);
			jedisPool.returnResource(jedis);
			return set;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public String spop(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String res = jedis.spop(key);
			jedisPool.returnResource(jedis);
			return res;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public Boolean sismember(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Boolean res = jedis.sismember(key, member);
			jedisPool.returnResource(jedis);
			return res;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	/*
	 * orderd set
	 */
	public Set<String> zrange(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<String> set = jedis.zrange(key, start, end);
			jedisPool.returnResource(jedis);
			return set;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void zadd(String key, double score, String member) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.zadd(key, score, member);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public Set<Tuple> zrangeWithScores(String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<Tuple> result = jedis.zrangeWithScores(key, start, end);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public Long zcard(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long count = jedis.zcard(key);
			jedisPool.returnResource(jedis);
			return count;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public Long zcount(String key, double min, double max) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long count = jedis.zcount(key, min, max);
			jedisPool.returnResource(jedis);
			return count;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	// hash
	public List<String> hmget(String key, String... fields) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<String> set = jedis.hmget(key, fields);
			jedisPool.returnResource(jedis);
			return set;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void hmset(String key, Map<String, String> hash) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key, hash);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void hmset(Map<String, Map<String, String>> hashs) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeline = jedis.pipelined();

			for (String key : hashs.keySet()) {
				Map<String, String> hash = hashs.get(key);
				if (hash == null || hash.isEmpty()) {
					continue;
				}
				pipeline.hmset(key, hash);
			}
			pipeline.sync();
			jedis.close();
		} catch (JedisException e) {
			jedis.close();
			throw e;
		}
	}

	public void hmset(byte[] key, Map<byte[], byte[]> hash) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hmset(key, hash);
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public void hset(byte[] key, byte[] field, byte[] value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (JedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void hset(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key, field, value);
		} catch (JedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
	

	public void hmsetBytes(Map<byte[], Map<byte[], byte[]>> hashs) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeline = jedis.pipelined();

			for (byte[] key : hashs.keySet()) {
				Map<byte[], byte[]> hash = hashs.get(key);
				if (hash == null || hash.isEmpty()) {
					continue;
				}
				pipeline.hmset(key, hash);
			}

			pipeline.sync();
			jedisPool.returnResource(jedis);
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	public Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Map<String, String> result = jedis.hgetAll(key);
			jedis.close();
			return result;
		} catch (JedisException e) {
			throw e;
		} finally{
			jedis.close();
		}
	}

	public String hget(String key, String field) {
		Jedis jedis = null;
		String result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.hget(key, field);
		} catch (JedisException e) {
			throw e;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Map<String, String> hgetAll(String key, boolean copyTo2nd) {
		Jedis jedis = null;
		Map<String, String> result = new HashMap<String, String>();
		try {
			jedis = jedisPool.getResource();
			result = jedis.hgetAll(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return result;
	}

	public Set<String> hkeys(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<String> result = jedis.hkeys(key);
			jedis.close();
			return result;
		} catch (JedisException e) {
			throw e;
		} finally {
			jedis.close();
		}
	}

	public Map<byte[], byte[]> hgetAll(byte[] redisKey) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Map<byte[], byte[]> result = jedis.hgetAll(redisKey);
			jedisPool.returnResource(jedis);
			return result;
		} catch (JedisException e) {
			jedisPool.returnBrokenResource(jedis);
			throw e;
		}
	}

	// batch
	public Map<String, Map<String, String>> hgetAll(List<String> keys) {
		if (keys.size() == 0) {
			return new HashMap<String, Map<String, String>>();
		}
		if (keys.size() <= maxBatchSize) {
			return pipelineReader.batchRead(keys);
		} else {
			List<List<String>> keysList = ListUtils.fixedSize(keys,
					maxBatchSize);
			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			for (List<String> ks : keysList) {
				result.putAll(pipelineReader.batchRead(ks));
			}
			return result;
		}
	}
	
	public Map<String, Map<String, String>> hgetAll(List<String> keys,
			boolean copyTo2nd) {
		Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
		if (keys.size() == 0) {
			result = new HashMap<String, Map<String, String>>();
		} else {
			if (keys.size() <= maxBatchSize) {
				result = pipelineReader.batchRead(keys);
			} else {
				List<List<String>> keysList = ListUtils.fixedSize(keys,
						maxBatchSize);
				for (List<String> ks : keysList) {
					result.putAll(pipelineReader.batchRead(ks));
				}
			}
		}
		return result;
	}

	public Map<String, Map<String, String>> hgetAllMulti(List<String> keys) {
		if (keys.size() == 0) {
			return new HashMap<String, Map<String, String>>();
		}
		if (keys.size() <= maxBatchSize) {
			return pipelineReader.batchRead(keys);
		} else {
			return multiReader.hgetAll(keys);
		}
	}

	public Map<String, Map<String, String>> hmget(List<String> keys,
			String... fields) {
		if (keys.size() == 0) {
			return new HashMap<String, Map<String, String>>();
		}
		if (keys.size() <= maxBatchSize) {
			return pipelineReader.batchRead(keys, fields);
		} else {
			List<List<String>> keysList = ListUtils.fixedSize(keys,
					maxBatchSize);
			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			for (List<String> ks : keysList) {
				result.putAll(pipelineReader.batchRead(ks, fields));
			}
			return result;
		}
	}

	public Map<String, Map<String, String>> hmgetMulti(List<String> keys,
			String... fields) {
		if (keys.size() == 0) {
			return new HashMap<String, Map<String, String>>();
		}
		if (keys.size() <= maxBatchSize) {
			return pipelineReader.batchRead(keys, fields);
		} else {
			return multiReader.hmget(keys, fields);
		}
	}

	public Map<String, Map<byte[], byte[]>> hgetAllByte(List<String> keys) {
		if (keys.size() == 0) {
			return new HashMap<String, Map<byte[], byte[]>>();
		}
		if (keys.size() <= maxBatchSize) {
			return pipelineReader.batchReadByte(keys);
		} else {
			List<List<String>> keysList = ListUtils.fixedSize(keys,
					maxBatchSize);
			Map<String, Map<byte[], byte[]>> result = new HashMap<String, Map<byte[], byte[]>>();
			for (List<String> ks : keysList) {
				result.putAll(pipelineReader.batchReadByte(ks));
			}
			return result;
		}
	}

	public Map<String, Map<byte[], byte[]>> hgetAllByte(List<String> keys,
			boolean copyTo2nd) {
		Map<String, Map<byte[], byte[]>> result = new HashMap<String, Map<byte[], byte[]>>();
		if (keys.size() == 0) {
			result = new HashMap<String, Map<byte[], byte[]>>();
		}
		if (keys.size() <= maxBatchSize) {
			result = pipelineReader.batchReadByte(keys);
		} else {
			List<List<String>> keysList = ListUtils.fixedSize(keys,
					maxBatchSize);
			for (List<String> ks : keysList) {
				result.putAll(pipelineReader.batchReadByte(ks));
			}
		}
		return result;
	}

	public static String getMatchRslt(String regex, String data) {
		String rsltStr = null;
		Matcher m = Pattern.compile(regex).matcher(data);
		if (m.find()) {
			rsltStr = m.group(1);
		}
		return rsltStr;
	}
	
//2016.08.20 增加批量写str的方法	
	protected Map<String, String> toStringMap(Map<String, Long> info) {
		Map<String, String> result = new HashMap<String, String>();
		for (String field : info.keySet()) {
			result.put(field, String.valueOf(info.get(field)));
		}
		return result;
	}

	public static void main(String[] args) {
		/*
		String key = "_IDCR:黄山市:高速公路:杭瑞高速:1602060100";
		String time = getMatchRslt(".*(:\\d{6,10}).*", key);
		System.out.println(time);
		String truncatedKey = key.replace(time, "");
		System.out.println(truncatedKey);
       */
		int maxBatchSize = 1000;
	      List<String> keys = new ArrayList<String>(); 
	      keys.add("3");
	      keys.add("5");
	      keys.add("6"); 
		List<List<String>> keysList = ListUtils.fixedSize(keys,maxBatchSize);
		System.out.println(ListUtils.fixedSize(keys,maxBatchSize));
	}
}