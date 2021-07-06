package com.moon.moon_commons.util;

/**
 * ID生成工具类
 * @author 浩
 *
 */
public class IDGenerator {

	/**
	 * 生成UUID
	 * @return UUID
	 */
	public final static String genUID(){
		return UUIDGenerator.get();
	}
	
	/**
	 * 生成不含-的UUID
	 * @return UUID
	 */
	public final static String genDomID(){
		return genUID().replaceAll("[-]", "");
	}
}
