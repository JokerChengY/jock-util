package com.jock.fex.util.encryption;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http 工具类
 * Created by Lj on 2017/6/8.
 */
public class HttpUtil {

    /**
     * http Get 请求
     *
     * @param url         请求url
     * @param contentType 内容类型
     * @param data        请求数据
     */
    public static byte[] httpGetRequest(String url, HttpContentType contentType, String data) throws IOException {
        return httpRequest(url, "GET", contentType, "UTF-8", data);
    }

    /**
     * http Post 请求
     *
     * @param url         请求url
     * @param contentType 内容类型
     * @param data        请求数据
     */
    public static byte[] httpPostRequest(String url, HttpContentType contentType, String data) throws IOException {
        return httpRequest(url, "POST", contentType, "UTF-8", data);
    }

    /**
     * http 请求
     *
     * @param url           请求url
     * @param requestMethod 请求方式（GET or POST）
     * @param contentType   内容类型
     * @param charset       请求编码
     * @param data          请求数据
     */
    public static byte[] httpRequest(String url, String requestMethod, HttpContentType contentType, String charset, String data) throws IOException {
        //URL请求地址
        URL postUrl = new URL(url);
        HttpURLConnection urlcon = (HttpURLConnection) postUrl.openConnection();
        //获取报文长度
        int contentLength = data.getBytes().length;
        //设置 url 连接超时时间
        urlcon.setConnectTimeout(1000 * 15);
        //设置数据响应超时时间
        urlcon.setReadTimeout(1000 * 60 * 2);
        //设置请求方式
        urlcon.setRequestMethod(requestMethod);
        //设置请求不能使用缓存
        urlcon.setUseCaches(false);
        // 使用 URL 连接进行输入
        urlcon.setDoInput(true);
        // 使用 URL 连接进行输出
        urlcon.setDoOutput(true);
        // 设置内容长度
        urlcon.setRequestProperty("Content-Length", String.valueOf(contentLength));
        // 设置内容类型:
        urlcon.setRequestProperty("Content-Type", contentType.getValue());
        //设置数据编码
        urlcon.setRequestProperty("Charset", charset);
        //输出数据
        DataOutputStream output = new DataOutputStream(urlcon.getOutputStream());
        output.write(data.getBytes(charset));
        output.flush();
        output.close();
        byte[] bytes = getBytes(urlcon.getInputStream());
        urlcon.disconnect();
        return bytes;
    }

    private static byte[] getBytes(InputStream input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = input.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();
        return bytes;
    }


    public enum HttpContentType {

        /**
         * XML数据格式
         */
        APPLICATION_XML("XML数据格式", "application/xml"),

        /**
         * JSON数据格式
         */
        APPLICATION_JSON("JSON数据格式", "application/json"),

        /**
         * FROM表单数据格式
         */
        APPLICATION_FORM("FROM表单数据格式"," application/x-www-form-urlencoded")

        ;

        private String name;
        private String value;

        HttpContentType(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

}
