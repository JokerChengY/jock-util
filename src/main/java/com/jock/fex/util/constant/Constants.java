package com.jock.fex.util.constant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 常量
 * 
 * @author ldt
 */
public interface Constants {

	String ACCESS_URL = "https://jinrongqiao.oss-cn-shenzhen.aliyuncs.com";

	String BASE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	String BASE_DATE = "yyyy-MM-dd";

	DateFormat BASE_DATE_TIME_FORMAT = new SimpleDateFormat(BASE_DATE_TIME);

	DateFormat BASE_DATE_FORMAT = new SimpleDateFormat(BASE_DATE);

	String USER_SERVICE = "USERMGR";

	String PRODUCT_SERVICE = "PRODUCTMGR";

	String ACTIVITY_SERVICE = "ACTIVITYMGR";

	String PAYMENT_SERVICE = "PAYMENTMGR";

}
