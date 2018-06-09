/**
 * 
 */
package com.chinaoly.frm.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 时间操作公共方法
 * @author jiangyi
 * 
 */
public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static String datePattern = "yyyy-MM-dd";
	private static String timePattern = "HH:mm";
	public static String fullPattern = "yyyy-MM-dd HH:mm:ss";

	/**
	 * * Return 缺省的日期格式 (yyyy/MM/dd)
	 * 
	 * @return 在页面中显示的日期格式
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串 *
	 * 
	 * @param aDate
	 *            日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	public static final String getDate(String pattern) {
		Date date = new Date();
		return getDate(date, pattern);
	}

	public static final String getDate(Date date, String pattern) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (date != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	public static Date getDate(String dateString, String pattern) {
		SimpleDateFormat df = null;
		Date date = new Date();
		if (dateString != null) {
			try {
				df = new SimpleDateFormat(pattern);
				date = df.parse(dateString);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * * 按照日期格式，将字符串解析为日期对象 * @param aMask * 输入字符串的格式 * @param strDate *
	 * 一个按aMask格式排列的日期的字符串描述 * @return Date 对象 * @see java.text.SimpleDateFormat
	 * * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
		}
		return (date);
	}

	/**
	 * * This method returns the current date time in the format: yyyy/MM/dd
	 * HH:MM * a * @param theTime * the current time * @return the current
	 * date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * 取当前日期时间
	 * 
	 * @return 当前日志时间字符串，格式如（2009-04-09 14:48:48)
	 */
	public static String getCurrentDatetime() {
		return getDateTime("yyyy-MM-dd HH:mm:ss", new Date());
	}

	/**
	 * 字符串日期格式转换(转换失败返回原日期)
	 */
	public static String dateStringParse(String dateStr, String inPattern,
			String outPattern) {
		SimpleDateFormat sdf1 = new SimpleDateFormat(inPattern);
		SimpleDateFormat sdf2 = new SimpleDateFormat(outPattern);
		Date d = null;
		try {
			d = sdf1.parse(dateStr);
			dateStr = sdf2.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * * This method returns the current date in the format: yyyy/MM/dd * * @return
	 * the current date * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern); // This seems
																	// like
																	// quite a
																	// hack
																	// (date ->
																	// string ->
																	// date), //
																	// but it
																	// works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * * This method generates a string representation of a date's date/time in
	 * * the format you specify on input * * @param aMask * the date pattern the
	 * string is in * @param aDate * a date object * @return a formatted string
	 * representation of the date * * @see java.text.SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * * 根据日期格式，返回日期按datePattern格式转换后的字符串 * @param aDate Date * @return String
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * * 按照日期格式，将字符串解析为日期对象 * @param strDate String * @return Date * @throws
	 * ParseException
	 */
	public static Date convertStringToDate(String strDate) {
		Date aDate = null;
		if (log.isDebugEnabled()) {
			log.debug("converting date with pattern: " + datePattern);
		}
		aDate = convertStringToDate(datePattern, strDate);
		return aDate;
	}

	public static String getYear() {
		Date date = new Date();
		return getDate(date, "yyyy");
	}

	public static String getMonth() {
		Date date = new Date();
		return getDate(date, "MM");
	}

	public static String getDay() {
		Date date = new Date();
		return getDate(date, "dd");
	}

	/**
	 * * 返回小时 * * @param date 日期 * @return 返回小时
	 * */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * * 返回分钟 * * @param date 日期 * @return 返回分钟
	 * */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * * 返回秒钟 * @param date 日期 * @return 返回秒钟
	 * */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * * 返回毫秒 * @param date 日期 * @return 返回毫秒
	 * */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * * 日期相加 * @param date 日期 * @param day 天数 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * * 日期相减 * @param date 日期 * @param date1 日期 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	public static int diffDateToHour(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (1000 * 60 * 60));
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss";
	}

	/**
	 * 计算秒之间相差多少个小时
	 * 
	 * @param timeInSeconds
	 */
	public static long calcHours(long timeInSeconds) {
		long hours;
		hours = timeInSeconds / 3600;
		return hours;
	}

	/**
	 * 计算两个时期之间的相差多少秒
	 */
	public static long getCalueteSeconde(String smallString, String bigString) {
		Date bigdate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",
				bigString);
		Date smalldate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",
				smallString);
		long big = bigdate.getTime();
		long mall = smalldate.getTime();
		long difference = big - mall;
		return difference / 1000;
	}

	/**
	 * 计算某时间距离当前时间相差多少毫秒
	 * 
	 * @param dataString
	 * @return
	 */
	public static long getCalueteMillisecond(String dataString) {
		Date malldate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",
				DateUtil.getCurrentDatetime());
		Date bigdate = DateUtil.convertStringToDate("yyyy-MM-dd HH:mm:ss",
				dataString);
		long big = bigdate.getTime();
		long mall = malldate.getTime();
		long difference = big - mall;
		return difference;
	}

	/**
	 * 计算某时间距离当前时间 ：x天x小时x分x秒
	 * 
	 * @param dataString
	 * @return
	 */
	public static String getDistanceTime(String dataString) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long diff = Math.abs(getCalueteMillisecond(dataString));
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		return (day > 0 ? (day + "天") : "") + (hour > 0 ? (hour + "小时") : "")
				+ (min > 0 ? (min + "分") : sec + "秒");
	}
	
	/**
	 * 将一段时间转换成：x天x小时x分x秒x毫秒
	 * 例：1000000毫秒  0天0时16分40秒1毫秒
	 * @param s（毫秒）
	 * @return
	 */
	public static String formatTime(long s) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long mis = 0;
		
		day = s / (24 * 60 * 60 * 1000);
		hour = (s / (60 * 60 * 1000) - day * 24);
		min = ((s / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (s / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		mis = (s - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - sec * 1000);
		return (day >= 0 ? (day + "天") : "") + (hour >= 0 ? (hour + "小时") : "")
				+ (min >= 0 ? (min + "分") : "" ) + (sec >= 0 ? (sec + "秒") : "" ) + (mis >= 0 ? (mis + "毫秒") : "" );
	}

	public static long calcuteHourTetweenTwo(String smallString,
			String bigString) {
		return calcHours(getCalueteSeconde(smallString, bigString));
	}

	/**
	 * 
	 * @param datatime传入时间
	 * @param hours该时间间隔多少小时之前的时间
	 * @return
	 * @throws Exception
	 */
	public static String getDateAndHours(String datatime, int hours)
			throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = format.parse(datatime);
		long Time = (date1.getTime() / 1000) - 60 * 60 * hours;
		date1.setTime(Time * 1000);
		String mydate1 = format.format(date1);
		return mydate1;
	}

	public static String getDateAddHours(String datatime, int hours)
			throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = format.parse(datatime);
		long Time = (date1.getTime() / 1000) + 60 * 60 * hours;
		date1.setTime(Time * 1000);
		String mydate1 = format.format(date1);
		return mydate1;
	}
	
}
