package com.jock.fex.util;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/6/21.
 */
public final class SecurityUtils {

    /**
     * BASE64编码
     *
     * @param source 欲编码字符串
     * @return 编码的字符串
     */
    public static String BASE64Enc(String source) {
        if (source == null)
            return null;
        try {
            return Base64.encodeBase64String(source.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BASE64解码
     *
     * @param source 欲解码字符串
     * @return 解码的字符串
     */
    public static String BASE64Dec(String source) {
        if (source == null)
            return null;
        try {
            byte[] b = Base64.decodeBase64(source);
            return new String(b, "utf-8");
        } catch (Exception e) {
            return null;
        }
    }
}
