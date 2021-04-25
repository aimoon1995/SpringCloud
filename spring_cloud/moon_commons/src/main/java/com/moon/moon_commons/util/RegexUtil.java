package com.moon.moon_commons.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @Description 正则表达式工具
 * @author chensh
 * @date 2017年12月6日 上午11:18:51
 */
public class RegexUtil {
	/*** 邮件地址 ***/
	private static final String EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

	/*** 中文汉字  ***/
	private static final String CHINESE = "^[\u4e00-\u9fa5]+$";

	/**  整数       **/
	private static final String DIGIT = "\\-?[0-9]+";

	/*** 浮点数  ****/
	private static final String DECIMAL  = "\\-?[1-9]\\d+(\\.\\d+)?";

	/** 日期 **/
	private static final String DATE = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}\\1\\d{1,2}$";

	/** 网址链接 */
	private static final String URL = "^http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?$";

	/** ip地址 **/
	private static final String IP = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";

	/**
	 * 校验字符串是否是合法的身份证号<br>
	 * <font color='red'>国家早已经废弃15位身份证号，因此本校验规则只承认18位的身份证号</font>
	 * @param str 被校验的字符串
	 * @return 是合法的身份证号就返回true，否则返回false
	 */
	public static boolean checkIdCard(String str) {
		if (StringUtils.isBlank(str) || str.length() != 18) {
			return false;
		}
		str = str.toUpperCase();
		// 身份证号正则
		String idCardRegex = "^\\d{17}[\\d|X]|\\d{15}$";
		// 正则都没通过，直接返回false
		if (!Pattern.matches(idCardRegex, str)) {
			return false;
		}
		char[] chars = str.toCharArray();
		// 加权因子
		final int[] power = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };
		// 校验码数组
		final char[] verifyCode = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
		// 加权计算和值
		int num = 0;
		for (int i = 0; i < chars.length - 1; i++) {
			num += (Integer.valueOf(chars[i] + "") * power[i]);
		}
		// 计算出来的校验码
		char code = verifyCode[num % 11];
		// 提供的身份证最后一位字符
		char orgCode = chars[17];
		// 计算出来的校验码和身份证最后一位一致，则是合法身份证号
		return code == orgCode;
	}

	/**
	 * 校验字符串str是否是邮箱地址
	 * @param str 被校验的字符串
	 * @return 是邮箱地址就返回true，否则返回false
	 */
	public static boolean checkEmail(String str) {
		boolean result = Pattern.matches(EMAIL, str);
		return result;
	}

	/**
	 * 校验字符串str是否是纯中文
	 * @param str 被校验的字符串
	 * @return 是纯中文就返回true，否则返回false
	 */
	public static boolean checkChines(String str) {
		boolean result = Pattern.matches(CHINESE, str);
		return result;
	}

	/**
	 * 校验字符串str是否是整数
	 * @param str 被校验的字符串
	 * @return 是整数就返回true，否则返回false
	 */
	public static boolean checkDigital(String str) {
		boolean result = Pattern.matches(DIGIT, str);
		return result;
	}

	/**
	 * 校验字符串str是否是浮点数
	 * @param str 被校验的字符串
	 * @return 是浮点数就返回true，否则返回false
	 */
	public static boolean checkDecimal(String str) {
		boolean result = Pattern.matches(DECIMAL, str);
		return result;
	}

	/**
	 * 校验字符串str是否是合法日期
	 * @param str 被校验的字符串
	 * @return 是合法日期就返回true，否则返回false
	 */
	public static boolean checkDate(String str) {
		boolean result = Pattern.matches(DATE, str);
		return result;
	}

	/**
	 * 校验字符串str是否是正确的网址链接
	 * @param str 被校验的字符串
	 * @return 是正确的网址链接就返回true，否则返回false
	 */
	public static boolean checkUrl(String str) {
		boolean result = Pattern.matches(URL, str);
		return result;
	}

	/**
	 * 校验字符串str是否是正确的ip地址
	 * @param str 被校验的字符串
	 * @return 是正确的ip地址就返回true，否则返回false
	 */
	public static boolean checkIp(String str) {
		boolean result = Pattern.matches(IP, str);
		return result;
	}

	/**
	 * 禁止实例化工具类
	 */
	private RegexUtil() {
		throw new Error("请不要实例化RegexUtil工具类");
	}

}
