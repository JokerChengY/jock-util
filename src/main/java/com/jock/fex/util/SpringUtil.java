package com.jock.fex.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @Description 类说明:使用ApplicationContextAware通过配置方式获取appContext
 *              和使用ApplicationListener容器初始化完成后执行某个方法
 *              （原有只是作为一个appContenxt的静态容器，现作了扩展通过配置注入方式和提供初初化处理）
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
		System.out.println("ApplicationContext配置成功");
		System.out.println("在普通类可以通过调用Utility.getAppContext()获取applicationContext对象,");
		System.out.println("applicationContext=" + SpringUtil.applicationContext + "========");
	}

	/** 获取applicationContext */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/** 通过name获取 Bean. */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/** 通过class获取Bean. */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/** 通过name,以及Clazz返回指定的Bean */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
}
