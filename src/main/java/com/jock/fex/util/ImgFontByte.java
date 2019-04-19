package com.jock.fex.util;

import java.awt.*;
import java.io.ByteArrayInputStream;

/**
 * ttf字体文件
 *
 * @author dsna
 */
public class ImgFontByte {
    public Font getFont(int fontHeight) {
        try {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new ByteArrayInputStream(hex2byte(getFontByteStr())));
            return baseFont.deriveFont(Font.PLAIN, fontHeight);
        } catch (Exception e) {
            return new Font("Arial", Font.PLAIN, fontHeight);
        }
    }

    private byte[] hex2byte(String str) {
        if (str == null)
            return null;
        str = str.trim();
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;

        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer
                        .decode("0x" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * ttf字体文件的十六进制字符串
     *
     * @return
     */
    private String getFontByteStr() {
        StringBuffer sb = new StringBuffer("0001000000100040000400c04f532f327d8175d4000087740000005650434c5461e3d9fb000087");
        sb.append("cc00000036636d61709cbc69ab00007a64000005e863767420bb32bf1600000f2400000");
        sb.append("0326670676d8333c24f00000f1000000014676c7966302665d800000fc");
        sb.append("40000663c68646d7806beef530000806c0000070868656164c6469a910000880");
        sb.append("40000003668686561068002f40000883c00000024686d7478e8bd09b4000077");
        sb.append("b0000001ac6b65726efffe00650000804c0000001e6c6f6361001a319600007600000001b06d617870013");
        sb.append("e049f00008860000000206e616d65a927f7000000010c00000e04706f737469");
        sb.append("df66ea0000795c0000010670726570eacfd8a800000f580000006b0000001f017a000000000000000001");
        sb.append("de00000000000000000001001c00520000000000000002000e01de0000000000000003003201ec0000000000000004001c00");

        return sb.toString();
    }
}