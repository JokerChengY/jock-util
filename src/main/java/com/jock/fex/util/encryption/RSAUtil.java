package com.jock.fex.util.encryption;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密算法 Util
 * Created by Lj on 2017/7/28.
 */
public abstract class RSAUtil {

    private static final String KEY_ALGORITHM = "RSA";

    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * /**
     * 用私钥对信息生成数字签名
     *
     * @param data             加密数据
     * @param base64PrivateKey base64编码的私钥字符串
     */
    public static String sign(byte[] data, String base64PrivateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = Coder.decodeBase64(base64PrivateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return Coder.encodeBase64ReturnStr(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data            加密数据
     * @param sign            数字签名
     * @param base64PublicKey base64编码的公钥字符串
     */
    public static boolean verify(byte[] data, String sign, String base64PublicKey) throws Exception {
        // 解密由base64编码的公钥
        byte[] keyBytes = Coder.decodeBase64(base64PublicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(Coder.decodeBase64(sign));
    }

    /**
     * 用私钥解密
     *
     * @param data             加密数据
     * @param base64PrivateKey base64编码的私钥字符串
     */
    public static byte[] decryptByPrivateKey(byte[] data, String base64PrivateKey) throws Exception {
        // 对密钥解密
        byte[] keyBytes = Coder.decodeBase64(base64PrivateKey);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密
     *
     * @param data             加密数据
     * @param base64PrivateKey base64编码的私钥字符串
     */
    public static String decryptByPrivateKey(String data, String base64PrivateKey) throws Exception {
        return Coder.newStringUtf8(decryptByPrivateKey(Coder.decodeBase64(data), base64PrivateKey));
    }

    /**
     * 用公钥加密
     *
     * @param data            未加密数据
     * @param base64PublicKey base64编码的公钥字符串
     */
    public static byte[] encryptByPublicKey(byte[] data, String base64PublicKey) throws Exception {
        // 对公钥解密
        byte[] keyBytes = Coder.decodeBase64(base64PublicKey);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     *
     * @param data            未加密数据
     * @param base64PublicKey base64编码的公钥字符串
     */
    public static String encryptReturnBase64Str(String data, String base64PublicKey) throws Exception {
        return Coder.encodeBase64ReturnStr(encryptByPublicKey(Coder.getBytesUtf8(data), base64PublicKey));
    }

}
