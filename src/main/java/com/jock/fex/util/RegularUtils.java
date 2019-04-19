package com.jock.fex.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegularUtils {
	/**
	 * 验证Email
	 * 
	 * @param email
	 *            email地址，格式：zhang@gmail.com，zhang@xxx.com.cn，xxx代表邮件服务商
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
	 * 
	 * @param mobile
	 *            移动、联通、电信运营商的号码段 移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
	 *            、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）
	 *            联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
	 *            电信的号段：133、153、180（未启用）、189
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 验证固定电话号码
	 * 
	 * @param phone
	 *            电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447 国家（地区） 代码
	 *            ：标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
	 *            区号（城市代码）：这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
	 *            对不使用地区或城市代码的国家（地区），则省略该组件。 电话号码：这包含从 0 到 9 的一个或多个数字
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPhone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * 验证整数（正整数和负整数）
	 * 
	 * @param digit
	 *            一位或多位0-9之间的整数
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * 验证整数和浮点数（正负整数和正负浮点数）
	 * 
	 * @param decimals
	 *            一位或多位0-9之间的浮点数，如：1.23，233.30
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * 验证空白字符
	 * 
	 * @param blankSpace
	 *            空白字符，包括：空格、\t、\n、\r、\f、\x0B
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * 验证中文
	 * 
	 * @param chinese
	 *            中文字符
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * 验证日期（年月日）
	 * 
	 * @param birthday
	 *            日期，格式：1992-09-03，或1992.09.03
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * 验证URL地址
	 * 
	 * @param url
	 *            格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
	 *            http://www.csdn.net:80
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkURL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * * 获取网址 URL 的一级域名
	 * http://detail.tmall.com/item.htm?spm=a230r.1.10.44.1xpDSH&id=15453106243&_u=f4ve1uq1092
	 * ->> tmall.com
	 *
	 *
	 * @param url
	 * @return
	 */
	public static String getDomain(String url) {
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
				Pattern.CASE_INSENSITIVE);
		// 获取完整的域名
		// Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)",
		// Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}

	/**
	 * 匹配中国邮政编码
	 * 
	 * @param postcode
	 *            邮政编码
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
	 * 
	 * @param ipAddress
	 *            IPv4标准地址
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	// 是否包含 . 号
	public static boolean checkContainsDot(String username) {
		return username.contains(".");
	}

	// 是否包含连词符
	public static boolean checkContainsHyphen(String username) {
		return username.contains("-");
	}

	// 密码长度 6-20
	public static boolean checkUserPasswordLength(String pwd) {
		return pwd.length() > 5 && pwd.length() < 21;
	}

	public static boolean isValidUserName(String un) {
		String regex = "([A-Z0-9a-z-]|[\\u4e00-\\u9fa5])+";
		return Pattern.matches(regex, un);
	}

	/**
	 * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
	 * 17+除9的任意数 147
	 */
	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		// ^ 匹配输入字符串开始的位置
		// \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
		// $ 匹配输入字符串结尾的位置
		String regExp = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 香港手机号码8位数，5|6|8|9开头+7位任意数
	 */
	public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
		// ^ 匹配输入字符串开始的位置
		// \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
		// $ 匹配输入字符串结尾的位置
		String regExp = "^(5|6|8|9)\\d{7}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 电话号码验证
	 *
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) throws PatternSyntaxException {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 大陆号码或香港号码均可
	 */
	public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
		return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	}

}
