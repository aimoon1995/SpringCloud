package com.moon.moon_commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类
 * 
 * @author 浩
 *
 */
public class DateUtil {

	static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	/** 日期格式：yy/MM/dd */
	public static final String DATE_FORMAT_DATE_S_0 = "yy/MM/dd";
	/** 日期格式：yyyy/MM/dd */
	public static final String DATE_FORMAT_DATE_S_1 = "yyyy/MM/dd";
	/** 日期格式：yy-MM-dd */
	public static final String DATE_FORMAT_DATE_M_0 = "yy-MM-dd";
	/** 日期格式：yyyy-MM-dd */
	public static final String DATE_FORMAT_DATE_M_1 = "yyyy-MM-dd";
	/** 日期格式：yy.MM.dd */
	public static final String DATE_FORMAT_DATE_D_0 = "yy.MM.dd";
	/** 日期格式：yyyy.MM.dd */
	public static final String DATE_FORMAT_DATE_D_1 = "yyyy.MM.dd";
	/** 日期时间格式：yyyy/MM/dd HH */
	public static final String DATE_FORMAT_DATETIME_S_H = "yyyy/MM/dd HH";
	/** 日期时间格式：yyyy/MM/dd HH:mm */
	public static final String DATE_FORMAT_DATETIME_S_HM = "yyyy/MM/dd HH:mm";
	/** 日期时间格式：yyyy/MM/dd HH:mm:ss */
	public static final String DATE_FORMAT_DATETIME_S_HMS = "yyyy/MM/dd HH:mm:ss";
	/** 日期时间格式：yyyy/MM/dd HH:mm:ss:SSS */
	public static final String DATE_FORMAT_DATETIME_S_HMSS = "yyyy/MM/dd HH:mm:ss:SSS";
	/** 日期时间格式：yyyy-MM-dd HH */
	public static final String DATE_FORMAT_DATETIME_M_H = "yyyy-MM-dd HH";
	/** 日期时间格式： yyyy-MM-dd HH:mm */
	public static final String DATE_FORMAT_DATETIME_M_HM = "yyyy-MM-dd HH:mm";
	/** 日期时间格式： yyyy-MM-dd HH:mm:ss */
	public static final String DATE_FORMAT_DATETIME_M_HMS = "yyyy-MM-dd HH:mm:ss";
	/** 日期时间格式： yyyy-MM-dd HH:mm:ss:SSS */
	public static final String DATE_FORMAT_DATETIME_M_HMSS = "yyyy-MM-dd HH:mm:ss:SSS";
	/** 日期时间格式： yyyy.MM.dd HH */
	public static final String DATE_FORMAT_DATETIME_D_H = "yyyy.MM.dd HH";
	/** 日期时间格式： yyyy.MM.dd HH:mm */
	public static final String DATE_FORMAT_DATETIME_D_HM = "yyyy.MM.dd HH:mm";
	/** 日期时间格式： yyyy.MM.dd HH:mm:ss */
	public static final String DATE_FORMAT_DATETIME_D_HMS = "yyyy.MM.dd HH:mm:ss";
	/** 日期时间格式： yyyy.MM.dd HH:mm:ss:SSS */
	public static final String DATE_FORMAT_DATETIME_D_HMSS = "yyyy.MM.dd HH:mm:ss:SSS";
	/**
	 * 简短日期格式:yyyy
	 */
	public static final String DATE_FORMAT_SHORT_YYYY = "yyyy";
	/**
	 * 简短日期格式:yyyyMM
	 */
	public static final String DATE_FORMAT_SHORT_YYYYMM = "yyyyMM";
	/**
	 * 简短日期格式:yyyyMMdd
	 */
	public static final String DATE_FORMAT_SHORT_YYYYMMDD = "yyyyMMdd";
	/**
	 * 简短日期格式:yyyyMMddHH
	 */
	public static final String DATE_FORMAT_SHORT_YYYYMMDDHH = "yyyyMMddHH";
	/**
	 * 简短日期格式:yyyyMMddHHmm
	 */
	public static final String DATE_FORMAT_SHORT_YYYYMMDDHHMM = "yyyyMMddHHmm";
	/**
	 * 简短日期格式:yyyyMMddHHmmss
	 */
	public static final String DATE_FORMAT_SHORT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	/**
	 * 简短日期格式:yyyyMMddHHmmssSSS
	 */
	public static final String DATE_FORMAT_SHORT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	/**
	 * 简短日期格式长度YYYYMMDDHH
	 */
	private static final int SHORT_YYYYMMDDHH_LENGTH = 10;
	/**
	 * 简短日期格式长度YYYYMMDDHHMM
	 */
	private static final int SHORT_YYYYMMDDHHMM_LENGTH = 12;
	/**
	 * 简短日期格式长度YYYYMMDDHHMMSS
	 */
	private static final int SHORT_YYYYMMDDHHMMSS_LENGTH = 14;

	/**
	 * 格式化
	 * 
	 * @param pDate
	 *            Date
	 * @param datePattern
	 *            格式化模板
	 * @return String 格式化字符
	 */
	public static String format(Date pDate, String datePattern) {
		if (StringUtil.isEmpty(datePattern) || pDate == null) {
			return "";
		}

		return new SimpleDateFormat(datePattern).format(pDate);
	}

	/**
	 * 功能:发布时间格式化为什么时间前发布
	 * 
	 * 开发:YangDong 2017年3月10日
	 * 
	 * @param date
	 *            Date
	 * @return
	 */
	public static String formatDescription(Date date) {
		if (date != null) {
			long estime = getSysDate().getTime() - date.getTime();
			if (estime < 3600000) {
				// time一个小时内
				int t = Math.round(estime / 60000);
				return t <= 0 ? "刚刚" : (t + " 分钟前");
			} else if (estime < 86400000) {
				// time一天内
				return Math.round(estime / 3600000) + " 小时前";
			} else if (estime < 604800000) {
				// time在7天内
				return Math.round(estime / 86400000) + " 天前";
			}

			return format(date, DATE_FORMAT_DATETIME_S_HM);
		}
		return "";
	}

	/**
	 * 转换常见格式日期字段，因为不确定性所以不建议使用，未来会移除
	 * 
	 * @param dateStr
	 *            日期字符
	 * @return 日期
	 */
	@Deprecated
	public static Date parse(String dateStr) {
		Date date = null;
		if (StringUtil.isNotEmpty(dateStr)) {
			// 防止将两位的月份，日期，时分秒等只用一位处理
			String[] params = StringUtil.split(dateStr, "- .:/");
			for (int i = 0; i < params.length; i++) {
				if (params[i].length() == 1 && "0123456789".indexOf(params[i]) > -1) {
					params[i] = "0" + params[i];
				}
			}
			String shortDateStr = StringUtil.join(params);
			int shortDateStrLen = shortDateStr.length();
			String formatStr = DATE_FORMAT_SHORT_YYYYMMDD;
			switch (shortDateStrLen) {
			case SHORT_YYYYMMDDHHMMSS_LENGTH:
				formatStr = DATE_FORMAT_SHORT_YYYYMMDDHHMMSS;
				break;
			case SHORT_YYYYMMDDHHMM_LENGTH:
				formatStr = DATE_FORMAT_SHORT_YYYYMMDDHHMM;
				break;
			case SHORT_YYYYMMDDHH_LENGTH:
				formatStr = DATE_FORMAT_SHORT_YYYYMMDDHH;
				break;
			default:
				formatStr = DATE_FORMAT_SHORT_YYYYMMDD;
				break;
			}
			date = parse(shortDateStr, formatStr);
		}
		return date;
	}

	/**
	 * 从日期字符转换为日期类型
	 * 
	 * @param dateStr
	 *            日期字符
	 * @param format
	 *            日期格式化模板
	 * @return 日期
	 */
	public static Date parse(String dateStr, String format) {
		Date date = null;
		if (StringUtil.isNotEmpty(dateStr)) {
			try {
				date = new SimpleDateFormat(format).parse(dateStr);
			} catch (ParseException ex) {
				logger.error("DateParseError", ex);
			}
		}
		return date;
	}

	/**
	 * 转换JAVA日期格式化模板到Javascript
	 * 
	 * @param javaformat
	 *            JAVA日期格式化模板
	 * @return Javascript日期格式化模板
	 */
	public static String toJavascriptFormat(String javaformat) {
		String result = javaformat;
		if (StringUtil.isNotEmpty(javaformat)) {
			if (javaformat.indexOf("i") > -1) {
				result = javaformat;
			} else if (javaformat.indexOf("M") > -1) {
				result = StringUtil.replace(javaformat, "mm", "ii");
				result = StringUtil.replace(result, "m", "i");
				result = StringUtil.replace(javaformat, "MM", "mm");
				result = StringUtil.replace(javaformat, "M", "m");
			}
			result = StringUtil.replace(result, "HH", "hh");
			result = StringUtil.replace(result, "H", "h");
		}
		return result;
	}

	/**
	 * 日期年加运算
	 * 
	 * @param date
	 *            原日期
	 * @param yearAmount
	 *            年
	 * @return 新日期
	 */
	public static Date addYears(Date date, int yearAmount) {
		return add(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 日期月加运算
	 * 
	 * @param date
	 *            原日期
	 * @param monthAmount
	 *            月
	 * @return 新日期
	 */
	public static Date addMonths(Date date, int monthAmount) {
		return add(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 日期日加运算
	 * 
	 * @param date
	 *            原日期
	 * @param dayAmount
	 *            日
	 * @return 新日期
	 */
	public static Date addDays(Date date, int dayAmount) {
		return add(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 日期加运算
	 * 
	 * @param date
	 *            原日期
	 * @param field
	 *            Calendar位置
	 * @param amount
	 *            数值
	 * @return 新日期
	 */
	public static Date add(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	/**
	 * 取系统时间
	 * 
	 * @return 系统时间
	 */
	public static Date getSysDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 取系统时间字符
	 * 
	 * @return 系统时间字符
	 */
	public static String getSysDateString() {
		return getSysDateString(DATE_FORMAT_SHORT_YYYYMMDDHHMMSS);
	}

	/**
	 * 取系统时间字符
	 * 
	 * @param format
	 *            日期格式化
	 * @return 系统时间字符
	 */
	public static String getSysDateString(String format) {
		return format(getSysDate(), format);
	}

	/**
	 * 取中点时间
	 * 
	 * @param firstDate
	 *            开始时间
	 * @param secendDate
	 *            结束时间
	 * @return 中点时间
	 */
	public static Date getMiddleDate(Date firstDate, Date secendDate) {
		if (firstDate == null || secendDate == null) {
			return null;
		}
		long time = secendDate.getTime() - firstDate.getTime();
		return add(firstDate, Calendar.MILLISECOND, (int) time);
	}

	/**
	 * 转换为不含时分秒日期
	 * 
	 * @param date
	 *            含时分秒日期
	 * @return 不含时分秒日期
	 */
	public static Date getDateByDatetime(Date date) {
		if (date == null) {
			return null;
		}
		return parse(format(date, DATE_FORMAT_SHORT_YYYYMMDD), DATE_FORMAT_SHORT_YYYYMMDD);
	}

	/**
	 * 功能:判断指定的字符串是否是指定的日期格式
	 * 
	 * @param strDate
	 *            字符串
	 * @param datePattern
	 *            日期格式
	 * @return true/false
	 */
	public static boolean isValidateDate(String strDate, String datePattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
			sdf.setLenient(false);
			sdf.parse(strDate);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 取当年最后一天
	 * 
	 * @param date
	 *            参考日期
	 * @return 当年最后一天
	 */
	public static Date getYearLastDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, Calendar.DECEMBER);
		return getMonthLastDay(calendar.getTime());
	}

	/**
	 * 取当年第一天
	 * 
	 * @param date
	 *            参考日期
	 * @return 当年第一天
	 */
	public static Date getYearFirstDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, Calendar.JANUARY);
		return getMonthFirstDay(calendar.getTime());
	}

	/**
	 * 当月最后一天
	 * 
	 * @param date
	 *            参考日期
	 * @return 当月最后一天
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		String dateStr = format(date, DATE_FORMAT_SHORT_YYYYMMDD);
		String lastDay = dateStr.substring(0, 6) + String.valueOf(calendar.getActualMaximum(Calendar.DATE));
		return parse(lastDay, DATE_FORMAT_SHORT_YYYYMMDD);
	}

	/**
	 * 当月第一天
	 * 
	 * @param date
	 *            参考日期
	 * @return 当月第一天
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);
		String dateStr = format(date, DATE_FORMAT_SHORT_YYYYMMDD);
		String lastDay = dateStr.substring(0, 6) + String.valueOf(calendar.getActualMinimum(Calendar.DATE));
		return parse(lastDay, DATE_FORMAT_SHORT_YYYYMMDD);
	}

	/**
	 * 得到日期所在星期的星期一
	 * 
	 * @param date
	 * @return 星期一
	 */
	public static Date getMondayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		Date result = null;
		if (weekDay == 1) {
			result = addDays(date, -6);
		} else {
			result = addDays(date, 2 - weekDay);
		}
		result = parse(format(result, DATE_FORMAT_SHORT_YYYYMMDD), DATE_FORMAT_SHORT_YYYYMMDD);
		return result;
	}

	/**
	 * 得到日期下周的星期一
	 * 
	 * @param date
	 * @return 下周的星期一
	 */
	public static Date getNextMondayOfWeek(Date date) {
		Date result = getMondayOfWeek(date);
		return addDays(result, 7);
	}

	public static Date getEndOfTheDay(Date date) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			return calendar.getTime();
		}
		return date;
	}

	/**
	 * 得到日期明日的开始日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTomorrowStart(Date date) {
		Date result = parse(format(date, DATE_FORMAT_SHORT_YYYYMMDD), DATE_FORMAT_SHORT_YYYYMMDD);
		return addDays(result, 1);
	}

	/**
	 * 取最大日期
	 * 
	 * @param dates
	 *            日期组
	 * @return 最大日期
	 */
	public static Date max(Date... dates) {
		if (dates == null || dates.length == 0) {
			return null;
		}
		Date result = dates[0];
		for (Date one : dates) {
			if (result == null) {
				result = one;
			}
			if (one != null && result.before(one)) {
				result = one;
			}
		}
		return result;
	}

	/**
	 * 取最小日期
	 * 
	 * @param dates
	 *            日期组
	 * @return 最小日期
	 */
	public static Date min(Date... dates) {
		if (dates == null || dates.length == 0) {
			return null;
		}
		Date result = dates[0];
		for (Date one : dates) {
			if (result == null) {
				result = one;
			}
			if (one != null && result.after(one)) {
				result = one;
			}
		}
		return result;
	}

}
