package com.jock.fex.util;

import java.util.HashMap;
import java.util.Map;

public class HttpClientUtils {

	/**
	 * 域名
	 */
	private final static String domain = "";// "http://10.10.1.55:10009";

	/**
	 * 默认参数
	 */
	private final static Map<String, String> defaultParam = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			// put("t", "t");
			// put("s", "s");
		}
	};

	public static void main(String[] args) {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("typeName", "资讯标题");
		map.put("pushPort", "0");
		map.put("token", "2C056D94-D653-4E2D-9B49-F77CFF04B163");

		// final String result = httpGetMethod("/coupon/select/all/enabled", map);
		// final String result = httpPostMethod("/coupon/add", map);
		// final String result = httpPutMethod("/policy/type/edit/20", map);
		final String result = httpDeleteMethod("/schoolmgr/app/v1/college/course/unEnroll/2", map);
		System.out.println(result);

	}

	/**
	 * get方式
	 *
	 * @param serverName
	 * @param uri
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public final static String httpGetMethod(String uri, Map<String, String> paramMap) {
		// 生成参数字符串
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		paramMap.putAll(defaultParam);
		final String param = createParam(paramMap);

		// 发送请求
		String result = HttpMethodUtils.sendGet(domain + uri, param);
		if (result == null || result.equals("")) {
			return "调用远程项目接口失败";
		}

		return result;
	}

	/**
	 * post方式
	 *
	 * @param serverName
	 * @param uri
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public final static String httpPostMethod(String uri, Map<String, String> paramMap) {
		// 生成参数字符串
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		paramMap.putAll(defaultParam);
		final String param = createParam(paramMap);

		// 发送请求
		String result = HttpMethodUtils.sendPost(domain + uri, param);
		if (result == null || result.equals("")) {
			return "调用远程项目接口失败";
		}

		return result;
	}

	/**
	 * put方式
	 *
	 * @param serverName
	 * @param uri
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public final static String httpPutMethod(String uri, Map<String, String> paramMap) {
		// 生成参数字符串
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		paramMap.putAll(defaultParam);
		final String param = createParam(paramMap);

		// 发送请求
		System.out.println("param:" + param);
		String result = HttpMethodUtils.sendPut(domain + uri, param);
		if (result == null || result.equals("")) {
			return "调用远程项目接口失败";
		}

		return result;
	}

	/**
	 * delete方式
	 *
	 * @param serverName
	 * @param uri
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public final static String httpDeleteMethod(String uri, Map<String, String> paramMap) {
		// 生成参数字符串
		if (paramMap == null) {
			paramMap = new HashMap<String, String>();
		}
		paramMap.putAll(defaultParam);
		final String param = createParam(paramMap);

		// 发送请求
		String result = HttpMethodUtils.sendDelete(domain + uri, param);
		if (result == null || result.equals("")) {
			return "调用远程项目接口失败";
		}

		return result;
	}

	/**
	 * 拼接参数
	 * 
	 * @param paramMap
	 * @return a=1&b=2&c=3
	 */
	public static final String createParam(Map<String, String> paramMap) {
		String param = "";
		if (paramMap != null && !paramMap.isEmpty()) {
			final StringBuffer buffer = new StringBuffer();
			for (String key : paramMap.keySet()) {
				buffer.append("&").append(key).append("=").append(paramMap.get(key));
			}
			param = buffer.toString().substring(1);
		}
		return param;
	}

	/**
	 * 拼接json参数
	 * 
	 * @param paramMap
	 * @return
	 */
	public static final String jsonStr(Map<String, String> paramMap) {
		String param = "";
		if (paramMap != null && !paramMap.isEmpty()) {
			final StringBuffer buffer = new StringBuffer();
			for (String key : paramMap.keySet()) {
				buffer.append(",'").append(key).append("':'").append(paramMap.get(key)).append("'");
			}
			param = buffer.toString().substring(1);
		}
		return "{" + param + "}";
	}
}
