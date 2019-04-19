package com.jock.fex.util.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密工具类
 */
public abstract class AESUtil {

    private static final String ALGORITHM = "AES";

    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * AESUtil/CBC/NOPADDING 加密
     *
     * @param key        密钥
     * @param initVector 向量
     * @param value      需要加密的内容
     */
    public static String encrypt(String key, String initVector, String value) throws Exception {
        int length = 16;
        StringBuilder valueBuilder = new StringBuilder(value);
        while (valueBuilder.toString().getBytes(DEFAULT_CHARSET).length % length != 0) {
            valueBuilder.append("\0");
        }
        value = valueBuilder.toString();
        //使用CBC模式，需要一个向量iv，可增加加密算法的强度
        byte[] keyBytes = parseHexStr2Byte(key);
        byte[] vectorBytes = parseHexStr2Byte(initVector);
        IvParameterSpec iv = new IvParameterSpec(vectorBytes);
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(value.getBytes(DEFAULT_CHARSET));
        return parseByte2HexStr(encrypted).trim();
    }

    /**
     * AESUtil/CBC/NOPADDING 解密
     *
     * @param key        密钥
     * @param initVector 向量
     * @param encrypted  需要解密的内容
     */
    public static String decrypt(String key, String initVector, String encrypted) throws Exception {
        byte[] keyBytes = parseHexStr2Byte(key);
        byte[] vectorBytes = parseHexStr2Byte(initVector);
        IvParameterSpec iv = new IvParameterSpec(vectorBytes);
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
        Cipher cipher = Cipher.getInstance("AES/CBC/NOPADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(parseHexStr2Byte(encrypted));
        return new String(original,DEFAULT_CHARSET);
    }

    /**
     * 将16进制字符串转换为二进制
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 将二进制转换成16进制字符串
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (byte aBuf : buf) {
            String hex = Integer.toHexString(aBuf & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String getAESKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(128);
        return parseByte2HexStr(kg.generateKey().getEncoded());
    }

    public static void main(String[] args) throws Exception {
        String key = getAESKey(); // 128 bit key
        String initVector = key; // 16 bytes IV
        System.out.println(key);
        String text = "   AESUtil/CBC/NOPADDING 加解密测试！！  ";
        String encryptStr = encrypt(key, initVector, text);
        System.out.println("Encrypt string is: " + encryptStr);
        String decryptStr = decrypt(key, initVector, encryptStr);
        System.out.println("Decrypt string is: " + text);
        System.out.println("Decrypt string is: " + decryptStr);
    }
}