package com.jock.fex.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 2019年4月15日<br>
 * 请求工具类
 * 
 * @author 0
 *
 */
public class RequestUtil {

	/**
	 * 获取请求参数值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getVal(HttpServletRequest request, String name) {
		String value = request.getHeader(name);
		if (StringUtils.isEmpty(value)) {
			value = request.getParameter(name);
		}
		return value;
	}

}
