package com.jock.fex.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量工具
 * 
 * @author 0
 *
 */
public class ConstUtil {

	/**
	 * 周中文常量
	 */
	public final static Map<Integer, String> WEEK = new HashMap<Integer, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(1, "星期天");
			put(2, "星期一");
			put(3, "星期二");
			put(4, "星期三");
			put(5, "星期四");
			put(6, "星期五");
			put(7, "星期六");
		}
	};
}
