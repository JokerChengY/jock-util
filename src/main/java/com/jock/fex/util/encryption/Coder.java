package com.jock.fex.util.encryption;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * 基础加密组件
 * Created by Lj on 2017/7/28.
 */
public abstract class Coder {

    public static String newStringUtf8(byte[] bytes) {
        return StringUtils.newStringUtf8(bytes);
    }

    public static byte[] getBytesUtf8(String string) {
        return StringUtils.getBytesUtf8(string);
    }

    /**
     * BASE64加密
     */
    public static String encodeBase64(String content) {
        return encodeBase64ReturnStr(getBytesUtf8(content));
    }

    /**
     * BASE64加密
     */
    public static String encodeBase64ReturnStr(byte[] binaryData) {
        return Base64.encodeBase64String(binaryData);
    }

    /**
     * BASE64加密
     */
    public static byte[] encodeBase64(byte[] binaryData) {
        return Base64.encodeBase64(binaryData);
    }

    /**
     * BASE64解密
     */
    public static byte[] decodeBase64(String base64String) {
        return Base64.decodeBase64(base64String);
    }

    /**
     * BASE64解密
     */
    public static byte[] decodeBase64(byte[] binaryData) {
        return Base64.decodeBase64(binaryData);
    }

    /**
     * BASE64解密
     */
    public static String decodeBase64ReturnStr(String base64String) {
        return newStringUtf8(decodeBase64(base64String));
    }

}
