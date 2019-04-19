package com.jock.fex.util;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 身份证校验
 */
public class IdentityCardNumberUtils {



    /** 大陆地区地域编码最大值 **/
    public static final int          MAX_MAINLAND_AREACODE     = 659004;
    /** 大陆地区地域编码最小值 **/
    public static final int          MIN_MAINLAND_AREACODE     = 110000;
    /** 香港地域编码值 **/
    public static final int          HONGKONG_AREACODE         = 810000;                                                                                                                                                 // 香港地域编码值
    /** 台湾地域编码值 **/
    public static final int          TAIWAN_AREACODE           = 710000;
    /** 澳门地域编码值 **/
    public static final int          MACAO_AREACODE            = 820000;

    /** 数字正则 **/
    public static final String       regexNum                  = "^[0-9]*$";
    /** 闰年生日正则 **/
    public static final String       regexBirthdayInLeapYear   = "^((19[0-9]{2})|(200[0-9])|(201[0-8]))((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))$";
    /** 平年生日正则 **/
    public static final String       regexBirthdayInCommonYear = "^((19[0-9]{2})|(200[0-9])|(201[0-8]))((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))$";

    private static final Set<String> BLACK_SET       = new HashSet<String>() {

        private static final long serialVersionUID = 48136604486603324L;
        {
            add("111111111111111");
        }
    };

    /**
     * 用户身份证号码的打码隐藏加星号加*
     * <p>18位和非18位身份证处理均可成功处理</p>
     * <p>参数异常直接返回null</p>
     *
     * @param idCardNum 身份证号码
     * @param front     需要显示前几位
     * @param end       需要显示末几位
     * @return 处理完成的身份证
     */
    public static String idMask(String idCardNum, int front, int end) {
        //身份证不能为空
        if (StringUtils.isEmpty(idCardNum)) {
            return null;
        }
        //需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return null;
        }
        //需要截取的不能小于0
        if (front < 0 || end < 0) {
            return null;
        }
        //计算*的数量
        int asteriskCount = idCardNum.length() - (front + end);
        StringBuffer asteriskStr = new StringBuffer();
        for (int i = 0; i < asteriskCount; i++) {
            asteriskStr.append("*");
        }
        String regex = "(\\w{" + String.valueOf(front) + "})(\\w+)(\\w{" + String.valueOf(end) + "})";
        return idCardNum.replaceAll(regex, "$1" + asteriskStr + "$3");
    }

    public static void main(String[] args) {
        System.out.println(">>>>>>>"+idMask("441622199704012072",14,0));
    }

    /**
     * <p>
     * 身份证格式强校验
     * </p>
     * <p>
     * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码， 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位） 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
     * 4、顺序码（第十五位至十七位） 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数） （1）十七位数字本体码加权求和公式 S =
     * Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和 Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5
     * 8 4 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     * </p>
     */
    public static final boolean strongVerifyIdNumber(String idNumber) {
        if (StringUtils.isBlank(idNumber)) {
            return false;
        }
        idNumber = idNumber.trim();

        if (BLACK_SET.contains(idNumber)) {
            return false;
        }
        if (!checkIdNumberRegex(idNumber)) {
            return false;
        }
        if (!checkIdNumberArea(idNumber.substring(0, 6))) {
            return false;
        }
        idNumber = convertFifteenToEighteen(idNumber);
        if (!checkBirthday(idNumber.substring(6, 14))) {
            return false;
        }
        if (!checkIdNumberVerifyCode(idNumber)) {
            return false;
        }
        return true;
    }

    /**
     * 身份证正则校验
     */
    private static boolean checkIdNumberRegex(String idNumber) {
        return Pattern.matches("^([0-9]{17}[0-9Xx])|([0-9]{15})$", idNumber);
    }

    /**
     * 身份证地区码检查
     */
    private static boolean checkIdNumberArea(String idNumberArea) {
        int areaCode = Integer.parseInt(idNumberArea);
        if (areaCode == HONGKONG_AREACODE || areaCode == MACAO_AREACODE || areaCode == TAIWAN_AREACODE) {
            return true;
        }
        if (areaCode <= MAX_MAINLAND_AREACODE && areaCode >= MIN_MAINLAND_AREACODE) {
            return true;
        }
        return false;
    }

    /**
     * 将15位身份证转换为18位
     */
    private static String convertFifteenToEighteen(String idNumber) {
        if (15 != idNumber.length()) {
            return idNumber;
        }
        idNumber = idNumber.substring(0, 6) + "19" + idNumber.substring(6, 15);
        idNumber = idNumber + getVerifyCode(idNumber);
        return idNumber;
    }

    /**
     * 根据身份证前17位计算身份证校验码
     */
    private static String getVerifyCode(String idNumber) {
        if (!Pattern.matches(regexNum, idNumber.substring(0, 17))) {
            return null;
        }
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum = sum + Integer.parseInt(String.valueOf(idNumber.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        return ValCodeArr[sum % 11];
    }

    /**
     * 身份证出生日期嘛检查
     */
    private static boolean checkBirthday(String idNumberBirthdayStr) {
        Integer year = null;
        try {
            year = Integer.valueOf(idNumberBirthdayStr.substring(0, 4));
        } catch (Exception e) {
        }
        if (null == year) {
            return false;
        }
        if (isLeapYear(year)) {
            return Pattern.matches(regexBirthdayInLeapYear, idNumberBirthdayStr);
        } else {
            return Pattern.matches(regexBirthdayInCommonYear, idNumberBirthdayStr);
        }
    }

    /**
     * 判断是否为闰年
     */
    private static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * 身份证校验码检查
     */
    private static boolean checkIdNumberVerifyCode(String idNumber) {
        return getVerifyCode(idNumber).equalsIgnoreCase(idNumber.substring(17));
    }
    /**
     * 通过身份证号码获取出生日期、性别、年龄
     * @param certificateNo
     * @return 返回的出生日期格式：1990-01-01   性别格式：F-女，M-男
     */
    public static Map<String, String> getBirAgeSex(String certificateNo) {
        String birthday = "";
        String age = "";
        String sexCode = "";

        int year = Calendar.getInstance().get(Calendar.YEAR);
        char[] number = certificateNo.toCharArray();
        boolean flag = true;
        if (number.length == 15) {
            for (int x = 0; x < number.length; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        } else if (number.length == 18) {
            for (int x = 0; x < number.length - 1; x++) {
                if (!flag) return new HashMap<String, String>();
                flag = Character.isDigit(number[x]);
            }
        }
        if (flag && certificateNo.length() == 15) {
            birthday = "19" + certificateNo.substring(6, 8) + "-"
                    + certificateNo.substring(8, 10) + "-"
                    + certificateNo.substring(10, 12);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt("19" + certificateNo.substring(6, 8))) + "";
        } else if (flag && certificateNo.length() == 18) {
            birthday = certificateNo.substring(6, 10) + "-"
                    + certificateNo.substring(10, 12) + "-"
                    + certificateNo.substring(12, 14);
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "F" : "M";
            age = (year - Integer.parseInt(certificateNo.substring(6, 10))) + "";
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("birthday", birthday);
        map.put("age", age);
        map.put("sexCode", sexCode);
        return map;
    }

    /**
     * 根据身份证判断性别
     *  2-女 1-男
     * @param certificateNo
     * @return
     */
    public static String getSexCode(String certificateNo) {
        String sexCode = "";
        if (certificateNo.length() == 15) {
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 3, certificateNo.length())) % 2 == 0 ? "2" : "1";
        } else if ( certificateNo.length() == 18) {
            sexCode = Integer.parseInt(certificateNo.substring(certificateNo.length() - 4, certificateNo.length() - 1)) % 2 == 0 ? "2" : "1";
        }
        return  sexCode;
    }
    /**
     * 检查身份证是否能用
     * @param idNumber
     * @return
     */
    public static boolean checkVerifyIdNumber(String idNumber){
        Boolean strongVerifyIdNumber =strongVerifyIdNumber(idNumber);
        System.out.println("校验结果》》》》》》"+strongVerifyIdNumber);
        return  strongVerifyIdNumber;
    }
//    public static void main(String[] args) {
//        System.out.println(strongVerifyIdNumber("9999"));
//    }

}
