package com.example.fastkit.utils.time_deal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Java时间处理
 */
public class TimeUtils {
	
	public static final String FORMAT_0 = "yyyy-MM-dd";
	
	public static final String FORMAT_1 = "HH:mm:ss";
	
	public static final String FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	
	/**
	 * 不能实例化
	 */
	private TimeUtils() {
	}
	
	/**
	 * 得到日期的字符串
	 * @param date 日期对象
	 * @param format 格式化类型
	 * @return
	 */
	public static String getDateStr(Date date, String format) {
		SimpleDateFormat simpleDateFormat;
		return (simpleDateFormat = new SimpleDateFormat(format)).format(date);
	}
	
	/**
	 * 得到日期的字符串
	 * @param longDate 时间戳
	 * @param format 格式化类型
	 * @return
	 */
	public static String getDateStr(long longDate, String format) {
		SimpleDateFormat simpleDateFormat;
		return (simpleDateFormat = new SimpleDateFormat(format)).format(longDate);
	}
	
	/**
	 * 获取计算机当前时间
	 * @param format 格式化类型，例如"yyyy-MM-dd"
	 * @return
	 */
	public static String getNowString(String format) {
		return getDateStr(System.currentTimeMillis(), format);
	}
	
	/**
	 * 获取当前日期对应的星期数.
	 * <br>1=星期天,2=星期一,3=星期二,4=星期三,5=星期四,6=星期五,7=星期六
	 * @return 当前日期对应的星期数
	 */
	public static int dayOfWeek() {
		GregorianCalendar g = new GregorianCalendar();
		int ret = g.get(java.util.Calendar.DAY_OF_WEEK);
		g = null;
		return ret;
	}
	
	/**
	 * 获取时间添加偏移量后的时间(天)
 	 * @param date 参考时间
	 * @param offset 偏移量(天)，大于0为未来 小于0为过去
	 * @return
	 */
	public static Date getOffsetDay(Date date, int offset) {
		return calculate(date, GregorianCalendar.DATE, offset);
	}
	
	/**
	 * 获取时间添加偏移量后的时间(分钟)
	 * @param date 参考时间
	 * @param offset 偏移量(分钟)，大于0为未来 小于0为过去
	 * @return
	 */
	public static Date getOffsetMinute(Date date, int offset) {
		return calculate(date, GregorianCalendar.MINUTE, offset);
	}
	
	/**
	 * 获取时间添加偏移量后的时间(小时)
	 * @param date 参考时间
	 * @param offset 偏移量(小时)，大于0为未来 小于0为过去
	 * @return
	 */
	public static Date getOffsetHour(Date date, int offset) {
		return calculate(date, GregorianCalendar.HOUR, offset);
	}
	
	/**
	 * 获取时间添加偏移量后的时间(年)
	 * @param date 参考时间
	 * @param offset 偏移量(年)，大于0为未来 小于0为过去
	 * @return
	 */
	public static Date getoffsetYear(Date date, int offset) {
		return calculate(date, GregorianCalendar.YEAR, offset);
	}
	
	/**
	 * 对日期(时间)中由field参数指定的日期成员进行加减计算. <br>
	 * 例子: <br>
	 * 如果Date类型的d为 2005年8月20日,那么 <br>
	 * calculate(d,GregorianCalendar.YEAR,-10)的值为1995年8月20日 <br>
	 * 而calculate(d,GregorianCalendar.YEAR,+10)的值为2015年8月20日 <br>
	 *
	 * @param d
	 *            日期(时间).
	 * @param field
	 *            日期成员. <br>
	 *            日期成员主要有: <br>
	 *            年:GregorianCalendar.YEAR <br>
	 *            月:GregorianCalendar.MONTH <br>
	 *            日:GregorianCalendar.DATE <br>
	 *            时:GregorianCalendar.HOUR <br>
	 *            分:GregorianCalendar.MINUTE <br>
	 *            秒:GregorianCalendar.SECOND <br>
	 *            毫秒:GregorianCalendar.MILLISECOND <br>
	 * @param amount
	 *            加减计算的幅度.+n=加n个由参数field指定的日期成员值;-n=减n个由参数field代表的日期成员值.
	 * @return 计算后的日期(时间).
	 */
	private static Date calculate(Date d, int field, int amount) {
		if (d == null)
			return null;
		GregorianCalendar g = new GregorianCalendar();
		g.setGregorianChange(d);
		g.add(field, amount);
		return g.getTime();
	}
	
	/**
	 * 得到日期字符串对应的日期
	 * @param timeString 源格式化时间字符串
	 * @param format 时间格式
	 * @param l 出错返回默认类型
	 * @return
	 */
	public static Date getDate(String timeString, String format, long l) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date;
		try {
			date = simpleDateFormat.parse(timeString);
		} catch (ParseException _ex) {
			date = new Date(l);
		}
		return date;
	}
	
	/**
	 * 得到日期字符串对应的日期
	 * @param timeString 源格式化时间字符串
	 * @param format 时间格式
	 * @return
	 */
	public static Date getDate(String timeString, String format)
	{
		return getDate(timeString, format, 0L);
	}
	
	/**
	 *获取当前日期的时间戳
	 * @return
	 */
	public static long getNowTimestamp() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 得到日期字符串对应的时间戳
	 * @param timeString 源时间字符串
	 * @param format 时间格式
	 * @param l 出错返回默认类型
	 * @return 时间戳
	 */
	public static long getTimestamp(String timeString, String format, long l)
	{
		return getDate(timeString, format, l).getTime();
	}
	
	/**
	 * 得到日期字符串对应的时间戳
	 * @param timeString 源时间字符串
	 * @param format 时间格式
	 * @return 时间戳
	 */
	public static long getTimestamp(String timeString, String format)
	{
		return getTimestamp(timeString, format, 0L);
	}
	
	/**
	 * 转换日期字符串的格式
	 * @param timeString 源日期字符串
	 * @param originalFormat 格式化类型1
	 * @param targetFormat 格式化类型2
	 * @return
	 */
	public static String convertDateFormat(String timeString, String originalFormat, String targetFormat) {
		Date date = getDate(timeString, originalFormat);
		if (null == date) {
			return "";
		} else  {
			return getDateStr(date, targetFormat);
		}
	}
	
	
	public static String convertTimeZone(String originFormater,
	                                     String originTimeString, String targetFormater, String targetTimeZoneId) {
		if (originFormater == null || "".equals(originFormater))
			return null;
		if (originTimeString == null || "".equals(originTimeString))
			return null;
		if (targetFormater == null || "".equals(targetFormater))
			return null;
		if (targetTimeZoneId == null || "".equals(targetTimeZoneId))
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(originFormater);
		try {
			int diffTime = getDiffTimeZoneRawOffset(targetTimeZoneId);
			Date d = sdf.parse(originTimeString);
			long nowTime = d.getTime();
			long newNowTime = nowTime - diffTime;
			d = new Date(newNowTime);
			return getDateStr(d, targetFormater);
		} catch (ParseException e) {
			return null;
		} finally {
			sdf = null;
		}
	}
	
	/**
	 * 获取系统当前默认时区与指定时区的时间差.(单位:毫秒)  
	 *
	 * @param timeZoneId
	 *            时区Id  
	 * @return 系统当前默认时区与指定时区的时间差.(单位:毫秒)
	 */
	private static int getDiffTimeZoneRawOffset(String timeZoneId) {
		return TimeZone.getDefault().getRawOffset()
				- TimeZone.getTimeZone(timeZoneId).getRawOffset();
	}
	
	/**
	 * 获取系统当前默认时区与UTC的时间差.(单位:毫秒)
	 *
	 * @return 系统当前默认时区与UTC的时间差.(单位:毫秒)
	 */
	private static int getDefaultTimeZoneRawOffset() {
		return TimeZone.getDefault().getRawOffset();
	}
	
	/**
	 * 获取所有的时区编号. <br>
	 * 排序规则:按照ASCII字符的正序进行排序. <br>
	 * 排序时候忽略字符大小写.
	 *
	 * @return 所有的时区编号(时区编号已经按照字符[忽略大小写]排序).
	 */
	public static String[] fecthAllTimeZoneIds() {
		Vector v = new Vector();
		String[] ids = TimeZone.getAvailableIDs();
		for (int i = 0; i < ids.length; i++) {
			v.add(ids[i]);
		}
		java.util.Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
		v.copyInto(ids);
		v = null;
		return ids;
	}
}