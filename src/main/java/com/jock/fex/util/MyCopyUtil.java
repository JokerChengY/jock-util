package com.jock.fex.util;

import org.apache.commons.beanutils.*;
import org.apache.commons.lang.ArrayUtils;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @author yangcs
 * @Description 类说明：对象属性拷贝操作工具
 */
public class MyCopyUtil {

	/**
	 * 拷贝共有属性到目录对象 源对象属性为null则不拷贝到目标属性，目标属性保持原值
	 * 
	 * @param tag
	 *            拷贝至目标对象
	 * @param src
	 *            拷贝的源对象
	 * 
	 */
	public static void copyProp(Object tag, Object src) {
		copyProp(tag, src, null);
	}

	/**
	 * 拷贝共有属性到目录对象 源对象属性为null则不拷贝到目标属性，目标属性保持原值
	 * 
	 * @param tag
	 *            拷贝至目标对象
	 * @param src
	 *            拷贝的源对象
	 * @param noprops
	 *            目标对象中无需拷贝的属性
	 */
	public static void copyProp(Object tag, Object src, String[] noprops) {
		noprops = noprops == null ? new String[] { "class" } : noprops;
		if (null == tag || null == src) {
			return;
		}
		PropertyDescriptor[] tagPds = PropertyUtils.getPropertyDescriptors(tag);
		PropertyDescriptor[] srcPds = PropertyUtils.getPropertyDescriptors(src);
		String[] srcPdsName = new String[srcPds.length];
		for (int i = 0; i < srcPds.length; i++) {
			srcPdsName[i] = srcPds[i].getName();
		}
		// 迭代目标对象
		for (PropertyDescriptor tagPd : tagPds) {
			if (ArrayUtils.indexOf(noprops, tagPd.getName()) >= 0) {
				continue;
			}
			if (!(ArrayUtils.indexOf(srcPdsName, tagPd.getName()) >= 0)) {
				continue;
			}
			try {
				if (tagPd.getWriteMethod() == null) // 无setter方法的属性无需拷
					continue;
				else if (PropertyUtils.getProperty(src, tagPd.getName()) == null) // src的属性值为空时不用拷
					continue;
			} catch (Exception e) {
				continue;
			}
			// 拷属性
			try {
				PropertyUtils.setProperty(tag, tagPd.getName(), PropertyUtils.getProperty(src, tagPd.getName()));
			} catch (Exception e) {
				throw new RuntimeException("拷贝属性" + tagPd.getName() + "不成功！", e);
			}
		}
	}

	/** 把可序列化对象深复制把要复制的对象所引用的对象都复制了一遍(关键：执行序列化和反序列化来进行深度拷贝) */
	public static <T extends Serializable> List<T> deepCopy(List<T> src) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		List<T> dest = null;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(src);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			List<T> destTag = (List<T>) in.readObject();
			dest = destTag;
		} catch (IOException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		}
		return dest;
	}

	/**
	 * 提供类型转换功能的拷贝，即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换 源对象覆盖目标对象
	 */
	public static void copyProperties(Object dest, Object orig) {
		if (orig == null) {
			return;
		}
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		}
	}

	/**
	 * 将对象属性拷贝到某个类，不格式化任何数据
	 * 
	 * @param destCalss
	 *            生成的类
	 * @param origObj
	 *            目标对象
	 */
	public static <T> T copyProperties(Class<T> destCalss, Object origObj) {
		T result = null;
		try {
			result = destCalss.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		copyProperties(result, origObj);
		return result;
	}

	/**
	 * 执行某个类的静态方法 java反射机制
	 */
	public static Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception {
		Class<?> ownerClass = Class.forName(className);
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = null;
		try {
			method = ownerClass.getMethod(methodName, argsClass);
		} catch (NoSuchMethodException e) {
			// e.printStackTrace();
		}
		if (method != null) {
			return method.invoke(null, args);
		} else {
			return null;
		}
	}

	// 兼容性的拷贝处理日期属性不同问题
	static {
		ConvertUtils.register(new DateConvert(), java.util.Date.class);
		ConvertUtils.register(new DateConvert(), java.sql.Date.class);
	}

	/** 将对象属性拷贝到某个对象，兼容性的拷贝处理日期属性不同问题,日期默认会格式化为字符串 */
	public static void copyPropByWrap(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 将对象属性拷贝到某个类，兼容性的拷贝处理日期属性不同问题,日期默认会格式化为字符串
	 * 
	 * @param destCalss
	 *            生成的类
	 * @param origObj
	 *            目标对象
	 */
	public static <T> T copyPropByWrap(Class<T> destCalss, Object origObj) {
		T result = null;
		if (null == origObj) {
			return null;
		}
		try {
			result = destCalss.newInstance();
			BeanUtils.copyProperties(result, origObj);
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		if (null == obj) {
			return params;
		}
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public static <T> List<T> copyList(Class<T> destCalss, List<? extends Object> src){
		if(null == src){
			return null;
		}
		List<T> list = new ArrayList<T>();
		T tmp;
		for(Object obj : src){
			tmp = MyCopyUtil.copyPropByWrap(destCalss, obj);
			list.add(tmp);
		}
		return list;
	}

/*	public static <T> PageBean<T> copyPageBean(Class<T> destCalss,PageBean<? extends Object> src) {
		if (null == src) {
			return null;
		}
		PageBean<T> page = new PageBean<T>();
		MyCopyUtil.copyProp(page, src, new String[] {"resultList"});
		List<T> acctountList = new ArrayList<T>();
		T t = null;
		for (Object account : src.getResultList()) {
			t = MyCopyUtil.copyPropByWrap(destCalss, account);
			acctountList.add(t);
		}
		page.setResultList(acctountList);
		return page;
	}*/
}

class DateConvert implements Converter {
	/** 年月日 */
	public static SimpleDateFormat dateFormatV1 = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat dateFormatV2 = new SimpleDateFormat("yyyy.MM.dd");
	public static SimpleDateFormat dateFormatV3 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateFormatV4 = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat dateFormatV5 = new SimpleDateFormat("yyyy年MM月dd日");
	public static SimpleDateFormat dateFormatV6 = new SimpleDateFormat("yyyyMM");
	/** 年月日时分秒 */
	public static SimpleDateFormat dateTimeFormatV1 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat dateTimeFormatV2 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	public static SimpleDateFormat dateTimeFormatV3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat dateTimeFormatV4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static SimpleDateFormat dateTimeFormatV5 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	/** 年月 */
	public static SimpleDateFormat yearMothFormatV1 = new SimpleDateFormat("yyyy年MM月");
	public static SimpleDateFormat yearMothFormatV2 = new SimpleDateFormat("yyyy.MM");
	public static SimpleDateFormat yearMothFormatV3 = new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat yearMothFormatV4 = new SimpleDateFormat("yyyy/MM");

	/** 年月日时分 */
	public static SimpleDateFormat dateHourTimeFormatV1 = new SimpleDateFormat("yyyyMMddHHmm");
	public static SimpleDateFormat dateHourTimeFormatV2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
	public static SimpleDateFormat dateHourTimeFormatV3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat dateHourTimeFormatV4 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	public static SimpleDateFormat dateHourTimeFormatV5 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

	public Object convert(@SuppressWarnings("rawtypes") Class arg0, Object arg1) {
		try {
			if (arg1 == null) {
				return null;
			}
			// 如果arg1已经是日期类型不需要转换
			if (arg1 instanceof java.util.Date || arg1 instanceof java.sql.Date) {
				return arg1;
			}
			String p = (String) arg1;
			switch (p.trim().length()) {// 转用正则优化TD
			case 0:
				return null;
			case 7:
				return yearMothFormatV3.parse(p.trim());
			case 8:
				return dateFormatV1.parse(p.trim());
			case 10:
				return dateFormatV3.parse(p.trim());
			case 14:
				return dateTimeFormatV1.parse(p.trim());
			case 19:
				return dateTimeFormatV3.parse(p.trim());
			default:
				return arg1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return arg1;
		}
	}

}
