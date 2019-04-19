package com.jock.fex.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Tong on 2016/8/1.
 */
public class MyFileUtils {

    /**
     * 获取服务器上的/获取服务器图片的尺寸
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void getImg() throws FileNotFoundException, IOException {

        URL url = new URL("http://jinrongqiao.oss-cn-shenzhen.aliyuncs.com/resourcesTemplateUrl/2016/08/01/87fd89e44f52f83c37d38b1f9994f770.jpg");
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        BufferedImage image = ImageIO.read(connection.getInputStream());
        int srcWidth = image.getWidth();      // 源图宽度
        int srcHeight = image.getHeight();    // 源图高度

        //System.out.println("srcWidth = " + srcWidth);
        //System.out.println("srcHeight = " + srcHeight);

    }

    /**
     * 获取服务器上的/获取服务器图片的尺寸
     *
     * @throws IOException
     */
    public void testImg1() throws IOException {
        InputStream murl = new URL("http://jinrongqiao.oss-cn-shenzhen.aliyuncs.com/resourcesTemplateUrl/2016/08/01/87fd89e44f52f83c37d38b1f9994f770.jpg").openStream();
        BufferedImage sourceImg = ImageIO.read(murl);
        //System.out.println(sourceImg.getWidth());   // 源图宽度
        //System.out.println(sourceImg.getHeight());   // 源图高度
    }

    /**
     * 本地获取/获取服务器图片的尺寸
     *
     * @param outFileUrl
     * @return
     * @throws IOException
     */
    public static long imgSize(String outFileUrl) throws IOException {
        File picture = new File(outFileUrl);
        //BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
        //System.out.println(sourceImg.getWidth()); // 源图宽度
        //System.out.println(sourceImg.getHeight()); // 源图高度
        //long size = Long.parseLong(String.format("%.1f", picture.length() / 1024.0));// 源图大小
        long size = picture.length();
        return size;
    }

    /**
     * 文件名
     */
    public static String fileName(String outFileUrl) {
        String fName = outFileUrl;
        File tempFile = new File(fName.trim());
        String fileName = tempFile.getName();//文件名
        //String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);// 后缀名
        return fileName;
    }

}
