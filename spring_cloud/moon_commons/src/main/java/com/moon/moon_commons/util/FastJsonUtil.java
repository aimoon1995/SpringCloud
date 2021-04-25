package com.moon.moon_commons.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * JSON工具类
 */
@Slf4j
public final class FastJsonUtil {

    /**
     * 将对象序列化为json字符串
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 将json字符串转化为指定类的实例
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 将json字符串转为指定类的list集合
     */
    public static <T> List<T> parseList(String jsonStr, Class<T> clazz) {
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     * 将json字符串封装成一个map集合<br>
     * <font color=red>map的value只可能是String或者Map</font>
     */
	public static Map<String, Object> toMap(String jsonStr) {
		JSONObject obj = null;
		try {
			obj = JSONObject.parseObject(jsonStr);
		} catch (Exception e) {
			obj = new JSONObject();
			log.error("将json字符串[{}]转化为Map集合发生异常", jsonStr, e);
		}
		final Map<String, Object> resultMap = new HashMap<>(obj.size());
		// 遍历jsonObj，转化为指定类型
		for (Entry<String, Object> en : obj.entrySet()) {
			Object value = en.getValue();
			// 如果是JSONObject，则转化
			if (value instanceof JSONObject) {
				value = toMap(((JSONObject) value).toJSONString());
			} else {
				// String化，防止空指针
				value = String.valueOf(value);
			}
			resultMap.put(en.getKey(), value);
		}
		return resultMap;
	}

	/*****禁止实例化工具类******/
    private FastJsonUtil() {
        throw new Error("请不要实例化JsonUtil工具类");
    }

}
