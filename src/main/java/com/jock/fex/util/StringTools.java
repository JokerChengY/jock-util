package com.jock.fex.util;

import org.springframework.util.StringUtils;

/**
 * 2018年11月7日<br>
 * 字符串工具类<br>
 * 该类是为了和其他jar的名称区分开来，避免冲突，希望以后都使用这个类
 * 
 * @author 0
 *
 */
public class StringTools {

	/**
	 * 2018年11月7日<br>
	 * 字符串拼接
	 * 
	 * @param str
	 * @return
	 */
	public final static String strSplicing(String... str) {
		String result = "";

		if (str != null && str.length > 0) {
			for (String s : str) {
				if (s != null && s.length() > 0) {
					result += s;
				}
			}
		}

		return result;
	}

	/**
	 * 2018年11月7日<br>
	 * 真假判断
	 * 
	 * @param blean
	 * @param falseStr
	 * @param trueStr
	 * @return
	 */
	public final static String ifTrue(boolean blean, String falseStr, String... trueStr) {
		String result = "";
		if (blean) {
			result = strSplicing(trueStr);
		} else {
			result = falseStr;
		}
		return result;
	}

	/**
	 * 2018年11月7日<br>
	 * url判断，uri中以http开头的则直接返回uri，没有http则加上domain
	 * 
	 * @param domain
	 * @param uri
	 * @return
	 */
	public final static String urlJudge(String domain, String uri) {
		if (StringUtils.isEmpty(uri)) {
			return "";
		}

		if (uri.indexOf("http") == 0) {
			return uri;
		}

		return strSplicing(domain, uri);
	}

	/**
	 * 2018年11月7日<br>
	 * url判断，uri中以http开头的则直接返回uri，没有http则加上domain
	 * 
	 * @param domain
	 * @param uri
	 * @return
	 */
	public final static String urlJudge(String domain, Object uri) {
		if (StringUtils.isEmpty(uri)) {
			return "";
		}

		return urlJudge(domain, uri.toString());
	}

	/**
	 * 2018年11月23日<br>
	 * 两个字符串是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @return true-相等，false-不相等
	 */
	public final static boolean equals(String first, String second) {
		return (first == null && second == null) || (first != null && first.equals(second));
	}

	/**
	 * 2018年12月8日<br>
	 * 如果是null，返回默认值
	 * 
	 * @param ori
	 *            源字符串
	 * @param def
	 *            默认字符串
	 * @return
	 */
	public final static String ifnull(String ori, String def) {
		if (ori == null) {
			return def;
		}
		return ori;
	}

	/**
	 * 2018年12月8日<br>
	 * 判断字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean isEmpty(String s) {
		return s == null || s.length() == 0;
	}

	/**
	 * 2018年12月18日<br>
	 * 如果是null就返回空
	 * 
	 * @param obj
	 * @return
	 */
	public final static String ifnullToEmpty(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	/**
	 * 2019年3月26日<br>
	 * 字符串默认值
	 * 
	 * @param value
	 * @return
	 */
	public final static String defVal(String value) {
		return defVal(value, "");
	}

	/**
	 * 2019年3月26日<br>
	 * 字符串默认值
	 * 
	 * @param value
	 * @param defVal
	 * @return
	 */
	public final static String defVal(String value, String defVal) {
		if (value == null || value == "") {
			return defVal;
		}
		return value;
	}
}
