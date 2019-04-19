package com.jock.fex.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {

	/**
	 * double类型默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static BigDecimal defaultValue(BigDecimal val, double def) {
		if (val == null) {
			return new BigDecimal(def);
		}
		return val;
	}

	/**
	 * int类型默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static BigDecimal defaultValue(BigDecimal val, int def) {
		if (val == null) {
			return new BigDecimal(def);
		}
		return val;
	}

	/**
	 * String类型默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static BigDecimal defaultValue(BigDecimal val, String def) {
		if (val == null) {
			return new BigDecimal(def);
		}
		return val;
	}

	/**
	 * long类型默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static BigDecimal defaultValue(BigDecimal val, long def) {
		if (val == null) {
			return new BigDecimal(def);
		}
		return val;
	}

	/**
	 * BigDecimal类型默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static BigDecimal defaultValue(BigDecimal val, BigDecimal def) {
		if (val == null) {
			return def;
		}
		return val;
	}

	/**
	 * Integer默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static Integer defaultValue(Integer val) {
		return defaultValue(val, 0);
	}

	/**
	 * Integer默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static Integer defaultValue(Integer val, int def) {
		if (val == null) {
			return def;
		}
		return val;
	}

	/**
	 * 2019年3月23日<br>
	 * Integer默认值，默认值0
	 * 
	 * @param val
	 * @return
	 */
	public final static Integer defaultValue(String val) {
		return defaultValue(val, 0);
	}

	/**
	 * 2019年3月23日<br>
	 * Integer默认值
	 * 
	 * @param val
	 * @param def
	 * @return
	 */
	public final static Integer defaultValue(String val, int def) {
		if (val == null || val.equals("")) {
			return def;
		}
		return Integer.valueOf(val);
	}

	/**
	 * Long默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static Long defaultValue(Long val, long def) {
		if (val == null) {
			return def;
		}
		return val;
	}

	/**
	 * Short默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static Short defaultValue(Short val, short def) {
		if (val == null) {
			return def;
		}
		return val;
	}

	/**
	 * Short默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static Double defaultValue(Double val, double def) {
		if (val == null) {
			return def;
		}
		return val;
	}

	/**
	 * Short默认值
	 *
	 * @param val
	 * @param def
	 * @return
	 */
	public final static Float defaultValue(Float val, float def) {
		if (val == null) {
			return def;
		}
		return val;
	}

	/**
	 * 2018年11月23日<br>
	 * 两个数字是否相等
	 *
	 * @param first
	 * @param second
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(Long first, Long second) {
		return (first == null && second == null)
				|| (first != null && second != null && first.longValue() == second.longValue());
	}

	/**
	 * 2018年11月23日<br>
	 * 两个数字是否相等
	 *
	 * @param first
	 * @param second
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(Integer first, Integer second) {
		return (first == null && second == null)
				|| (first != null && second != null && first.intValue() == second.intValue());
	}

	/**
	 * 2018年11月23日<br>
	 * 两个数字是否相等
	 *
	 * @param first
	 * @param second
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(Long first, Integer second) {
		return (first == null && second == null)
				|| (first != null && second != null && first.intValue() == second.longValue());
	}

	/**
	 * 2018年11月23日<br>
	 * 两个数字是否相等
	 *
	 * @param first
	 * @param second
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(Short first, Short second) {
		return (first == null && second == null)
				|| (first != null && second != null && first.shortValue() == second.shortValue());
	}

	/**
	 * 2018年11月23日<br>
	 * 两个数字是否相等
	 *
	 * @param first
	 * @param second
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(Integer first, Short second) {
		return (first == null && second == null)
				|| (first != null && second != null && first.intValue() == second.intValue());
	}

	/**
	 * 2018年11月23日<br>
	 * 两个数字是否相等
	 *
	 * @param first
	 * @param second
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(Long first, Short second) {
		return (first == null && second == null)
				|| (first != null && second != null && first.longValue() == second.longValue());
	}

	/**
	 * 获取四舍五入后的数据
	 *
	 * @param msg
	 *            需要处理的数据
	 * @param scale
	 *            需要保留小数点后几位数据
	 * @param mode
	 *            数值格式化的方式
	 * @return
	 */
	public final static BigDecimal bigDecimalDealMsg(String msg, int scale, RoundingMode mode) {
		BigDecimal bigDecimal = new BigDecimal(msg);
		bigDecimal = bigDecimal.setScale(scale, mode);
		return bigDecimal;
	}

	public final static BigDecimal bigDecimalDealMsg(String msg) {
		return bigDecimalDealMsg(msg, 2, RoundingMode.HALF_UP);
	}

	public final static BigDecimal bigDecimalDealMsg(String msg, int scale) {
		return bigDecimalDealMsg(msg, scale, RoundingMode.HALF_UP);
	}

	public final static BigDecimal bigDecimalDealMsg(String msg, RoundingMode mode) {
		return bigDecimalDealMsg(msg, 2, mode);
	}

}
