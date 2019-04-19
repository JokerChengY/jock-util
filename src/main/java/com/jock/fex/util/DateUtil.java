package com.jock.fex.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	/**
	 * 文章显示时间转换
	 * 
	 * @param createdTime
	 * @return
	 */
	public final static String articleTime(Date createdTime) {
		final SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd", Locale.CHINA);
		if (createdTime != null) {
			long agoTimeInMin = (new Date(System.currentTimeMillis()).getTime() - createdTime.getTime()) / 1000 / 60;
			// 如果在当前时间以前一分钟内
			if (agoTimeInMin <= 1) {
				return "刚刚";
			} else if (agoTimeInMin <= 60) {
				// 如果传入的参数时间在当前时间以前10分钟之内
				return agoTimeInMin + "分钟前";
			} else if (agoTimeInMin <= 60 * 24) {
				return agoTimeInMin / 60 + "小时前";
			} else if (agoTimeInMin <= 60 * 24 * 2) {
				return agoTimeInMin / (60 * 24) + "天前";
			} else {
				return format.format(createdTime);
			}
		} else {
			return format.format(new Date(0));
		}
	}

	/**
	 * 文章显示时间转换
	 * 
	 * @param createdTime
	 * @return
	 */
	public final static String articleTime2(Date createTime) {
		if (createTime == null) {
			return "";
		}

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Calendar cal = Calendar.getInstance();

		final String dateStr = sdf.format(createTime);

		// 当天
		if (dateStr.equals(sdf.format(cal.getTime()))) {
			final Calendar c = Calendar.getInstance();
			c.setTime(createTime);
			return calDif(c, cal, Calendar.HOUR_OF_DAY) + "小时前";
		}

		// 昨天
		cal.add(Calendar.DAY_OF_YEAR, -1);
		if (dateStr.equals(sdf.format(cal.getTime()))) {
			return "昨天";
		}

		return dateStr;
	}

	/**
	 * 获取时间格式化模型
	 * 
	 * @param pattern
	 * @return
	 */
	public static final SimpleDateFormat getDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	/**
	 * 判断是否是今天
	 * 
	 * @param d
	 * @return
	 */
	public static boolean today(Date d) {
		final SimpleDateFormat sdf = getDateFormat("yyyy-MM-dd");
		final String curDate = sdf.format(new Date());
		if (curDate.equals(sdf.format(d))) {
			return true;
		}

		return false;
	}

	/**
	 * 获取时间差**
	 * 
	 * @param start
	 * @param end
	 * @return "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String timeDif(Date start, Date end) {
		if (start != null && end != null) {
			final long time = end.getTime() - start.getTime();
			return getTimeDif(time);
		} else {
			return "";
		}
	}

	/**
	 * 毫秒转换字符串时间
	 * 
	 * @param time
	 * @return day + "天" + hour + "时" + min + "分" + sec + "秒";
	 */
	public static final String getTimeDif(long time) {
		final long oneDay = 24 * 60 * 60 * 1000;
		final long oneHour = 60 * 60 * 1000;
		final long oneMin = 60 * 1000;
		final long oneSec = 1000;
		final long day = time / oneDay;
		final long hour = (time - day * oneDay) / oneHour;
		final long min = (time - day * oneDay - hour * oneHour) / oneMin;
		final long sec = (time - day * oneDay - hour * oneHour - min * oneMin) / oneSec;
		return day + "天" + hour + "时" + min + "分" + sec + "秒";
	}

	/**
	 * 判断当前时间是否在活动举行的时间内**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static Boolean isActivityTime(Date startTime, Date endTime) {
		Boolean flag = false;
		final SimpleDateFormat sdf = getDateFormat("yyyy-MM-dd");
		try {
			if (startTime == null || endTime == null) {
				return flag;
			}
			final String sdate = sdf.format(startTime);
			final String edate = sdf.format(endTime);
			final String today = sdf.format(new Date());

			final int startResult = today.compareTo(sdate);
			final int endResult = today.compareTo(edate);

			if (startResult >= 0 && endResult <= 0) {
				flag = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 计算两个日历时间差
	 * 
	 * @param c1
	 * @param c2
	 * @param field
	 * @return c2 -c1
	 */
	public static int calDif(Calendar c1, Calendar c2, int field) {
		return c2.get(field) - c1.get(field);
	}

	/**
	 * 计算两个日期的天数差
	 * 
	 * @param d1
	 * @param d2
	 * @return d2 - d1
	 */
	public static long dateCountDif(Date d1, Date d2) {
		final long milis = d2.getTime() - d1.getTime();
		return milis / 1000 / 60 / 60 / 24;
	}

	/**
	 * 与当前时间比计算天数差
	 * 
	 * @param d
	 * @return d - new Date()
	 */
	public static long dateCountDif(Date d) {
		if (d == null) {
			return 0;
		}
		return dateCountDif(new Date(), d);
	}

	/**
	 * 修改时间
	 * 
	 * @param field
	 * @param time
	 * @return
	 */
	public static Date addDate(int field, int time) {
		return addDate(new Date(), field, time);
	}

	/**
	 * 修改时间
	 * 
	 * @param date
	 * @param field
	 * @param time
	 * @return
	 */
	public static Date addDate(Date date, int field, int time) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, time);
		return cal.getTime();
	}

	/**
	 * 根据格式获取时间
	 * 
	 * @author zgr
	 * @创建时间：2018年10月12日 @param date
	 * @return
	 */
	public static String dateFormat(Date date, String dateFormat) {
		if (date != null) {
			return getDateFormat(dateFormat).format(date);
		}
		return "";
	}

	/**
	 * 根据格式获取时间
	 * 
	 * @author zgr
	 * @创建时间：2018年10月12日 @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateFormat(String date, String dateFormat) {
		if (StringUtils.hasText(date)) {
			return getDateFormat(dateFormat).format(new Date(Long.parseLong(date)));
		}
		return "";
	}

	/**
	 * 根据格式获取时间
	 * 
	 * @author zgr
	 * @创建时间：2018年10月12日 @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateFormat(String date) {
		return dateFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 根据格式获取时间
	 * 
	 * @author zgr
	 * @创建时间：2018年10月12日 @param date
	 * @param dateFormat
	 * @return
	 */
	public static String dateFormat(Date date) {
		return dateFormat(date, "yyyy-MM-dd");
	}

	/**
	 * 获取时间间隔
	 * 
	 * @author zgr
	 * @创建时间：2018年10月18日 @param date
	 * @return
	 * @throws ParseException
	 */
	public static String timeInterval(Date date) {
		if (date != null) {
			final SimpleDateFormat sdf = getDateFormat("yyyy-MM-dd");
			final String startDate = sdf.format(date);
			final String currentDate = sdf.format(new Date());
			if (startDate.compareTo(currentDate) < 0) {
				// 在当前时间以前
				try {
					final Date cur = sdf.parse(currentDate);
					final Date start = sdf.parse(startDate);

					final long count = (cur.getTime() - start.getTime()) / 1000 / 60 / 60 / 24;
					if (count == 1) {
						return "昨天";
					} else {
						return count + "天前";
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				// 当前
				return intervalHour(date);
			}
		}
		return "";
	}

	/**
	 * 获得时间间隔：小时
	 * 
	 * @author zgr
	 * @创建时间：2018年10月18日 @param date
	 * @return
	 */
	public static String intervalHour(Date date) {
		if (date != null) {
			long agoTimeInMin = (new Date(System.currentTimeMillis()).getTime() - date.getTime()) / 1000 / 60;
			return agoTimeInMin / 60 + "小时前";
		}
		return "";
	}

	/**
	 * 获得时间间隔：小时
	 * 
	 * @author zgr
	 * @创建时间：2018年10月18日 @param date
	 * @return
	 */
	public static String intervalDay(Date date) {
		if (date != null) {
			long agoTimeInMin = (System.currentTimeMillis() - date.getTime()) / 1000 / 60;
			if (agoTimeInMin / (60 * 24) == 1) {
				return "昨天";
			}
			return agoTimeInMin / (60 * 24) + "天前";
		}
		return "";
	}

	/**
	 * Sring类型转年月日 2018年10月18日
	 * 
	 * @param date
	 * @return
	 */
	public static String dateFormatString(String date) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy年MM月dd日");
		Date dateNew = format1.parse(date);
		String date1 = format2.format(dateNew);
		return date1;
	}

	/**
	 * string转格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String dateFormatToString(String date) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		Date dateNew = format1.parse(date);
		String date1 = format1.format(dateNew);
		return date1;
	}

	/**
	 * data转string yyyyMMdd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String dateFormatToString(Date date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		String date1 = "";
		if (!StringUtils.isEmpty(date)) {
			date1 = simpleDateFormat.format(date);
		}

		return date1;
	}

	/**
	 * string转格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String dateFormatToyyyyMMdd(String date) throws ParseException {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
		Date dateNew = format1.parse(date);
		String date1 = format2.format(dateNew);
		return date1;
	}

	/**
	 * date转格式yyyy-MM-dd hh:mm:ss * * @param date * @return * @throws ParseExceptio
	 */
	public static String dateFormatToyyyyMMddhhmmss(Date date) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss", Locale.CHINA);
		String dateString = "";
		if (!StringUtils.isEmpty(date)) {
			dateString = format.format(date);
		}
		return dateString;
	}

	/**
	 * 时间格式化后提取年份
	 * 
	 * @param forDate
	 * @param format
	 * @return
	 */
	@SuppressWarnings("unused")
	public static int getYear(String forDate, String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date;
		try {
			date = sdf.parse(forDate);
			cal.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 时间格式化后提取月份
	 * 
	 * @param forDate
	 * @param format
	 * @return
	 */
	@SuppressWarnings("unused")
	public static int getMonth(String forDate, String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date;
		try {
			date = sdf.parse(forDate);
			cal.setTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取月份第一天
	 *
	 * @param dateStr
	 * @return
	 */
	public static String getMonthBegin() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long currentTimeMillis = System.currentTimeMillis();
		Date date1 = new Date();
		date1.setTime(currentTimeMillis);
		String dateStr = format.format(date1);
		
		String monthBegin = "";
		if (!StringUtils.hasText(dateStr)) {
			return monthBegin;
		}
		try {
			Date date = format.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			monthBegin = format.format(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return monthBegin;
	}

	/**
	 * 获取月份最后一天
	 *
	 * @param dateStr
	 * @return
	 */
	public static String getMonthEnd() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long currentTimeMillis = System.currentTimeMillis();
		Date date1 = new Date();
		date1.setTime(currentTimeMillis);
		String dateStr = format.format(date1);
		
		String lastday = "";
		if (!StringUtils.hasText(dateStr)) {
			return lastday;
		}
		try {
			Date date = format.parse(dateStr);
			Calendar cale = Calendar.getInstance();
			cale = Calendar.getInstance();
			cale.add(Calendar.MONTH, 1);
			cale.set(Calendar.DAY_OF_MONTH, 0);
			lastday = format.format(cale.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastday;
	}

}
