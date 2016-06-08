package org.llama.library.utils;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.llama.library.utils.CalendarUtil;

/**
 * 日历工具
 * 
 * @ClassName: CalendarUtil
 * @Description: 通过日历组件查询其他天的日期
 * @author Tonny
 * @date 2012-4-18 下午1:49:33
 * @version 1.0
 */
public class CalendarUtil {
	private static final String[] MONTH_NAMES = new String[] { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月",
			"十月", "十一月", "十二月" };

	private static final String[] DAY_NAMES = new String[] { "日", "一", "二", "三", "四", "五", "六" };

	Calendar calendar = null;

	Date currentDate;

	/**
	 * <p>
	 * 初始化为当前日期
	 * </p>
	 */
	public CalendarUtil() {
		calendar = Calendar.getInstance();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
	}

	/**
	 * 
	 * <p>
	 * 按指定年月日初始化日期
	 * </p>
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public CalendarUtil(int year, int month, int day) {
		calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
	}

	/**
	 * 通过日期初始化日期
	 * <p>
	 * </p>
	 * 
	 * @param date
	 */
	public CalendarUtil(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
	}

	/**
	 * 重新指定日期
	 * 
	 * @Title: reset
	 * @param year
	 * @param month
	 * @param day
	 * @date 2012-4-18 下午1:52:26
	 * @author tonny
	 * @version 1.0
	 */
	public void reset(int year, int month, int day) {
		calendar.set(year, month - 1, day);
	}

	/**
	 * 重新指定日期
	 */
	public void reset(int year, int month, int day, int hour, int min, int sec) {
		calendar.set(year, month - 1, day, hour, min, sec);
	}

	/**
	 * 重新指定日期
	 * 
	 * @Title: set
	 * @param date
	 * @date 2012-4-18 下午1:52:57
	 * @author tonny
	 * @version 1.0
	 */
	public void set(Date date) {
		calendar.setTime(date);
	}

	/**
	 * 当前年份
	 * 
	 * @Title: getYear
	 * @return
	 * @date 2012-4-18 下午1:56:32
	 * @author tonny
	 * @version 1.0
	 */
	public int getYear() {
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 当前月中文名称
	 * 
	 * @Title: getMonth
	 * @return
	 * @date 2012-4-18 下午1:56:42
	 * @author tonny
	 * @version 1.0
	 */
	public String getMonth() {
		int m = getMonthInt();
		if (m > 12)
			return "Unknown to Man";

		return MONTH_NAMES[m - 1];

	}

	/**
	 * 当前周几
	 * 
	 * @Title: getDay
	 * @return
	 * @date 2012-4-18 下午1:56:54
	 * @author tonny
	 * @version 1.0
	 */
	public String getDay() {
		int x = getDayOfWeek();
		if (x > 7)
			return "Unknown to Man";

		return DAY_NAMES[x - 1];

	}

	/**
	 * 当前月份数字，1-12
	 * 
	 * @Title: getMonthInt
	 * @return
	 * @date 2012-4-18 下午1:57:05
	 * @author tonny
	 * @version 1.0
	 */
	public int getMonthInt() {
		return calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 当前日期,yyyy-M-d格式
	 * 
	 * @Title: getDate
	 * @return
	 * @date 2012-4-18 下午1:57:22
	 * @author tonny
	 * @version 1.0
	 */
	public String getDate() {
		return getYear() + "-" + getMonthInt() + "-" + getDayOfMonth();
	}

	/**
	 * 时间，H:m:s格式
	 * 
	 * @Title: getTime
	 * @return
	 * @date 2012-4-18 下午1:59:33
	 * @author tonny
	 * @version 1.0
	 */
	public String getTime() {
		return getHour() + ":" + getMinute() + ":" + getSecond();
	}

	/**
	 * 该月的第几天，日
	 * 
	 * @Title: getDayOfMonth
	 * @return
	 * @date 2012-4-18 下午1:59:55
	 * @author tonny
	 * @version 1.0
	 */
	public int getDayOfMonth() {
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 改年的第多少天
	 * 
	 * @Title: getDayOfYear
	 * @return
	 * @date 2012-4-18 下午2:00:17
	 * @author tonny
	 * @version 1.0
	 */
	public int getDayOfYear() {
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 该年的第多少周
	 * 
	 * @Title: getWeekOfYear
	 * @return
	 * @date 2012-4-18 下午2:00:26
	 * @author tonny
	 * @version 1.0
	 */
	public int getWeekOfYear() {
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 该月的第多少周
	 * 
	 * @Title: getWeekOfMonth
	 * @return
	 * @date 2012-4-18 下午2:00:44
	 * @author tonny
	 * @version 1.0
	 */
	public int getWeekOfMonth() {
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 周几
	 * 
	 * @Title: getDayOfWeek
	 * @return
	 * @date 2012-4-18 下午2:00:59
	 * @author tonny
	 * @version 1.0
	 */
	public int getDayOfWeek() {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 几时
	 * 
	 * @Title: getHour
	 * @return
	 * @date 2012-4-18 下午2:01:09
	 * @author tonny
	 * @version 1.0
	 */
	public int getHour() {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 几分
	 * 
	 * @Title: getMinute
	 * @return
	 * @date 2012-4-18 下午2:01:17
	 * @author tonny
	 * @version 1.0
	 */
	public int getMinute() {
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 几秒
	 * 
	 * @Title: getSecond
	 * @return
	 * @date 2012-4-18 下午2:01:23
	 * @author tonny
	 * @version 1.0
	 */
	public int getSecond() {
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 时区
	 * 
	 * @Title: getZoneOffset
	 * @return
	 * @date 2012-4-18 下午2:03:48
	 * @author tonny
	 * @version 1.0
	 */
	public int getZoneOffset() {
		return calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000);
	}

	public int getDSTOffset() {
		return calendar.get(Calendar.DST_OFFSET) / (60 * 60 * 1000);
	}

	public int getAMPM() {
		return calendar.get(Calendar.AM_PM);
	}

	/**
	 * 移到本周第一天
	 * 
	 * @Title: firstDayOfWeek
	 * @date 2012-4-18 下午2:04:04
	 * @author tonny
	 * @version 1.0
	 */
	public void firstDayOfWeek() {
		calendar.set(Calendar.DATE, this.getDayOfMonth() - this.getDayOfWeek() + 1);
	}

	public void addMonth(int months) {
		calendar.add(Calendar.MONTH, months);
	}

	public void addYear(int years) {
		calendar.add(Calendar.YEAR, years);
	}

	public void addDays(int days) {
		calendar.add(Calendar.DAY_OF_MONTH, days);
	}

	public void addHours(int hours) {
		calendar.add(Calendar.HOUR_OF_DAY, hours);
	}

	public void addMinutes(int minutes) {
		calendar.add(Calendar.MINUTE, minutes);
	}

	public void addSeconds(int seconds) {
		calendar.add(Calendar.SECOND, seconds);
	}

	public void setMonth(int months) {
		calendar.set(Calendar.MONTH, months);
	}

	public void setYear(int years) {
		calendar.set(Calendar.YEAR, years);
	}

	public void setDay(int days) {
		calendar.set(Calendar.DAY_OF_MONTH, days);
	}

	public void setHour(int hours) {
		calendar.set(Calendar.HOUR_OF_DAY, hours);
	}

	public void setMinute(int minutes) {
		calendar.set(Calendar.MINUTE, minutes);
	}

	public void setSecond(int seconds) {
		calendar.set(Calendar.SECOND, seconds);
	}

	/**
	 * 是否该月最后一天
	 * 
	 * @Title: isLastDayInMonth
	 * @return
	 * @date 2012-4-18 下午2:05:44
	 * @author tonny
	 * @version 1.0
	 */
	public boolean isLastDayInMonth() {
		return this.getDayOfMonth() == this.getDaysInMonth();
	}

	/**
	 * 每个月多少天
	 * 
	 * @return String
	 */
	public int getDaysInMonth() {
		switch (this.getMonthInt()) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default:
			if (this.getYear() % 400 == 0 || (this.getYear() % 4 == 0 && this.getYear() % 100 != 0))
				return 29;
			else
				return 28;
		}
	}

	/**
	 * 当前时间
	 * 
	 * @Title: getDateTime
	 * @return
	 * @date 2012-4-18 下午2:06:11
	 * @author tonny
	 * @version 1.0
	 */
	public Date dateTime() {
		return calendar.getTime();
	}

	/**
	 * 本周第一天
	 * 
	 * @Title: firstDayOfWeek
	 * @date 2012-4-18 下午2:04:04
	 * @author tonny
	 * @version 1.0
	 */
	public static Date firstDayOfWeek(Date date) {
		CalendarUtil cal = new CalendarUtil(date);
		cal.firstDayOfWeek();
		return cal.dateTime();
	}

	/**
	 * 移动月份
	 * 
	 * @Title: prevMonth
	 * @date 2012-4-18 下午2:04:21
	 * @author tonny
	 * @version 1.0
	 */
	public static Date addMonth(Date date, int months) {
		CalendarUtil cal = new CalendarUtil(date);
		cal.addMonth(months);
		return cal.dateTime();
	}

	public static Date addYear(Date date, int years) {
		CalendarUtil cal = new CalendarUtil(date);
		cal.addYear(years);
		return cal.dateTime();
	}

	public static Date addDays(Date date, int days) {
		CalendarUtil cal = new CalendarUtil(date);
		cal.addDays(days);
		return cal.dateTime();
	}

}
