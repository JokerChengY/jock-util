package com.jock.fex.util.freemarker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class FreemarkerConfig {

	/**
	 * freeMarker配置
	 * 
	 * @return
	 */
	@Bean(value = "freeMarkerConfigurer")
	public FreeMarkerConfigurer pageInterceptor() {
		final FreeMarkerConfigurer config = new FreeMarkerConfigurer();

		// 模板目录
		config.setTemplateLoaderPath("classpath:/templates/freemarker/");
		config.setDefaultEncoding("utf-8");

		// 配置属性
		final Properties prop = new Properties();
		prop.put("template_update_delay", "1");
		prop.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
		prop.put("date_format", "yyyy-MM-dd");
		prop.put("time_format", "HH:mm:ss");
		prop.put("number_format", "#.##");
		prop.put("locale", "zh-CN");
		prop.put("whitespace_stripping", "true");
		prop.put("default_encoding", "UTF-8");
		prop.put("output_encoding", "UTF-8");
		prop.put("tag_syntax", "auto_detect");
		prop.put("url_escaping_charset", "UTF-8");
		prop.put("classic_compatible", "true");
		prop.put("object_wrapper", "freemarker.template.DefaultObjectWrapper");
		config.setFreemarkerSettings(prop);

		// 配置变量
		final Map<String, Object> param = new HashMap<String, Object>();
		config.setFreemarkerVariables(param);

		return config;
	}
}
