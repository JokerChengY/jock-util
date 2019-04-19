package com.jock.fex.util.encryption;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

public class SecretKeyGenerate {

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 取得私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Coder.encodeBase64ReturnStr(key.getEncoded());
    }

    /**
     * 取得公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Coder.encodeBase64ReturnStr(key.getEncoded());
    }

    /**
     * 初始化密钥
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static void main(String[] a) throws Exception {

        //生成金服公私钥
        Map<String, Object> jfKeysMap = initKey();
        String jfPublicKey = getPublicKey(jfKeysMap);
        String jfPrivateKey = getPrivateKey(jfKeysMap);

        System.out.println(jfPublicKey);
        System.out.println(jfPrivateKey);
    }
}
