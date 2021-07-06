package com.moon.moon_commons.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置文件工具类
 */
@Slf4j
public final class PropertiesUtil {

    // 文件名 ---> property对象
    private static final Map<String, Properties> FILENAME_PROPERTY_MAP = new ConcurrentHashMap<>();

    /**
     * 获取property实例
     * @param fileName 配置文件的名称
     */
    public static Properties getInstance(String fileName) {
        loadPropertiesFile(fileName);
        return FILENAME_PROPERTY_MAP.get(fileName);
    }

    /**
     * 载入配置文件，读取里面的配置并存储
     * @param fileName 需要载入的配置文件名称
     */
    private static void loadPropertiesFile(String fileName) {
        // 判断是否已经加载过该配置文件
        if (FILENAME_PROPERTY_MAP.get(fileName) != null) {
            return;
        }
        InputStreamReader input = null;
        try {
            // 用类加载器加载classpath下的文件
            InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
            input = new InputStreamReader(is, "UTF-8");
            // 将文件内容载入properties对象
            Properties p = new Properties();
            p.load(input);
            FILENAME_PROPERTY_MAP.put(fileName, p);
        } catch (IOException e) {
            log.error("发生异常", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

}