package org.llama.library.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * <p>
 * 换算公历为农历
 * </p>
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class LunarCalendar {

	private static final int[] LUNAR_INFO = { 0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0,
			0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
			0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
			0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
			0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573,
			0x052b0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950,
			0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970,
			0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50,
			0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
			0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
			0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
			0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0, 0x14b63 };

	private static final String[] YEAR_NAME = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	private static final String[] MONTH_NAME = { "", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊" };
	private static final String[] DAY_NAME = { "初", "十", "廿","三"};
	private static final String[] DAY_NAME1 = { "初十", "二十", "三十" };
	/**
	 * 农历对象，包含农历的年月日信息
	 */
	private Lunar lu;

	/**
	 * 设置日期
	 * 
	 * @param date Date date对象
	 */
	public void setTime(Date date) {
		lu.set(date);
	}

	/**
	 * 设置日期
	 * 
	 * @param date String 日期格式
	 */
	public void setTime(String date) {
		try {
			lu.set(DateUtils.parseDate(date, new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" }));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public int getYear() {
		return lu.year;
	}

	public int getMonth() {
		return lu.month;
	}

	public int getDay() {
		return lu.day;
	}

	public LunarCalendar() {
		lu = new Lunar();
	}

	public String getYearName() {
		String year = Integer.toString(lu.year);
		StringBuffer yearName = new StringBuffer();
		for (int i = 0; i < year.length(); i++) {
			yearName.append(YEAR_NAME[NumberUtils.toInt(year.substring(i, i + 1), 0)]);
		}
		return yearName.toString();
	}

	public String getMonthName() {
		return (lu.isLeap ? "闰" : "") + MONTH_NAME[lu.month] + "月";
		// lu.isLeap?"闰":""+
	}

	public boolean isLeap() {
		return lu.isLeap;
	}

	public String getDayName() {
		if (lu.day % 10 == 0)
			return DAY_NAME1[lu.day / 10 - 1];

		String day = lu.day > 10 ? Integer.toString(lu.day) : "0" + Integer.toString(lu.day);
		return DAY_NAME[NumberUtils.toInt(day.substring(0, 1), 0)]
				+ YEAR_NAME[NumberUtils.toInt(day.substring(1, 2), 0)];
	}

	/**
	 * 获取某年的天数，正常为348天，闰月多
	 * 
	 * @param y int
	 * @return int
	 */
	private int lYearDays(int y) {
		int sum = 348;
		for (int i = 0x8000; i > 0x8; i >>= 1) {
			sum += (LUNAR_INFO[y - 1900] & i) != 0 ? 1 : 0;
		}
		return (sum + leapDays(y));
	}

	/**
	 * 获取某年闰月天数
	 * 
	 * @param y int
	 * @return int
	 */
	private int leapDays(int y) {
		if (leapMonth(y) != 0) {
			return ((LUNAR_INFO[y - 1900] & 0x10000) != 0 ? 30 : 29);
		} else {
			return (0);
		}
	}

	/**
	 * 获取闰月月份
	 * 
	 * @param y int
	 * @return int
	 */
	private int leapMonth(int y) {
		return (LUNAR_INFO[y - 1900] & 0xf);
	}

	/**
	 * 获取某年某月的天数
	 * 
	 * @param y int 年
	 * @param m int 月
	 * @return int 天数
	 */
	private int monthDays(int y, int m) {
		return ((LUNAR_INFO[y - 1900] & (0x10000 >> m)) != 0 ? 30 : 29);
	}

	/**
	 * 传入公历日期，返回农历对象，包含年月日
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2005
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author not attributable
	 * @version 1.0
	 */
	private class Lunar {
		public int year;
		public boolean isLeap;
		public int month;
		public int day;

		public Lunar() {
			set(new Date());
		}

		/**
		 * 设定日期
		 * 
		 * @param objDate Date
		 */
		public void set(Date objDate) {
			int i, leap = 0, temp = 0;
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(objDate);
			cal1.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
			long l = cal1.getTime().getTime();
			cal1.set(1900, 0, 31);
			long m = cal1.getTime().getTime();
			long offset = (l - m) / 86400000;
			for (i = 1900; i < 2050 && offset > 0; i++) {
				temp = lYearDays(i);
				offset -= temp;
			}
			if (offset < 0) {
				offset += temp;
				i--;
			}
			this.year = i;
			leap = leapMonth(i); // 闰哪个月
			this.isLeap = false;
			for (i = 1; i < 13 && offset > 0; i++) {
				// 闰月
				if (leap > 0 && i == (leap + 1) && this.isLeap == false) {
					--i;
					this.isLeap = true;
					temp = leapDays(this.year);
				} else {
					temp = monthDays(this.year, i);
				}

				// 解除闰月
				if (this.isLeap == true && i == (leap + 1)) {
					this.isLeap = false;
				}

				offset -= temp;
			}
			if (offset == 0 && leap > 0 && i == leap + 1) {
				if (this.isLeap) {
					this.isLeap = false;
				} else {
					this.isLeap = true;
					--i;
				}
			}

			if (offset < 0) {
				offset += temp;
				--i;
			}

			this.month = i;
			this.day = (int) offset + 1;

		}
	}

	public static void main(String[] argv) {

	}

}
