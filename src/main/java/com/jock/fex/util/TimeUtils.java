package com.jock.fex.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author zgr
 *
 */
public class TimeUtils {

	/**
	 * 两个时间的先后顺序
	 * @author zgr
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static boolean compare_date(Date DATE1, Date DATE2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date dt1 = df.parse(df.format(DATE1));
			Date dt2 = df.parse(df.format(DATE2));
			System.out.println("DATE1========"+df.format(DATE1));
			System.out.println("DATE2========"+df.format(DATE2));
			System.out.println("dt1.getTime()========"+dt1.getTime());
			System.out.println("dt2.getTime()========"+dt2.getTime());
			if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return true;
			} else if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
}
