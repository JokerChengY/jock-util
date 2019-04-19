package com.jock.fex.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * MD5的算法在RFC1321 中定义 在RFC 1321中，给出了Test suite用来检验你的实现是否正确： MD5 ("") = d41d8cd98f00b204e9800998ecf8427e MD5 ("a") =
 * 0cc175b9c0f1b6a831c399e269772661 MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") =
 * f96b697d7cb7938d525a2f31aaf161d0 MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 * 
 * 
 * 传入参数：一个字节数组 传出参数：字节数组的 MD5 结果字符串
 */
public class Md5Util {
    
    public static String getMD5(byte[] source) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
            // 所以表示成 16 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
                // 转换成 16 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
                // >>> 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return s;
    }

    public static byte[] getMD5ForBytes(byte[] bs) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bs);
            byte[] tmp = md.digest();
            return tmp;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
    
    public static String getMD5(String str) {
        return getMD5(str.getBytes());
    }
    
    // =======================盐值加密处理==============================================
    static private final char base64_code[] = {'.', '/', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9'};
    
    /**
     * 生成加密的盐
     * 
     * @return
     */
    public static String gensalt() {
        return gensalt(new SecureRandom());
    }
    
    /**
     * 生成加密的盐
     * 
     * @return
     */
    /**
     * 生成加密的盐
     * 
     * @param random
     * @return
     */
    private static String gensalt(SecureRandom random) {
        StringBuffer rs = new StringBuffer();
        byte rnd[] = new byte[16];
        
        random.nextBytes(rnd);
        
        rs.append("$");
        rs.append(encode_base64(rnd, rnd.length));
        rs.append("$");
        return rs.toString();
    }
    
    /**
     * 检查两个密码是否一样
     * 
     * @param pwd 原密码
     * @param md5pwd 加密后的密码
     * @param salts 盐s
     * @return
     */
    public static boolean checkpw(String pwd, String md5pwd, String... salts) {
        StringBuffer pawSB = new StringBuffer(pwd);
        for (String salt : salts) {
            pawSB.append(salt);
        }
        return (md5pwd.compareTo(getMD5(pawSB.toString())) == 0);
    }
    
    private static String encode_base64(byte d[], int len) throws IllegalArgumentException {
        int off = 0;
        StringBuffer rs = new StringBuffer();
        int c1, c2;
        
        if (len <= 0 || len > d.length)
            throw new IllegalArgumentException("Invalid len");
        
        while (off < len) {
            c1 = d[off++] & 0xff;
            rs.append(base64_code[(c1 >> 2) & 0x3f]);
            c1 = (c1 & 0x03) << 4;
            if (off >= len) {
                rs.append(base64_code[c1 & 0x3f]);
                break;
            }
            c2 = d[off++] & 0xff;
            c1 |= (c2 >> 4) & 0x0f;
            rs.append(base64_code[c1 & 0x3f]);
            c1 = (c2 & 0x0f) << 2;
            if (off >= len) {
                rs.append(base64_code[c1 & 0x3f]);
                break;
            }
            c2 = d[off++] & 0xff;
            c1 |= (c2 >> 6) & 0x03;
            rs.append(base64_code[c1 & 0x3f]);
            rs.append(base64_code[c2 & 0x3f]);
        }
        return rs.toString();
    }
    
    /**
     * MD5 32位加密
     */
    public static String md532(final String plainText) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
    
    public static void main(String[] args) {
		System.out.println(Md5Util.getMD5("123"));
		System.out.println(Md5Util.md532("123"));
	}
}
