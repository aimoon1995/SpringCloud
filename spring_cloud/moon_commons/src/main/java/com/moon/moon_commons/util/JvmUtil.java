package com.moon.moon_commons.util;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;


/**
 * JVM工具类
 * @author 浩
 *
 */
@Slf4j
public class JvmUtil {
	
	/**
	 * 正常关闭VM
	 */
	public static void shutdown(){
		new Thread(){
			@Override
			public void run(){
				try {
					log.info("Application will shutdown in 5 seconds.");
					Thread.sleep(5000);
					System.exit(0);
				} catch (InterruptedException e) {
					log.error("Shutdown application failure", e);
				}
			};
		}.start();
	}
	
	/**
	 * 启动非受管进程(主进程退出后不影响子进程继续运行)
	 * @return 启动结果
	 */
	public static boolean startupProcessUnmanaged(final String target,final String cmd){
		boolean ret = true;
		log.info(StringUtil.formatMessage("在Target[{0}]上启动[{1}]", target, cmd));
		new Thread(){
			@Override
			public void run() {
				try {
					if(StringUtil.isEmpty(target) || "localhost".equalsIgnoreCase(target)){
						try{
							Desktop.getDesktop().open(new File(cmd));
						}catch(IllegalArgumentException e){
							// 带参数时，只能以普通方式启动
							log.warn("桌面方式启动失败，正在以普通方式启动（无命令行界面）");
							Runtime.getRuntime().exec(cmd);
							Thread.sleep(5000);
							log.warn("启动完成");
						}
					} else {
						log.error("暂不支持此Target，请使用空或localhost");
					}
				} catch (Exception e) {
					log.error("启动进程失败", e);
				}
			}
		}.start();
		return ret;
	}
}
