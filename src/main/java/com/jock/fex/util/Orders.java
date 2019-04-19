package com.jock.fex.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntSupplier;

public final class Orders {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

	private static final String KEY = "order-sn:";

	private static final LocalTime EXPIRE_TIME = LocalTime.of(23, 59, 59);

	/**
	 * 日期6位+下单渠道1位+业务类型1位+单量4位+随机码4位
	 * 
	 * @param channel
	 *            下单渠道: 1:APP; 2:微信; 3:小程序; 4:网页; 5:其他
	 * @param businessType
	 *            业务类型: 1:按揭; 2:查册; 3:评估; 4:商场; 5:贷款; 6:提现 ; 7:报备; 8:红包; 9:中介协会; 0:其他
	 * @param countCall
	 *            数据库查日单量
	 * @return sn 流水号
	 * @author ldt
	 */
	public static String getSn(int channel, int businessType, IntSupplier countCall) {
		return getSn(channel, businessType, countCall, RedisUtils.getStringRedisTemplate());
	}

	public static String getSn(int channel, int businessType, IntSupplier countCall, StringRedisTemplate template) {
		if (countCall == null || template == null) {
			throw new IllegalArgumentException();
		}

		LocalDate localDate = LocalDate.now();
		String localDateStr = localDate.format(FORMATTER);

		RedisSerializer<String> stringSerializer = template.getStringSerializer();
		
		int count = template.execute(new RedisCallback<Integer>() {

			@Override
			public Integer doInRedis(RedisConnection connection) throws DataAccessException {
				// 统一存储在3号数据库
				connection.select(3);

				byte[] key = stringSerializer.serialize(buildKey(localDateStr, businessType));
				byte[] value = connection.get(key);

				int count;
				if (value == null) {
					count = countCall.getAsInt() + 1;
					long expireSeconds = ChronoUnit.SECONDS.between(LocalTime.now(), EXPIRE_TIME);
					connection.setEx(key, expireSeconds, stringSerializer.serialize(String.valueOf(count)));
				} else {
					count = Integer.valueOf(stringSerializer.deserialize(value)) + 1;
					connection.incr(key);
				}
				return count;
			}

		});

		return new StringBuilder(localDateStr)//
				.append(channel)//
				.append(businessType)//
				.append(String.format("%04d", count))//
				.append(RandomStringUtils.randomNumeric(4))//
				.toString();
	}

	/**
	 * 下单渠道
	 */
	public static final Map<String, String> CHANNEL = new HashMap<String, String>() {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		{
			put("1", "良策APP");
			put("2", "微信");
			put("3", "小程序");
			put("4", "网页");
			put("5", "其他");
		}
	};

	private static String buildKey(String localDate, int type) {
		return KEY + localDate + ":" + type;
	}

	/**
	 * 业务类型
	 */
	public static final Map<String, String> BUSINESS_TYPE;
	static {
		BUSINESS_TYPE = new HashMap<String, String>();
		BUSINESS_TYPE.put("1", "按揭");
		BUSINESS_TYPE.put("2", "查册");
		BUSINESS_TYPE.put("3", "评估");
		BUSINESS_TYPE.put("4", "商城");
		BUSINESS_TYPE.put("5", "贷款");
		BUSINESS_TYPE.put("6", "提现");
		BUSINESS_TYPE.put("7", "报备");
		BUSINESS_TYPE.put("8", "红包");
	}

	/**
	 * 根据流水号解析出业务类型
	 * 
	 * @param sn
	 *            流水号
	 * @return 业务类型
	 * @author ldt
	 */
	public static String getBusinessType(String sn) {
		return BUSINESS_TYPE.get(String.valueOf(sn.charAt(7)));
	}

}
