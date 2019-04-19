package com.jock.fex.util.freemarker;

import com.jock.fex.util.SpringUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Map;

/**
 * Freemarker 工具类
 * 
 * @author 0
 *
 */
public class FreemarkerUtils {
	private static String charsetName = "UTF-8";

	/**
	 * 按模板生成文件输出到指定位置
	 * 
	 * @param ftlName
	 *            模板名称
	 * @param distFilePath
	 *            输出地址
	 * @param data
	 *            数据
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static void execute(String ftlName, String distFilePath, Map<String, Object> data)
			throws TemplateException, IOException {
		// 创建目录
		final File packageFile = new File(distFilePath).getParentFile();
		if ((null != packageFile) && (!packageFile.exists())) {
			packageFile.mkdirs();
		}
		// 创建模板
		final Template template = createTemplate(ftlName);

		// 输出
		final Writer out = new OutputStreamWriter(new FileOutputStream(distFilePath), charsetName);

		template.process(data, out);
		out.flush();
		out.close();
	}

	/**
	 * 按模板生成文件输出到指定位置
	 * 
	 * @param ftlName
	 *            模板名称
	 * @param data
	 *            数据
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static String execute(String ftlName, Map<String, Object> data) throws TemplateException, IOException {
		StringWriter sw = new StringWriter();
		Template template = createTemplate(ftlName);
		template.process(data, sw);
		return sw.toString();
	}

	/**
	 * 创建模板
	 * 
	 * @param ftlName
	 * @return
	 * @throws IOException
	 */
	public static Template createTemplate(String ftlName) throws IOException {
		final FreeMarkerConfigurer config = (FreeMarkerConfigurer) SpringUtil.getBean("freeMarkerConfigurer");
		final Template template = config.getConfiguration().getTemplate(ftlName);
		return template;
	}
}