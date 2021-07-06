package com.moon.moon_commons.constants;

public interface LogConstants  {

	String LOG_LEVEL_SYSTEM = "SYSTEM";
	String LOG_LEVEL_BUSINESS = "BUSINESS";
	String LOG_LEVEL_NONE = "NONE";
	String LOG_LEVEL_AUDIT = "AUDIT";
	String LOG_LEVEL_ALL = "ALL";
	/**
	 * 系统日志存储表名
	 */
	String LOG_STORAGE_NAME_SYSTEM_LOG = "t_sys_system_log";
	/**
	 * 业务日志存储表名
	 */
	String LOG_STORAGE_NAME_BUSINESS_LOG = "t_sys_business_log";
	/**
	 * 业务日志切面：Controller
	 */
	String LOG_BUSINESS_POINTCUT_CONTROLLER = "C";
	/**
	 * 业务日志切面：Service
	 */
	String LOG_BUSINESS_POINTCUT_SERVICE = "S";
	/**
	 * 业务日志切面：Dao
	 */
	String LOG_BUSINESS_POINTCUT_DAO = "D";
}
