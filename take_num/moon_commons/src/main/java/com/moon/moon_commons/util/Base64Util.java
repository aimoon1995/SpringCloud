package com.moon.moon_commons.util;

import java.util.Base64;

public class Base64Util {

    /**
     * 将给定字符串进行base64编码
     *
     * @author chensh
     */
    public static final String encode(String str) {
        String result = Base64.getEncoder().encodeToString(str.getBytes());
        return result;
    }

    /**
     * 将给定字节数组进行base64编码,返回base64字符串
     *
     * @author chensh
     */
    public static final String encode(byte[] bytes) {
        String result = Base64.getEncoder().encodeToString(bytes);
        return result;
    }

    /**
     * 将给定的base64字符串解码
     *
     * @author chensh
     */
    public static final String decode(String base64Str) {
        byte[] bytes = Base64.getDecoder().decode(base64Str);
        return new String(bytes);
    }

    /**
     * 将给定的base字符串解码
     *
     * @author chensh
     */
    public static final String decode(byte[] bytes) {
        byte[] resultBytes = Base64.getDecoder().decode(bytes);
        return new String(resultBytes);
    }

    /**
     * 禁止实例化工具类
     */
    private Base64Util() {
        throw new Error("请不要实例化Base64Util工具类");
    }

}
