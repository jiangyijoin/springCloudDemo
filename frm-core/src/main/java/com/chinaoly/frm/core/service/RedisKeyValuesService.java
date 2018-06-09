package com.chinaoly.frm.core.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.collections.RedisList;
import org.springframework.data.redis.support.collections.RedisSet;
import org.springframework.data.redis.support.collections.RedisZSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chinaoly.frm.core.baseDao.redis.RedisKeyValuesDao;
import com.chinaoly.frm.core.service.ServiceException;

@Component
public class RedisKeyValuesService {

	@Autowired
	private RedisKeyValuesDao redisKeyValuesDao;

	/**
	 * 设置key和value
	 * @param type	类型："string","list","set","zSet","map"
	 * @param key
	 * @param value
	 * @return  true（设置成功）/false（设置不成功）
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public boolean setKeyValue(String type, String key, Object value)throws Exception{
		return redisKeyValuesDao.setKeyValue(type, key, value);
	}

	/**
	 * 删除一个key
	 * @param key
	 * @throws ServiceException
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteKey(String key) throws ServiceException{
		redisKeyValuesDao.deleteKey(key);
	}
	
	/**
	 * 批量删除keys
	 * @param keys
	 * @throws ServiceException
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteKeys(Collection<String> keys) throws ServiceException{
		redisKeyValuesDao.deleteKeys(keys);
	}
	
	/**
	 * 模糊匹配删除keys
	 * @param keys
	 * @throws ServiceException
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteFuzzyKeys(String prex, String suffix) throws ServiceException{
		redisKeyValuesDao.deleteFuzzyKeys(prex, suffix);
	}
	
	
	/**
	 * 选择数据库
	 * @param index
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void selectDB(int index) throws Exception{
		redisKeyValuesDao.selectDB(index);
	}
	
	
	
	/*
	public HashMap<String,String> getKeyValuesForString(String regexp) throws ServiceException;
	
	public HashMap<String,Map<String, Object>> getKeyValuesForMap(String regexp) throws ServiceException;
	
	public HashMap<String,RedisList<String>> getKeyValuesForList(String regexp)throws ServiceException;

	public HashMap<String,RedisZSet<String>> getKeyValuesForZSet(String regexp)throws ServiceException;

	public HashMap<String,RedisSet<String>> getKeyValuesForSet(String regexp)throws ServiceException;*/

	/**
	 * 是否存在key
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key){
		return redisKeyValuesDao.hasKey(key);
	}

	/**
	 * 获得string值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String getStringValue(String key) throws Exception{
		return redisKeyValuesDao.getValue(key);
	}

	/**
	 * 获得map的值
	 * @param key
	 * @param mapKey
	 * @return
	 */
	public String getMapValue(String key, String mapKey) throws Exception{
		return redisKeyValuesDao.redisMap(key).get(mapKey);
	}

	/**
	 * 获得set的值
	 * @param key
	 * @return
	 */
	public Set<String> getSetValue(String key) throws Exception{
		return  redisKeyValuesDao.redisSet(key);
	}

	/**
	 * 获得ZSet的值
	 * @param key
	 * @return
	 */
	public Set<String> getZSetValue(String key) throws Exception{
		return redisKeyValuesDao.redisZSet(key);
	}

	/**
	 * 获得list的值
	 * @param key
	 * @return
	 */
	public List<String> getListValue(String key)throws Exception{
		return redisKeyValuesDao.redisList(key);
	}

	
	/**
	 * 模糊查询获取KEY Values
	 */
	public HashMap<String,String> getKeyValuesForString(String type, String regexp) throws ServiceException{
		return redisKeyValuesDao.getKeyValuesForString(regexp);
	}

	public HashMap<String,Map<String, Object>> getKeyValuesForMap(String regexp) throws ServiceException{
		return redisKeyValuesDao.getKeyValuesForMap(regexp);
	}

	public HashMap<String,RedisList<String>> getKeyValuesForList(String regexp)throws ServiceException{
		return redisKeyValuesDao.getKeyValuesForList(regexp);
	}

	public HashMap<String,RedisZSet<String>> getKeyValuesForZSet(String regexp)throws ServiceException{
		return redisKeyValuesDao.getKeyValuesForZSet(regexp);
	}

	public HashMap<String,RedisSet<String>> getKeyValuesForSet(String regexp)throws ServiceException{
		return redisKeyValuesDao.getKeyValuesForSet(regexp);
	}
	
	/**
	 * 设置过期时间
	 *
	 *  @param key
	 *  @param timeout  过期时间（秒）
	 */
	public void setExpire(String key , int timeout) {
		redisKeyValuesDao.setExpire(key, timeout);
	}

	/**
	 * 设置过期时间
	 *
	 *  @param key
	 *  @param date  过期时间（date）
	 */
	public void setExpire(String key , Date date) {
		redisKeyValuesDao.setExpire(key, date);
	}
}
