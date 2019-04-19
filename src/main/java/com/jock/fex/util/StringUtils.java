package com.jock.fex.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 去除字符串左右的空格
	 *
	 * @author Shenyq
	 * @param targetStr
	 * @return String
	 */
	public static String trim(String targetStr) {
		return targetStr == null ? "" : targetStr.trim();
	}

	/**
	 * 判断是否空值或空串
	 *
	 * @author Shenyq
	 * @param targetStr
	 *            (String) 被检测的字符串
	 * @return boolean 返回true 则为空 false 反之
	 */
	public static boolean isNull(String targetStr) {
		return (targetStr == null || targetStr.trim().length() <= 0);
	}

	public static String getNowTime() {
		return getNowTime("yyyy-MM-dd HH:mm:ss");
	}

	public static String getNowTime(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	public static String replaceTab(String str) {
		String regex = "\\s+3";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll("");
	}

	/**
	 * 如果null则返回空
	 * 
	 * @param obj
	 * @return
	 */
	public final static String ifNull(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * 2018年7月27日<br>
	 * 签名判断
	 * 
	 * @param sign
	 * @param property
	 * @return
	 */
	public final static boolean signJudge(String sign, String... property) {
		String str = "";
		for (String s : property) {
			str += s;
		}
		System.out.println("加密字符串：" + str);
		if (sign.equals(Md5Util.getMD5(str))) {
			return true;
		}
		return false;
	}

	/**
	 * 拼接字符串 参数列表有值就先拼接，没有就返回默认值
	 * 
	 * @author zgr
	 * @创建时间：2018年10月12日 @param flag
	 * @param defaultValue
	 * @param params
	 * @return
	 */
	public final static String splicingStr(Boolean flag, String defaultValue, Object... params) {
		String result = "";
		if (flag) {
			for (Object str : params) {
				result += str;
			}
			if (!StringUtils.hasText(result) && StringUtils.hasText(defaultValue)) {
				result = defaultValue;
			}
		} else {
			if (StringUtils.hasText(defaultValue)) {
				result = defaultValue;
			}
		}
		return result;
	}

	/**
	 * 通过修改正则表达式实现校验负数，将正则表达式修改为“^-?[0-9]+”即可，修改为“-?[0-9]+.?[0-9]+”即可匹配所有数字。
	 */
	public final static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否有值
	 * 
	 * @author zgr
	 * @创建时间：2018年10月19日 @param str
	 * @return
	 */
	public final static boolean hasText(String str) {
		return org.springframework.util.StringUtils.hasText(str);
	}

	/**
	 * 获取字符串
	 * 
	 * @author zgr
	 * @创建时间：2018年10月22日 @param str
	 * @return
	 */
	public final static String getText(String str) {
		return (org.springframework.util.StringUtils.hasText(str) ? str : "");
	}

	/**
	 *  保留小数点有且2位
	 * @return
	 */
	public final static String formatDoubleDate(float msg){
		return formatDoubleDate(msg+"");
	}
	public final static String formatDoubleDate(String msg){
		String date = msg;
		if(!StringUtils.isNull(date)){
			Double cny = Double.parseDouble(date);//转换成Double
			DecimalFormat df = new DecimalFormat("0.00");//格式化
			date = df.format(cny);
		}else{
			date = "0.00";
		}
		return date;
	}

}
