package com.jock.fex.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/7/12.
 */
public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

	private static String fileImgFolder = "/data/web/lcedai/fileUpload/img/";

	/**
	 * 文件上传
	 *
	 * @param file
	 *            文件对象
	 * @param folderPath
	 *            存放文件夹
	 * @param fileName
	 *            文件名称（如helloworld.jpg），默认用时间戳
	 * @return 文件服务器存放路径
	 */
	public static File fileUpload(MultipartFile file, String folderPath, String fileName) {
		/**
		 * 1在服务器创建一新文件 a.若文件名称是空则用时间戳代替 b.从配置文件中获取服务器路径 c.若指定存放文件夹则加入到路径上
		 */
		if (StringUtils.isEmpty(fileName)) {
			// 获取文件名称
			fileName = file.getOriginalFilename();
			// 替换时间戳.文件类型
			fileName = System.currentTimeMillis() + fileName.substring(fileName.lastIndexOf("."));
		}
		// 获取文件服务器路径
		String filePath = fileImgFolder;
		if (!StringUtils.isEmpty(folderPath)) {
			// 路径后加入文件夹
			filePath = filePath + folderPath;
		}
		// 生成文件
		File targetFile = new File(filePath, fileName);

		// 文件不存在则创建
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		/**
		 * 2.文件传输并返回文件在服务器的部署路径
		 */
		try {
			// 文件传输
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return targetFile;
	}

	/**
	 * 文件上传
	 *
	 * @param file
	 *            文件对象
	 * @param folderPath
	 *            存放文件夹
	 * @param fileName
	 *            文件名称（如helloworld.jpg），默认用时间戳
	 * @return 文件服务器存放路径
	 */
	public static File fileUpload(byte[] file, String folderPath, String fileName, String fileType) throws IOException {
		/**
		 * 1在服务器创建一新文件 a.若文件名称是空则用时间戳代替 b.从配置文件中获取服务器路径 c.若指定存放文件夹则加入到路径上
		 */
		if (StringUtils.isEmpty(fileName)) {
			// 替换时间戳.文件类型
			fileName = System.currentTimeMillis() + "." + fileType;
		} else {
			fileName = fileName + "." + fileType;
		}
		// 获取文件服务器路径
		String filePath = fileImgFolder;
		if (!StringUtils.isEmpty(folderPath)) {
			// 路径后加入文件夹
			filePath = filePath + folderPath;
		}
		logger.error(filePath + "---" + fileName);
		// 生成文件
		File targetFile = new File(filePath, fileName);

		// 文件不存在则创建
		if (!targetFile.exists()) {
			targetFile.createNewFile();
		}
		org.apache.commons.io.FileUtils.writeByteArrayToFile(targetFile, file);
		return targetFile;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return Example: jpg
	 */
	public static String getFileExt(String fileName) {
		if (fileName != null && fileName.length() > 0) {
			final int count = fileName.lastIndexOf(".");
			if (count > 0) {
				return fileName.substring(count + 1);
			}
		}
		return "";
	}

	/**
	 * 文件大小格式成单位
	 * 
	 * @param size
	 * @return
	 */
	public static String sizeFormat(long size) {
		final DecimalFormat df = new DecimalFormat("#.00");
		if (size > Math.pow(2, 50)) {
			double d = size / Math.pow(2, 50);
			return df.format(d) + "EB";
		} else if (size > Math.pow(2, 40)) {
			double d = size / Math.pow(2, 40);
			return df.format(d) + "TB";
		} else if (size > Math.pow(2, 30)) {
			double d = size / Math.pow(2, 30);
			return df.format(d) + "GB";
		} else if (size > Math.pow(2, 20)) {
			double d = size / Math.pow(2, 20);
			return df.format(d) + "MB";
		} else if (size > Math.pow(2, 10)) {
			double d = size / Math.pow(2, 10);
			return df.format(d) + "KB";
		} else {
			return size + "B";
		}
	}

	/**
	 * spring boot 读取文件内容
	 * 
	 * @param path
	 * @return
	 */
	public static String readFile(String path) {

		InputStreamReader reader = null;
		BufferedReader bfReader = null;
		final StringBuilder builder = new StringBuilder();
		try {
			// 文件初始化
			final File file = ResourceUtils.getFile(path);
			logger.info("rootPath:" + file.getAbsolutePath());

			// 文件流
			reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			bfReader = new BufferedReader(reader);

			// 行读取
			String tmpContent = null;
			while ((tmpContent = bfReader.readLine()) != null) {
				builder.append(tmpContent);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (bfReader != null) {
					bfReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return builder.toString();
	}

	/**
	 * 过滤注释内容
	 * 
	 * @param input
	 * @return
	 */
	public static String filter(String input) {
		return input.replaceAll("/\\*[\\s\\S]*?\\*/", "");
	}

	/**
	 * 2018年11月30日<br>
	 * 通过文件名获取http请求内容类型
	 * 
	 * @param fileExt
	 *            文件扩展名：jpg
	 * @return
	 */
	public final static String contentType(String fileName) {
		final String fileN = fileName == null ? "" : fileName.toLowerCase();

		String type = "";
		try {
			type = Files.probeContentType(Paths.get(fileN));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(type)) {
			System.out.println("fileName : " + fileN);

			switch (getFileExt(fileN)) {
			case "bmp":
				return "image/bmp";
			case "gif":
				return "image/gif";
			case "jpeg":
				return "image/jpeg";
			case "jpg":
				return "image/jpeg";
			case "png":
				return "image/jpeg";
			case "html":
				return "text/html";
			case "txt":
				return "text/plain";
			case "vsd":
				return "application/vnd.visio";
			case "pptx":
				return "application/vnd.ms-powerpoint";
			case "ppt":
				return "application/vnd.ms-powerpoint";
			case "docx":
				return "application/vnd.ms-powerpoint";
			case "doc":
				return "application/msword";
			case "xml":
				return "text/xml";
			case "mp4":
				return "video/mp4";
			default:
				return "application/octet-stream";
			}
		}

		return type;
	}

}
