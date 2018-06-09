package com.chinaoly.frm.core.utils.redis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.util.Pool;
import redis.clients.util.SafeEncoder;
/**
 *
 * @author jiangyi
 * @Date 2018.5.11
 */
public class PipelineReader {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Pool<Jedis> jedisPool;

	public PipelineReader(Pool<Jedis> jedisPool) {
		this.jedisPool = jedisPool;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> batchRead(List<String> keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeline = jedis.pipelined();
			for (String key : keys) {
				pipeline.hgetAll(key);
			}
			List<Object> objs = pipeline.syncAndReturnAll();
			jedis.close();

			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			int i = 0;
			try {
				for (i = 0; i < keys.size(); i++) {
					result.put(keys.get(i), (Map<String, String>) objs.get(i));
				}
			} catch (Exception ex) {
				logger.error("读取redis异常,{} -- {}.", objs.get(i), objs.get(i)
						.getClass().getName());
				throw ex;
			}
			return result;
		} catch (Throwable th) {
			jedis.close();
			throw new RuntimeException(th);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> batchRead(List<String> keys,
			String... fields) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeline = jedis.pipelined();
			for (String key : keys) {
				pipeline.hmget(key, fields);
			}
			List<Object> objs = pipeline.syncAndReturnAll();
			jedis.close();

			Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
			int i = 0;
			try {
				for (i = 0; i < keys.size(); i++) {
					List<String> values = (List<String>) objs.get(i);
					Map<String, String> tmp = new HashMap<String, String>();
					for (int index = 0; index < fields.length; index++) {
						tmp.put(fields[index], values.get(index));
					}
					result.put(keys.get(i), tmp);
				}
			} catch (Exception ex) {
				logger.error("读取redis异常,{} -- {}.", objs.get(i), objs.get(i)
						.getClass().getName());
				throw ex;
			}
			return result;
		} catch (Throwable th) {
			jedis.close();
			throw new RuntimeException(th);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<byte[], byte[]>> batchReadByte(List<String> keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeline = jedis.pipelined();
			for (String key : keys) {
				pipeline.hgetAll(SafeEncoder.encode(key));
			}
			List<Object> objs = pipeline.syncAndReturnAll();
			jedis.close();

			Map<String, Map<byte[], byte[]>> result = new HashMap<String, Map<byte[], byte[]>>();
			int i = 0;
			try {
				for (i = 0; i < keys.size(); i++) {
					result.put(keys.get(i), (Map<byte[], byte[]>) objs.get(i));
				}
			} catch (Exception ex) {
				logger.error("读取redis异常,{} -- {}.", objs.get(i), objs.get(i)
						.getClass().getName());
				throw ex;
			}
			return result;
		} catch (Throwable th) {
			if(jedis != null){
				jedis.close();
			}
			throw new RuntimeException(th);
		}
	}
}
