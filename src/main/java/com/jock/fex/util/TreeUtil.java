package com.jock.fex.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeUtil {

	/**
	 * 列表转成tree列表
	 * 
	 * @param list<T>
	 * String...args
	 * args[0] 指定id字段名，默认为"id"
	 * args[1] 指定父级id字段名，默认为"parentId"
	 * args[2] 指定子列表属性字段名，默认为"children"
	 * @return
	 */
	public static <T> List<T> listByTree(List<T> list,String...args) {
		String idName = "id";
		String parentIdName = "parentId";
		String childrenName = "children";
		if(args.length==1) {
			idName = args[0];
		}else if(args.length==2) {
			idName = args[0];
			parentIdName = args[1];
		}else if(args.length>2) {
			idName = args[0];
			parentIdName = args[1];
			childrenName = args[2];
		}
		try {
			Map<String, List<T>> map = BeanPropertyUtil.getMapsByList(list, parentIdName);
			Iterator<T> it = list.iterator();
			while (it.hasNext()) {
				T obj = it.next();
				Object o = PropertyUtils.getProperty(obj, parentIdName);
				String parentId = o == null ? null : String.valueOf(o);
				if (parentId == null || "0".equals(parentId)) {
					Object o2 = PropertyUtils.getProperty(obj, idName);
					String id = o2 == null ? null : String.valueOf(o2);
					List<T> children = map.get(id);
					if (children != null && children.size() > 0) {
						PropertyUtils.setProperty(obj, childrenName, children);
						createChildrenTree(children, map,idName,childrenName);
					}
				} else {
					it.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private static <T> void createChildrenTree(List<T> list, Map<String, List<T>> map,String idName,String childrenName) throws Exception {
		for (T obj : list) {
			Object o = PropertyUtils.getProperty(obj, idName);
			String id = o == null ? null : String.valueOf(o);
			List<T> children = map.get(id);
			if (children != null && children.size() > 0) {
				PropertyUtils.setProperty(obj, childrenName, children);
				createChildrenTree(children, map,idName,childrenName);
			}
		}
	}
}
