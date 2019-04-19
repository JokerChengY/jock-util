package com.jock.fex.util;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ListUtil {

	public static List<Long> typeChange(Collection<?> e) {
		if (e != null && !e.isEmpty()) {
			List<Long> list = new ArrayList<Long>();
			for (Object object : e) {
				if (object != null) {
					list.add(Long.parseLong(object.toString()));
				}
			}
			return list;
		}
		return null;
	}

	/**
	 * 空值 则返回 空List数组
	 *
	 * @param obj
	 * @return
	 */
	public static Object objectIsNullToList(Object obj) {
		obj = obj == null || obj == "" ? new ArrayList<Object>() : obj;
		return obj;
	}

	/**
	 * 空值 则返回 空Map
	 *
	 * @param obj
	 * @return
	 */
	public static Object objectIsNullToMap(Object obj) {
		obj = obj == null || obj == "" ? new HashMap<String, Object>() : obj;
		return obj;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 *
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	public static Map<String, Object> convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if (bean != null) {
			Class type = bean.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					Object result = readMethod.invoke(bean, new Object[0]);
					if (result != null) {
						if ("realityEndDate".equals(propertyName)) {
							Date result_date = (Date) readMethod.invoke(bean, new Object[0]);
							// returnMap.put(propertyName, DateUtils.formatDateTime(result_date));
						} else {
							returnMap.put(propertyName, result);
						}
					}
					/*
					 * else { returnMap.put(propertyName, ""); }
					 */
				}
			}
		}
		return returnMap;
	}

	/**
	 * 处理图片路径
	 *
	 * @param str
	 * @param accessUrl
	 * @return
	 */
	public static String dealImgUrl(String str, String accessUrl) {
		if (!StringUtils.isEmpty(str)) {
			str = accessUrl + str;
		}
		return str;
	}

	/**
	 * 字符串转换list
	 * 
	 * @param obj
	 * @return
	 */
	public static List<String> stringToList(String obj) {
		List<String> orderNumberList = new ArrayList<String>();
		if (!StringUtils.isEmpty(obj)) {
			if (obj.contains(",")) {
				String[] objStr = obj.split(",");
				for (int i = 0; i < objStr.length; i++) {
					String str = objStr[i];
					orderNumberList.add(str);
				}
			} else {
				orderNumberList.add(obj);
			}
		}
		return orderNumberList;
	}

	/**
	 * 删除ArrayList中重复元素，保持顺序
	 *
	 * @param list
	 */
	public static List removeDuplicateWithOrder(List list) {
		if (list != null) {
			if (list.size() > 0) {
				Set set = new HashSet();
				List newList = new ArrayList();
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Object element = iter.next();
					if (set.add(element))
						newList.add(element);
				}
				list.clear();
				list.addAll(newList);
			}
		}

		System.out.println(" removeDuplicateWithOrder = list=== " + list);
		return list;
	}

	/**
	 * 判断 字符是否 包含在 List里
	 *
	 * @param list
	 * @param item
	 * @return
	 */
	public static Boolean containsList(List<?> list, String item) {
		if (list != null) {
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Long id = (Long) list.get(i);
					if (item.equals(id.toString())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * list 截取固定条数
	 * 
	 * @param list
	 * @param skip
	 * @param pageSize
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> getSubListPage(List<T> list, int skip, int pageSize) {
		if (list == null || list.isEmpty()) {
			return null;
		}
		int startIndex = skip * pageSize;
		int endIndex = (skip + 1) * pageSize;
		if (startIndex > endIndex || startIndex > list.size()) {
			return null;
		}
		if (endIndex > list.size()) {
			endIndex = list.size();
		}
		return list.subList(startIndex, endIndex);
	}

	/**
	 * list 转 string
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<?> list) {
		String result = "";
		if (!CollectionUtils.isEmpty(list)) {
			for (Object obj : list) {
				if (obj != null) {
					result += "," + obj.toString();
				}
			}
			if (!StringUtils.isEmpty(result)) {
				result = result.substring(1);
			}
		}

		return result;
	}

	/**
	 * list 转 string，分隔符默认英文逗号
	 * 
	 * @param list
	 *            map集合
	 * @param key
	 *            map key name
	 * @return
	 */
	public static String listToString(List<? extends Map<? extends Object, Object>> list, Object key) {
		return listToString(list, key, ",");
	}

	/**
	 * list 转 string
	 * 
	 * @param list
	 *            map集合
	 * @param key
	 *            map key name
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String listToString(List<? extends Map<? extends Object, Object>> list, Object key,
			String separator) {
		String result = "";
		if (!CollectionUtils.isEmpty(list)) {
			for (Map<? extends Object, Object> obj : list) {
				if (obj != null) {
					result += separator + obj.get(key);
				}
			}
			if (!StringUtils.isEmpty(result) && !StringUtils.isEmpty(separator)) {
				result = result.substring(separator.length());
			}
		}

		return result;
	}

	/**
	 * 2018年8月11日<br>
	 * 集合某个字段转字符串
	 * 
	 * @param list
	 * @param key
	 * @return
	 */
	public static String listFieldToString(List<?> list, String key) {
		return listFieldToString(list, key, ",");
	}

	/**
	 * 2018年8月11日<br>
	 * 集合某个字段转字符串
	 * 
	 * @param list
	 * @param key
	 * @param separator
	 * @return
	 */
	public static String listFieldToString(List<?> list, String key, String separator) {
		String result = "";
		try {
			if (!CollectionUtils.isEmpty(list)) {
				for (Object obj : list) {
					final Method m = getGetMethod(obj.getClass(), key, "get");
					if (m != null) {
						result += separator + m.invoke(obj, new Object[0]);
					}
				}
				if (!StringUtils.isEmpty(result) && !StringUtils.isEmpty(separator)) {
					result = result.substring(separator.length());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 2018年8月11日<br>
	 * 集合某个字段重新组合成集合
	 * 
	 * @param list
	 * @param key
	 * @param separator
	 * @return
	 */
	public static List<String> listToList(List<?> list, String key) {
		List<String> result = new ArrayList<String>();
		try {
			if (!CollectionUtils.isEmpty(list)) {
				for (Object obj : list) {
					final Method m = getGetMethod(obj.getClass(), key);
					final Object o = m != null ? m.invoke(obj, new Object[0]) : null;
					result.add(o != null ? o.toString() : "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 2018年8月11日<br>
	 * 反射方法
	 * 
	 * @param obj
	 * @param key
	 * @return
	 */
	@Deprecated
	public static Method getGetMethod(Class<?> obj, String key, String getSet) {
		StringBuffer sb = new StringBuffer();
		sb.append(getSet).append(key.substring(0, 1).toUpperCase()).append(key.substring(1));
		try {
			return obj.getMethod(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2018年8月11日<br>
	 * 反射方法-获取get方法
	 * 
	 * @param obj
	 * @param key
	 * @return
	 */
	public static Method getGetMethod(Class<?> obj, String key) {
		StringBuffer sb = new StringBuffer();
		sb.append("get").append(key.substring(0, 1).toUpperCase()).append(key.substring(1));
		try {
			return obj.getMethod(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2018年8月11日<br>
	 * 反射方法-获取set方法
	 * 
	 * @param obj
	 * @param key
	 * @return
	 */
	public static Method getSetMethod(Class<?> obj, String key, Class<?> typeClass) {
		StringBuffer sb = new StringBuffer();
		sb.append("set").append(key.substring(0, 1).toUpperCase()).append(key.substring(1));
		try {
			return obj.getMethod(sb.toString(), new Class[] { typeClass });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
