package com.jock.fex.util;

/**
 * 获取随机数，可以单数字，或者单字母，或者组合
 *
 */
public class RamdomUtils {

	/**
	 * 大写字母
	 */
	final static String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * 小写字母
	 */
	final static String lowercase = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * 数字
	 */
	final static String number = "1234567890";

	public static void main(String[] args) {
		System.out.println(getLetterNumber(10));
	}

	/**
	 * 根据参数获取不同位数的随机数
	 * 
	 * @param count
	 *            随机数位数，必须大于0小于20
	 * @return String
	 */
	public final static String getRamdom(int count) {
		Double d = Math.random();
		if (count > 0) {
			d = d * Math.pow(10, count);
		} else {
			count = 1;
		}
		final long l = d.longValue();
		final String format = new StringBuffer("%0").append(count).append("d").toString();
		return String.format(format, l);
	}

	/**
	 * 根据参数获取不同位数的随机大写字母数
	 * 
	 * @return char
	 */
	public final static String getLetter(int count) {
		String r = "";
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				r += String.valueOf(uppercase.charAt((int) (Math.random() * 26)));
			}
		} else {
			r = String.valueOf(uppercase.charAt((int) (Math.random() * 26)));
		}
		return r;
	}

	/**
	 * 获取8位随机数，并且前两位是大写字母
	 * 
	 * @return String
	 */
	public final static String getRamdom() {
		return getLetter(2) + getRamdom(8);
	}

	/**
	 * 根据参数获取不同位数的随机字母数字
	 * 
	 * @param count
	 * @return
	 */
	public final static String getLetterNumber(int count) {
		final String letterNumber = uppercase + number + lowercase;
		String r = "";
		if (count > 0) {
			for (int i = 0; i < count; i++) {
				r += String.valueOf(letterNumber.charAt((int) (Math.random() * 62)));
			}
		} else {
			r = String.valueOf(letterNumber.charAt((int) (Math.random() * 62)));
		}
		return r;
	}
}
