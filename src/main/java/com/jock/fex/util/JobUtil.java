package com.jock.fex.util;

public class JobUtil {

	/**
	 * 2018年7月28日<br>
	 * 线程沉睡
	 * 
	 * @param millis
	 */
	public final static void threadSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2018年7月28日<br>
	 * 随机沉睡
	 * 
	 * @param count
	 *            沉睡毫秒位数
	 */
	public final static void ramdomSleep(int count) {
		final long millis = Long.valueOf(RamdomUtils.getRamdom(count));
		threadSleep(millis);
	}

	/**
	 * 2018年7月28日<br>
	 * 获取job的运行状态，start or end
	 * 
	 * @param key
	 * @return
	 */
	public final static String jobStatus(String key) {
		return RedisUtils.getByKey(key);
	}

	/**
	 * 2018年7月28日<br>
	 * job开始
	 * 
	 * @param key
	 * @return true-已经运行，false-没有运行
	 */
	public final static boolean jobStart(String key) {
		try {
			final String value = jobStatus(key);
			if (value == null || "end".equals(value)) {
				// 有效期一个小时（一个任务执行一个小时应该够了）
				RedisUtils.add(key, "start", 60 * 60 * 1000L);
				return false;
			} else if ("start".equals(value)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 2018年7月28日<br>
	 * job结束
	 * 
	 * @param key
	 */
	public final static void jobEnd(String key) {
		try {
			RedisUtils.add(key, "end", 60 * 60 * 1000L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
