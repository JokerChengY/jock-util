package com.jock.fex.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtils
 * @Description: redis 提供5种数据类型的操作 String ,hash ,list , set , zset
 * @author yangcs
 * @date 2017年10月25日 下午2:01:43
 */

public class RedisUtils {

	@Autowired
	private static StringRedisTemplate stringRedisTemplate;

	static {
		stringRedisTemplate = (StringRedisTemplate) SpringUtil.getBean("stringRedisTemplate");
	}

	public static StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}

	/**
	 * 添加数据到redis
	 * 
	 * @param key
	 * @param obj
	 * @param time
	 *            过期时间(毫秒)
	 */
	public static void add(String key, Object obj, Long time) {
		final Gson gson = new Gson();
		stringRedisTemplate.opsForValue().set(key, gson.toJson(obj), time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 添加数据到redis
	 * 
	 * @param key
	 * @param value
	 * @param time
	 */
	public static void add(String key, String value, Long time) {
		stringRedisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 添加集合到redis
	 * 
	 * @param key
	 * @param list
	 * @param time
	 *            过期时间(毫秒)
	 */
	public static void addList(String key, List<? extends Object> list, Long time) {
		final Gson gson = new Gson();
		stringRedisTemplate.opsForValue().set(key, gson.toJson(list), time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 查询redis对象数据
	 * 
	 * @param key
	 * @param destCalss
	 * @return
	 */
	public static <T> T getByKey(String key, Class<T> destCalss) {
		final Gson gson = new Gson();
		T user = null;
		final String json = stringRedisTemplate.opsForValue().get(key);
		if (StringUtils.isNotEmpty(json)) {
			user = gson.fromJson(json, destCalss);
		}
		return user;
	}

	/**
	 * 查询字符串
	 * 
	 * @param key
	 * @return
	 */
	public static String getByKey(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 查询redis集合
	 * 
	 * @param key
	 * @param destCalss
	 * @return
	 */
	@Deprecated
	public static <T extends Serializable> List<T> getUserListByKey(String key, Class<T> destCalss) {
		final Gson gson = new Gson();
		List<T> list = null;
		final String json = stringRedisTemplate.opsForValue().get(key);
		if (StringUtils.isNotEmpty(json)) {
			list = gson.fromJson(json, new TypeToken<List<T>>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 2018年10月25日<br>
	 * 获取list数据
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * 
	 * @param <T>
	 * 
	 * @param key
	 * @param destCalss
	 * @return
	 */
	public final static <T> List<T> getList(String key, Class<T> t) {
		final Gson gson = new Gson();
		List<T> list = null;
		final String json = stringRedisTemplate.opsForValue().get(key);
		if (StringUtils.isNotEmpty(json)) {
			list = gson.fromJson(json, new TypeToken<T>() {
			}.getType());
		}
		return list;
	}

	/**
	 * 删除redis数据
	 * 
	 * @param key
	 */
	public static void deleteByKey(String key) {
		stringRedisTemplate.opsForValue().getOperations().delete(key);
	}

	/**
	 * 添加hash表
	 * 
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public static Boolean addHashTable(String tableName, Object key, Object value) {
		return stringRedisTemplate.opsForHash().putIfAbsent(tableName, key, value);
	}

	/**
	 * 查询hash表
	 * 
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public static Map<Object, Object> selectHashTable(String tableName) {
		return stringRedisTemplate.opsForHash().entries(tableName);
	}

	/**
	 * 查询hash表
	 * 
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public static Object selectHashTable(String tableName, String key) {
		return stringRedisTemplate.opsForHash().get(tableName, key);
	}

	/**
	 * 校验hash表
	 * 
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public static Boolean existsHashTable(String tableName, Object key) {
		return stringRedisTemplate.opsForHash().hasKey(tableName, key);
	}

	/**
	 * 删除hash表
	 * 
	 * @param tableName
	 * @param key
	 * @param value
	 */
	public static Long deleteHashTable(String tableName, Object key) {
		return stringRedisTemplate.opsForHash().delete(tableName, key);
	}

}