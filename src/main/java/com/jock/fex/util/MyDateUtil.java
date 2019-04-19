package com.jock.fex.util;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version V1.0
 * @since 2017-9-28
 * @Description 类说明:(日期时间工具类)
 */
public class MyDateUtil {

    /** 年月日 */
    public static SimpleDateFormat dateFormatV1 = new SimpleDateFormat("yyyyMMdd");

    public static SimpleDateFormat dateFormatV2 = new SimpleDateFormat("yyyy.MM.dd");

    public static SimpleDateFormat dateFormatV3 = new SimpleDateFormat("yyyy-MM-dd");

    public static SimpleDateFormat dateFormatV4 = new SimpleDateFormat("yyyy/MM/dd");

    public static SimpleDateFormat dateFormatV5 = new SimpleDateFormat("yyyy年MM月dd日");

    public static SimpleDateFormat dateFormatV6 = new SimpleDateFormat("yyyyMM");

    /** 年月日时分秒 */
    public static SimpleDateFormat dateTimeFormatV1 = new SimpleDateFormat("yyyyMMddHHmmss");

    public static SimpleDateFormat dateTimeFormatV2 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

    public static SimpleDateFormat dateTimeFormatV3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat dateTimeFormatV4 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static SimpleDateFormat dateTimeFormatV5 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    /** 年月 */
    public static SimpleDateFormat yearMothFormatV1 = new SimpleDateFormat("yyyy年MM月");

    public static SimpleDateFormat yearMothFormatV2 = new SimpleDateFormat("yyyy.MM");

    public static SimpleDateFormat yearMothFormatV3 = new SimpleDateFormat("yyyy-MM");

    public static SimpleDateFormat yearMothFormatV4 = new SimpleDateFormat("yyyy/MM");

    public static SimpleDateFormat yearFormatV1 = new SimpleDateFormat("yyyy");// 41

    /** 年月日时分 */
    public static SimpleDateFormat dateHourTimeFormatV1 = new SimpleDateFormat("yyyyMMddHHmm");

    public static SimpleDateFormat dateHourTimeFormatV2 = new SimpleDateFormat("yyyy.MM.dd.HH.mm");

    public static SimpleDateFormat dateHourTimeFormatV3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static SimpleDateFormat dateHourTimeFormatV4 = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public static SimpleDateFormat dateHourTimeFormatV5 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");

    public static String TIME_SUB_START = " 00:00:00";

    public static String TIME_SUB_END = " 23:59:59";

    /**
     * 日期字符串加天
     * 
     * @param src
     *            原日期字符串
     * @param f
     *            格式 1:yyyyMMdd 2:yyyy.MM.dd 3:yyyy-MM-dd 4:yyyy/MM/dd
     *            5:yyyy年MM月dd日
     * @param n
     *            天数，可为负数
     * @return
     */
    public static synchronized String dateStrAddDate(String src, int f, int n) {
        SimpleDateFormat sdf = null;
        if (f == 1) {
            sdf = dateFormatV1;
        } else if (f == 2) {
            sdf = dateFormatV2;
        } else if (f == 3) {
            sdf = dateFormatV3;
        } else if (f == 4) {
            sdf = dateFormatV4;
        } else {
            throw new RuntimeException("参数错误");
        }
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(sdf.parse(src));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        ca.add(Calendar.DAY_OF_MONTH, n);
        return sdf.format(ca.getTime());
    }

    /**
     * 日期字符串加天
     * 
     * @param src
     *            原日期字符串
     * @param f
     *            格式 1:yyyyMMddHHmmss 2:yyyy.MM.dd HH:mm:ss 3:yyyy-MM-dd
     *            HH:mm:ss 4:yyyy/MM/dd HH:mm:ss 5:yyyy年MM月dd日 HH时mm分ss秒
     * @param n
     *            天数，可为负数
     * @return
     */
    public static String timeStrAddDate(String src, int f, int n) {
        SimpleDateFormat sdf = selectByDateformat(f);
        Calendar ca = Calendar.getInstance();
        try {
            ca.setTime(sdf.parse(src));
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        ca.add(Calendar.DAY_OF_MONTH, n);
        return sdf.format(ca.getTime());
    }

    /**
     * 获取当前日期的前后N个月
     * 
     * @param n
     *            前N个月为负数，反之则为正数
     * @return
     */
    public static Date getDateAddMon(int n) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, n);
        Date needDate = c.getTime();
        return needDate;
    }

    /**
     * 获取当前日期的前后N个月
     * 
     * @param srcDate
     *            源时间
     * 
     * @param n
     *            前N个月为负数，反之则为正数
     * @return
     */
    public static Date getDateAddMon(Date srcDate, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(srcDate);
        c.add(Calendar.MONTH, n);
        Date needDate = c.getTime();
        return needDate;
    }

    /**
     * 获取当前时间之前或之后几分钟 minute
     * 
     * @param srcDate
     *            源时间
     * 
     * @param n
     *            前minute为负数，反之则为正数
     * @return
     */
    public static Date getTimeByMinute(int minute) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, minute);
        return c.getTime();

    }

    /**
     * 根据日期字符串转Date对象
     * 
     * @param src
     *            源时间
     * 
     * @param f
     *            格式 1:yyyyMMdd 2:yyyy.MM.dd 3:yyyy-MM-dd 4:yyyy/MM/dd
     *            5:yyyy年MM月dd日
     * 
     *            格式 11:yyyyMMddHHmmss 12:yyyy.MM.dd HH:mm:ss 13:yyyy-MM-dd
     *            HH:mm:ss 14:yyyy/MM/dd HH:mm:ss 15:yyyy年MM月dd日 HH时mm分ss秒
     * 
     *            格式 21:yyyy年MM月 22:yyyy.MM 23:yyyy-MM 24:yyyy/MM/dd
     * 
     *            格式 31:yyyyMMddHHmm 32:yyyy.MM.dd HH:mm 33:yyyy-MM-dd HH:mm
     *            34:yyyy/MM/dd HH:mm 35:yyyy年MM月dd日 HH时mm分
     * @return
     */
    public static Date parseStr2Date(String src, int fmt) {
        Date date = null;
        if (StringUtils.isEmpty(src)) {
            return date;
        }
        SimpleDateFormat sdf = selectByDateformat(fmt);
        try {
            date = sdf.parse(src);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 当前时间
     * 
     * @param f
     *            格式 1:yyyyMMdd 2:yyyy.MM.dd 3:yyyy-MM-dd 4:yyyy/MM/dd
     *            5:yyyy年MM月dd日
     * 
     *            格式 11:yyyyMMddHHmmss 12:yyyy.MM.dd HH:mm:ss 13:yyyy-MM-dd
     *            HH:mm:ss 14:yyyy/MM/dd HH:mm:ss 15:yyyy年MM月dd日 HH时mm分ss秒
     * 
     *            格式 21:yyyy年MM月 22:yyyy.MM 23:yyyy-MM 24:yyyy/MM/dd
     * 
     *            格式 31:yyyyMMddHHmm 32:yyyy.MM.dd HH:mm 33:yyyy-MM-dd HH:mm
     *            34:yyyy/MM/dd HH:mm 35:yyyy年MM月dd日 HH时mm分
     * @return
     */
    public static String getTimeNow(int f) {
        return getDateTime(Calendar.getInstance().getTime(), f);
    }

    /**
     * 当前时间
     * 
     * @param f
     *            格式 1:yyyyMMdd 2:yyyy.MM.dd 3:yyyy-MM-dd 4:yyyy/MM/dd
     *            5:yyyy年MM月dd日
     * 
     *            格式 11:yyyyMMddHHmmss 12:yyyy.MM.dd HH:mm:ss 13:yyyy-MM-dd
     *            HH:mm:ss 14:yyyy/MM/dd HH:mm:ss 15:yyyy年MM月dd日 HH时mm分ss秒
     * 
     *            格式 21:yyyy年MM月 22:yyyy.MM 23:yyyy-MM 24:yyyy/MM/dd
     * 
     *            格式 31:yyyyMMddHHmm 32:yyyy.MM.dd HH:mm 33:yyyy-MM-dd HH:mm
     *            34:yyyy/MM/dd HH:mm 35:yyyy年MM月dd日 HH时mm分
     * @return
     */
    public static String getDateTime(Date date, int f) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat sdf = selectByDateformat(f);
        return sdf.format(date);
    }

    private static synchronized SimpleDateFormat selectByDateformat(int fmt) {
        SimpleDateFormat sdf = null;
        switch (fmt) {
        case 1:
            sdf = dateFormatV1;
            break;
        case 2:
            sdf = dateFormatV2;
            break;
        case 3:
            sdf = dateFormatV3;
            break;
        case 4:
            sdf = dateFormatV4;
            break;
        case 5:
            sdf = dateFormatV5;
            break;
        case 6:
            sdf = dateFormatV6;
            break;
        case 11:
            sdf = dateTimeFormatV1;
            break;
        case 12:
            sdf = dateTimeFormatV2;
            break;
        case 13:
            sdf = dateTimeFormatV3;
            break;
        case 14:
            sdf = dateTimeFormatV4;
            break;
        case 15:
            sdf = dateTimeFormatV5;
            break;
        case 21:
            sdf = yearMothFormatV1;
            break;
        case 22:
            sdf = yearMothFormatV2;
            break;
        case 23:
            sdf = yearMothFormatV3;
            break;
        case 24:
            sdf = yearMothFormatV4;
            break;
        case 31:
            sdf = dateHourTimeFormatV1;
            break;
        case 32:
            sdf = dateHourTimeFormatV2;
            break;
        case 33:
            sdf = dateHourTimeFormatV3;
            break;
        case 34:
            sdf = dateHourTimeFormatV4;
            break;
        case 35:
            sdf = dateHourTimeFormatV5;
            break;
        case 41:
            sdf = yearFormatV1;
            break;
        default:
            throw new RuntimeException("参数错误");
        }
        return sdf;
    }

    /**
     * 得到某月的第一天
     */
    public static Date getMonthFirstDay(Date inputDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到某月的最后一天
     */
    public static Date getMonthLastDay(Date inputDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /** 获得两个日期之前相差的月份 */
    public static int getDifferMonth(Date start, Date end) {
        if (null == start || null == end) {
            return 0;
        }
        if (start.after(end)) {
            Date temp = start;
            start = end;
            end = temp;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);
        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
        if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    /**
     * 获取日期的加减N小时
     * 
     * @param srcDate
     *            源时间
     * 
     * @param n
     *            减N个为负数，反之则为正数
     * @return
     */
    public static Date getTimeAddHour(Date srcDate, int n) {
        Calendar c = Calendar.getInstance();
        c.setTime(srcDate);
        c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) + n);
        Date needDate = c.getTime();
        return needDate;
    }

    /**
     * 根据出生年月日计算实际年龄（精确到生日）
     * 
     * @param birthDate
     * @return
     */
    public static Integer getAgeByBirthDay(Date birthDate) {
        if (null == birthDate) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDate)) {
            // The birthDay is before Now.It's unbelievable!
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDate);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }
    /***
     * 计算两个时间相差天数
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int getDateSpace(Date beginDate, Date endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = null;
            Date date2 = null;
            date1 = sdf.parse(sdf.format(beginDate));
            date2 = sdf.parse(sdf.format(endDate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();
            Long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return between_days.intValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getHourByDate(Date date) {
    	if(null == date) {
    		return -1;
    	}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    /**
	 * year,month,dd转换yyyyMMdd的时间格式
	 */
    public static String changeTime(int year, int month, int date) {
		String time = "";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf1 = null;
		if (date == -1) {
			sdf1 = new SimpleDateFormat("yyyyMM");
		} else {
			sdf1 = new SimpleDateFormat("yyyyMMdd");
			c.set(Calendar.DATE, date);
		}
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		time = sdf1.format(c.getTime());
		return time;
	}
    
    /**
	* @Description: 获取某年某月的最后一天，返回yyyyMMdd格式的字符串
	* @param year
	* @param month
	* @return String
	 */
	public static String getLastDateTime(int year,int month){
		String time = "";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));  
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyyMMdd");
		time = sdf.format(c.getTime());
		return time;
	}
    /**
     * 判断字符串是否时间格式，以下3种为true：只有小时，如12；小时和分钟，如12:01；小时分钟描述，如12:12:12
     */
    public static boolean isTime(String timeStr) {
        if (timeStr == null) {
            return false;
        } else {
            Pattern pattern = Pattern.compile("^(([0-1]?[0-9])|([2][0-3]))(:([0-5]?[0-9]))?(:([0-5]?[0-9]))?$");
            Matcher matcher = pattern.matcher(timeStr);
            return matcher.matches();
        }
    }
}
