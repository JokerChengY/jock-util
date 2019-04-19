package com.jock.fex.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangcs
 * @version V1.0
 * @since 2017年11月15日
 * @Description 类说明:(描述)
 */
public final class BeanPropertyUtil {

	/**
	 * 根据对象List获取相应的属性成员值集合
	 * 
	 * @param objList
	 *            实体对象集合
	 * @param propName
	 *            获取的属性名称
	 * @return {@link List} 值列表
	 * 
	 */
	public static <T> List<T> getPropListByBeanList(List<?> objList, String propName) {
		propName = null == propName ? "" : propName;
		List<T> valList = new ArrayList<T>();
		if (null == objList) {
			return valList;
		}
		try {
			for (Object object : objList) {
				if (null != object) {
					@SuppressWarnings("unchecked")
					T value = (T) PropertyUtils.getProperty(object, propName);
					if (null != value) {
						if (value instanceof String && ((String) value).indexOf(",") >= 0) {
							String[] arr = ((String) value).split(",");
							for (String str : arr) {
								valList.add((T) str);
							}
							continue;
						}
						valList.add(value);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valList;
	}

	/** 根据对象List获转Map */
	public static <T> Map<String, T> getMapByList(List<T> list, String propName) {
		Map<String, T> resultMap = new HashMap<String, T>();
		if (null == list || list.isEmpty()) {
			return resultMap;
		}
		try {
			for (T model : list) {
				String value;
				value = (String) PropertyUtils.getProperty(model, propName);
				resultMap.put(value, model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	/** 根据对象List获转Map */
	public static <T> Map<String, List<T>> getMapsByList(List<T> list, String propName) {
		Map<String, List<T>> resultMap = new HashMap<String, List<T>>();
		if (null == list || list.isEmpty()) {
			return resultMap;
		}
		try {
			for (T model : list) {
				Object obj = PropertyUtils.getProperty(model, propName);
				String value = obj == null ? null : String.valueOf(obj);
				if (resultMap.get(value) != null) {
					resultMap.get(value).add(model);
				} else {
					List<T> l = new ArrayList<T>();
					l.add(model);
					resultMap.put(value, l);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultMap;
	}

	/** 比对同类两个对象的属性值，是不是相等，相等的两个属性值设置为null,不等时保留原有各自的值 */
	public static void contrastProperty(Object obj1, Object obj2, Class clazz) {
		try {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
				Method getMethod = pd.getReadMethod();
				Object o1 = getMethod.invoke(obj1);
				Object o2 = getMethod.invoke(obj2);
				if (o1 != null && o2 != null) {
					if (o1.equals(o2)) {
						field.setAccessible(true);
						field.set(obj1, null);
						field.set(obj2, null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** map 转对象 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null) {
			return null;
		}
		Object obj = beanClass.newInstance();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}

				field.setAccessible(true);
				field.set(obj, map.get(field.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}

	/** 对象 转map */
	public static Map<String, Object> objectToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Class<?> objClass = obj.getClass();
			while (objClass != null) {
				Field[] declaredFields = objClass.getDeclaredFields();
				for (Field field : declaredFields) {
					field.setAccessible(true);
					map.put(field.getName(), field.get(obj));
				}
				objClass = objClass.getSuperclass();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 对象转map（不包含父类的属性,不包含没有get方法的）
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> objectToMapSelfProp(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Class<?> cls = obj.getClass();
			Method[] methods = cls.getDeclaredMethods();
			Field[] fields = cls.getDeclaredFields();

			for (Field field : fields) {
				String fieldName = field.getName();
				String fieldGetMetName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				boolean flag = false;
				for (Method met : methods) {
					if (fieldGetMetName.equals(met.getName())) {
						flag = true;
					}
				}
				if (!flag) {
					continue;
				}
				Method fieldGetMet = cls.getMethod(fieldGetMetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(obj, new Object[] {});

				map.put(fieldName, fieldVal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
