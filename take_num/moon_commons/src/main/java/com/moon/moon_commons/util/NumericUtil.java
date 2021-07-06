package com.moon.moon_commons.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数值工具类
 * @author 浩
 *
 */
public class NumericUtil {

	/**
	 * 取两个值中较小值
	 * @param a
	 * @param b
	 * @return 较小值
	 */
	public static BigDecimal min(BigDecimal a, BigDecimal b){
		if(a == null) {
			return b;
		}
		if(b == null) {
			return a;
		}
		return a.compareTo(b) > 0 ? b : a;
	}

	/**
	 * 取两个值中较大值
	 * @param a
	 * @param b
	 * @return 较大值
	 */
	public static BigDecimal max(BigDecimal a, BigDecimal b){
		if(a == null) {
			return b;
		}
		if(b == null) {
			return a;
		}
		return a.compareTo(b) > 0 ?  a : b;
	}
	
	/**
	 * 字符串转换为整型
	 * @param value 字符串
	 * @return 整型
	 */
	public static Integer toInteger(String value){
		int i = 0;
		try{
			i = Integer.parseInt(StringUtil.getOrElse(value).trim());
		}catch(Exception ex){}
		return i;
	}

	/**
	 * 字符串转换为长整型
	 * @param value 字符串
	 * @return 长整型
	 */
	public static Long toLong(String value){
		long i = 0;
		try{
			i = Long.parseLong(StringUtil.getOrElse(value).trim());
		}catch(Exception ex){}
		return i;
	}
	
	/**
	 * 格式化数字
	 * @param number 输入
	 * @return 格式化数字
	 */
	public static String formatNumber(BigDecimal number){
		return formatNumber(number, "");
	}

	/**
	 * 格式化数字
	 * @param number 输入
	 * @param unit 单位
	 * @return 格式化数字
	 */
	public static String formatNumber(BigDecimal number, String unit){
		if(number == null){
			return "0 " + unit;
		}
		NumberFormat nf = new DecimalFormat("#,###.## ");
		return nf.format(number) + unit;
	}

	/**
	 * 比较num否比compareNum大
	 * @param num 被比较的数
	 * @param compareNum 比较的数
	 * @return
	 */
	public static Boolean biggerThan(BigDecimal num, Integer compareNum){
		if(num == null) {
			return false;
		}
		if(compareNum == null) {
			return true;
		}
		return num.compareTo(new BigDecimal(compareNum)) > 0;
	}

	/**
	 * 比较num否比compareNum小
	 * @param num 被比较的数
	 * @param compareNum 比较的数
	 * @return
	 */
	public static Boolean smallerThan(BigDecimal num, Integer compareNum){
		if(num == null && compareNum != null) {
			return true;
		}
		if(compareNum == null) {
			return false;
		}
		return num.compareTo(new BigDecimal(compareNum)) < 0;
	}
}
