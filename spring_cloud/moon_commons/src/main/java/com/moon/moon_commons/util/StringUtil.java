package com.moon.moon_commons.util;

import org.apache.commons.lang3.StringUtils;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends org.apache.commons.lang3.StringUtils {

    public static final String ENCODE_UTF_8 = "utf-8";

    public static final String BLANK = "";

    public static final char SPACE = ' ';
    public static final String FILE_NAME_EXT_SPLIT = ".";
    public static final String PATH_SLASH = "/";
    public static final String PATH_BACKSLASH = "\\\\";

    /**
     * 返回非NULL字符串
     *
     * @param value 输入值
     * @return 返回字符串
     */
    public static final String getOrElse(Object value) {
        return getOrElse(value, EMPTY);
    }

    /**
     * 返回非NULL字符串
     *
     * @param value        输入值
     * @param defaultValue 默认值
     * @return 返回字符串
     */
    public static final String getOrElse(Object value, String defaultValue) {
        if (value != null && isNotEmpty(value.toString())) {
            return value.toString();
        } else if (isNotEmpty(defaultValue)) {
            return defaultValue;
        }
        return BLANK;
    }

    /**
     * 返回非NULL字符串并Trim
     *
     * @param value        输入值
     * @param defaultValue 默认值
     * @return 返回字符串
     */
    public static final String getOrElseTrim(Object value, String defaultValue) {
        if (value != null && isNotEmpty(value.toString())) {
            return value.toString().trim();
        } else if (StringUtil.isNotEmpty(defaultValue)) {
            return defaultValue.trim();
        }
        return BLANK;
    }

    /**
     * 返回非NULL字符串并Trim
     *
     * @param value 输入值
     * @return 返回字符串
     */
    public static final String getOrElseTrim(Object value) {
        return getOrElseTrim(value, BLANK);
    }

    /**
     * 返回UUID
     *
     * @return UUID
     */
    public static final String uuid() {
        return IDGenerator.genUID();
    }

    /**
     * 根据情况返回不同值
     *
     * @param check     检查值
     * @param okvalue   候选值:真
     * @param elsevalue 修选值:假
     * @return 返回值
     */
    public static final String nval(boolean check, String okvalue, String elsevalue) {
        return (check ? okvalue : elsevalue);
    }

    /**
     * 根据假查值是否为空返回不同值
     *
     * @param check     检查值
     * @param okvalue   候选值:真
     * @param elsevalue 修选值:假
     * @return 返回值
     */
    public static final String nval(String check, String okvalue, String elsevalue) {
        return nval(isNotEmpty(check), okvalue, elsevalue);
    }

    /**
     * 根据假查值是否为空返回不同值
     *
     * @param check   检查值
     * @param okvalue 候选值:真
     * @return 返回值
     */
    public static final String nval(String check, String okvalue) {
        return nval(check, okvalue, BLANK);
    }

    /**
     * 根据情况返回值
     *
     * @param check   检查值
     * @param okvalue 候选值:真
     * @return 返回值
     */
    public static final String nval(Boolean check, String okvalue) {
        return nval(check, okvalue, BLANK);
    }

    /**
     * 生成Mask模板
     *
     * @param intLength   整数部分长度
     * @param scaleLength 小数部分长度
     * @return Mask模板
     */
    public static final String buildMask(int intLength, int scaleLength) {
        int intCntMod = intLength % 3;
        int intCnt = intLength / 3;
        if (intCntMod != 0) {
            intCnt += 1;
        }
        String[] masks = new String[intCnt];
        for (int i = 0; i < intCnt; i++) {
            masks[i] = "000";
        }
        String mask = join(masks, ",");
        if (scaleLength > 0) {
            mask += "." + StringUtil.buildString(scaleLength, '0');
        }
        return mask;
    }

    /**
     * 根据最大值生成Mask模板
     *
     * @param max         最大值
     * @param scaleLength 小数部分长度
     * @return Mask模板
     */
    public static final String buildMaskByMax(int max, int scaleLength) {
        int intLength = String.valueOf(max).length();
        return buildMask(intLength, scaleLength);
    }

    /**
     * 分割字符串
     * 匹配";,|\/"
     *
     * @param value 输入
     * @return 输出
     */
    public static final String[] split(String value) {
        return split(value, " ;,|\\/");
    }

    /**
     * 固定分割为两段
     *
     * @param old     输入
     * @param splitor 分割字符
     * @return 输出
     */
    public static final String[] splitTwo(String old, String splitor) {
        String[] result = new String[2];
        if (isNotEmpty(old)) {
            int index = old.indexOf(splitor);
            if (index > -1) {
                result[0] = old.substring(0, index);
                result[1] = old.substring(index + splitor.length());
            } else {
                result[0] = old;
                result[1] = BLANK;
            }
        }
        return result;
    }

    /**
     * 固定分割为两段(从末尾匹配)
     *
     * @param old     输入
     * @param splitor 分割字符
     * @return 输出
     */
    public static final String[] lastSplitTwo(String old, String splitor) {
        String[] result = new String[2];
        if (isNotEmpty(old)) {
            int index = old.lastIndexOf(splitor);
            if (index > -1) {
                result[0] = old.substring(0, index);
                result[1] = old.substring(index + 1);
            } else {
                result[0] = old;
                result[1] = BLANK;
            }
        }
        return result;
    }

    /**
     * 末尾匹配分割后半部分
     *
     * @param old     输入
     * @param splitor 分割字符
     * @return 输出
     */
    public static final String lastSplitValue(String old, String splitor) {
        String result = BLANK;
        if (isNotEmpty(old)) {
            int index = old.lastIndexOf(splitor);
            if (index > -1) {
                result = old.substring(index + 1);
            } else {
                result = old;
            }
        }
        return result;
    }

    /**
     * 重复指定次数生成字符串
     *
     * @param len       重复次数
     * @param appendStr 重复字符串
     * @return 生成字符串
     */
    public static String buildString(int len, char appendStr) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < len; i++) {
            buf.append(appendStr);
        }
        return buf.toString();
    }

    /**
     * 补齐文本到指定长度
     *
     * @param value     原文本
     * @param fixLength 补齐长度
     * @param fixChar   补齐字符
     * @param fixLeft   是否左补齐
     * @return 返回字符串
     */
    public static String pad(String value, int fixLength, char fixChar, Boolean fixLeft) {
        String v = getOrElse(value);
        if (fixLeft) {
            //左边填充
            return buildString(fixLength - v.length(), fixChar) + v;
        } else {
            return getOrElse(v) + buildString(fixLength - v.length(), fixChar);
        }
    }

    /**
     * 左补齐文本到指定长度
     *
     * @param value     原文本
     * @param fixLength 补齐长度
     * @param fixChar   补齐字符
     * @return 返回字符串
     */
    public static String lpad(String value, int fixLength, char fixChar) {
        return pad(value, fixLength, fixChar, true);
    }

    /**
     * 右补齐文本到指定长度
     *
     * @param value     原文本
     * @param fixLength 补齐长度
     * @param fixChar   补齐字符
     * @return 返回字符串
     */
    public static String rpad(String value, int fixLength, char fixChar) {
        return pad(value, fixLength, fixChar, false);
    }

    public static String replaceByIndex(String value, int index, String replacement) {
        int end = index + replacement.length();
        String before = value.substring(0, index);
        String after = value.substring(end);
        return before + replacement + after;
    }

    /**
     * 根据属性名生成get方法名
     *
     * @param temp 属性名
     * @return get方法名
     */
    public static String makeGetMethod(String temp) {
        return "get" + toUpperInitial(temp);
    }

    /**
     * 首字母大写
     *
     * @param temp 原字符串
     * @return 首字母大写字符串
     */
    public static String toUpperInitial(String temp) {
        return temp.substring(0, 1).toUpperCase() + temp.substring(1);
    }

    /**
     * 根据起始，结束标记取参数
     *
     * @param value     原字符串
     * @param startSign 起始标记
     * @param endSign   结束标记
     * @return 参数
     */
    public static List<String> getParamNames(String value, String startSign, String endSign) {
        TokenParser parser = new TokenParser(startSign, endSign);
        return parser.getTokens(value);
    }


    /**
     * 根据起始，结束标记取参数
     *
     * @param value     原字符串
     * @param startSign 起始标记
     * @param endSign   结束标记
     * @param ignoreDup 是否去重
     * @return 参数
     */
    public static List<String> getParamNames(String value, String startSign, String endSign, boolean ignoreDup) {
        TokenParser parser = new TokenParser(startSign, endSign);
        return parser.getTokens(value, ignoreDup);
    }

    /**
     * 全部替换
     *
     * @param value        原字符串
     * @param searchString 查找字符串
     * @param replacement  替换字符串
     * @return 替换后字符串
     */
    public static String replaceRepeat(String value, String searchString, String replacement) {
        String result = value;
        if (isNotEmpty(value)) {
            do {
                result = replace(result, searchString, replacement);
            } while (!result.equals(replace(result, searchString, replacement)));
        }
        return result;
    }

    /**
     * 字符串转换为布尔值
     *
     * @param value 字符串
     * @return 布尔值
     */
    public static Boolean toBoolean(String value) {
        boolean result = false;
        if (isNotEmpty(value) && ("1".equals(value)
                || "true".equalsIgnoreCase(value)
                || "是".equalsIgnoreCase(value)
                || "ok".equalsIgnoreCase(value)
                || "yes".equalsIgnoreCase(value)
                || "y".equalsIgnoreCase(value)
                || "on".equalsIgnoreCase(value))) {
            result = true;
        }
        return result;
    }

    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underScoreName(String str) {
        if (StringUtil.isEmpty(str)
                || str.indexOf(".") > -1
                || str.indexOf("_") > -1
                || str.indexOf("-") > -1) {
            return str;
        }
        String name = str.trim();
        if (StringUtil.isEmpty(name)) {
            return str;
        }
        String firstChar = name.substring(0, 1);
        if (firstChar.equals(firstChar.toUpperCase())) {
            //首写字母大写
            return str;
        }
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && Character.isLetter(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return BLANK;
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.toLowerCase();
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 转换驼峰，仅转换第一个
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelFirst(String name) {
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return BLANK;
        }
        String result = BLANK;
        if (name.length() == 0) {
            // 第一个驼峰片段，全部字母都小写
            result = name.toLowerCase();
        } else {
            // 其他的驼峰片段，首字母大写
            result = name.substring(0, 1).toUpperCase();
            result = result + name.substring(1);
        }
        return result;
    }

    /**
     * Base64加密
     *
     * @param str 原文
     * @return 密文
     */
    public static String encodeBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes(ENCODE_UTF_8);
        } catch (UnsupportedEncodingException e) {
            b = str.getBytes();
        }
        if (b != null) {
            s = new Base64Encoder().encode(b);
        }
        return s;
    }

    /**
     * Base64解密
     *
     * @param 密文
     * @return 原文
     */
    public static String decodeBase64(String s) {
        byte[] b = null;
        String result = BLANK;
        if (StringUtil.isNotEmpty(s)) {
            Base64Encoder decoder = new Base64Encoder();
            try {
                b = decoder.decode(s);
                result = new String(b, ENCODE_UTF_8);
            } catch (Exception e) {
                result = s;
            }
        }
        return result;
    }

//    /**
//     * 转换为Ascii码字符串
//     *
//     * @param value 字符串
//     * @return Ascii码字符串
//     */
//    public static String es(Object value) {
//        return escape(value);
//    }

//    /**
//     * 转换为Ascii码字符串
//     *
//     * @param obj 字符串
//     * @return Ascii码字符串
//     */
//    public static String escape(Object obj) {
//        if (obj == null || isEmpty(obj.toString())) {
//            return "";
//        }
//        String value = obj.toString();
//        String result = "";
//        try {
//            StringWriter out = new StringWriter(value.length() * 2);
//            int sz = value.length();
//            for (int i = 0; i < sz; ++i) {
//                char ch = value.charAt(i);
//
//                switch (ch) {
//                    case '﻿':
//                        break;
//                    case '\b':
//                        out.write(92);
//                        out.write(98);
//                        break;
//                    case '\n':
//                        out.write(92);
//                        out.write(110);
//                        break;
//                    case '\t':
//                        out.write(92);
//                        out.write(116);
//                        break;
//                    case '\f':
//                        out.write(92);
//                        out.write(102);
//                        break;
//                    case '\r':
//                        out.write(92);
//                        out.write(114);
//                        break;
//                    default:
//
//                        switch (ch) {
//                            case '\'':
//                                out.write(92);
//                                out.write(39);
//                                break;
//                            case '"':
//                                out.write(92);
//                                out.write(34);
//                                break;
//                            case '\\':
//                                out.write(92);
//                                out.write(92);
//                                break;
//                            case '/':
//                                out.write(92);
//
//                                out.write(47);
//                                break;
//                            default:
//                                out.write(ch);
//                        }
//                }
//            }
//            result = out.toString();
//        } catch (Exception ioe) {
//            result = value;
//        }
//        return result;
//    }

    /**
     * 转换为Ascii码字符串,换行结束
     *
     * @param obj 字符串
     * @return Ascii码字符串
     */
    public static String escapeBreakLine(Object obj) {
        if (obj == null || isEmpty(obj.toString())) {
            return "";
        }
        String value = obj.toString();
        String result = "";
        try {
            StringWriter out = new StringWriter(value.length() * 2);
            int sz = value.length();
            for (int i = 0; i < sz; ++i) {
                char ch = value.charAt(i);

                switch (ch) {
                    case '\n':
                        break;
                    default:
                        out.write(ch);
                }
            }
            result = out.toString();
        } catch (Exception ioe) {
            result = value;
        }
        return result;
    }

    /**
     * 从URL中取参数MAP
     *
     * @param q URL
     * @return 参数MAP
     */
    public static Map<String, String> getQueryMap(String q) {
        String query = getOrElse(q);
        if (query.indexOf("?") > -1) {
            query = splitTwo(query, "?")[1];
        }
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            if (StringUtil.isNotEmpty(param)) {
                String[] kv = splitTwo(param, "=");
                String name = kv[0];
                String value = kv[1];
                map.put(name, value);
            }
        }
        return map;
    }


    /**
     * 从Query String中取参数MAP
     *
     * @param q URL
     * @return 参数MAP
     */
    public static Map<String, String> getQueryMapByQueryString(String q) {
        String query = getOrElse(q);
        String[] params = StringUtil.split(query, "?&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            if (StringUtil.isNotEmpty(param)) {
                String[] kv = splitTwo(param, "=");
                String name = kv[0];
                String value = kv[1];
                map.put(name, value);
            }
        }
        return map;
    }

    /**
     * 生成GET URL
     *
     * @param baseUrl     基础URL
     * @param queryString 参数String
     * @return URL
     */
    public static String buildQueryString(String baseUrl, String queryString) {
        StringBuffer result = new StringBuffer(baseUrl);
        String qs = StringUtil.getOrElse(queryString);
        if (result.indexOf("?") < 0 && qs.indexOf("?") < 0) {
            result.append("?");
        } else if (!result.toString().endsWith("&") && !qs.startsWith("&")) {
            result.append("&");
        }
        result.append(qs);
        return result.toString();
    }

    /**
     * 生成GET URL
     *
     * @param baseUrl 基础URL
     * @param map     参数
     * @return URL
     */
    public static String buildQueryString(String baseUrl, Map<String, ? extends Object> map) {
        return buildQueryString(baseUrl, buildQueryString(map));
    }

    /**
     * 生成GET参数
     *
     * @param map 参数
     * @return GET参数
     */
    public static String buildQueryString(Map<String, ? extends Object> map) {
        StringBuffer buffer = new StringBuffer();
        if (map != null) {
            boolean first = true;
            for (String key : map.keySet()) {
                if (first) {
                    first = false;
                } else {
                    buffer.append("&");
                }
                buffer.append(key).append("=");
                Object v = map.get(key);
                if (v != null) {
                    if (v.getClass().isArray()) {
                        buffer.append(StringUtil.join((Object[]) v));
                    } else {
                        buffer.append(v);
                    }
                }
            }
        }
        return buffer.toString();
    }


    /**
     * 生成GET参数，按顺序生成
     *
     * @param map 参数
     * @return GET参数
     */
    public static String buildAscQueryString(Map<String, ? extends Object> map) {
        StringBuffer buffer = new StringBuffer();
        if (map != null) {
            List<String> keys = new ArrayList<String>();
            keys.addAll(map.keySet());
            Collections.sort(keys);
            boolean first = true;
            for (String key : keys) {
                if (first) {
                    first = false;
                } else {
                    buffer.append("&");
                }
                buffer.append(key).append("=");
                Object v = map.get(key);
                if (v != null) {
                    if (v.getClass().isArray()) {
                        buffer.append(StringUtil.join((Object[]) v));
                    } else {
                        buffer.append(v);
                    }
                }
            }
        }
        return buffer.toString();
    }

    /**
     * 正则匹配所有可能并拼接后返回
     *
     * @param value     输入
     * @param parterner 正则
     * @return 返回
     */
    public static String matchAll(Object value, String parterner) {
        Pattern p = Pattern.compile(parterner);
        Matcher matcher = p.matcher(getOrElse(value));
        StringBuffer buf = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group(1);
            buf.append(group);
        }
        return buf.toString();
    }

    /**
     * 正则匹配所有可能并以列表的形式返回
     *
     * @param value     输入
     * @param parterner 正则
     * @return 返回
     */
    public static List<String> match(Object value, String parterner) {
        Pattern p = Pattern.compile(parterner);
        Matcher matcher = p.matcher(getOrElse(value));
        List<String> bufs = new ArrayList<String>();
        while (matcher.find()) {
            String group = matcher.group(1);
            bufs.add(group);
        }
        return bufs;
    }

    /**
     * 拼接字符串
     *
     * @param somethings 输入
     * @return 输出
     */
    public static String build(String... somethings) {
        StringBuffer sb = new StringBuffer();
        for (String a : somethings) {
            sb.append(a);
        }
        return sb.toString();
    }

    /**
     * 格式金额-分转元
     *
     * @param cent 以分为单位的金额
     * @return 以元为单位的金额
     */
    public static String formatMoney2Yuan(String cent) {
        if (StringUtil.isEmpty(cent)) {
            return "0";
        }
        BigDecimal number = new BigDecimal(cent);
        NumberFormat nf = new DecimalFormat("#,##0.00");
        String yuan = nf.format(number.divide(new BigDecimal(100)));
        return yuan;
    }

    /**
     * 格式金额-元转分
     *
     * @param yuan 以元为单位的金额
     * @return 以分为单位的金额
     */
    public static String formatMoney2Cent(String yuan) {
        if (StringUtil.isEmpty(yuan)) {
            return "0";
        }
        yuan = yuan.replace(",", "");
        BigDecimal number = new BigDecimal(yuan);
        if (number.equals(BigDecimal.ZERO)) {
            return "0";
        }
        NumberFormat nf = new DecimalFormat("####0");
        String cent = nf.format(number.multiply(new BigDecimal(100)));
        return cent;
    }

    /**
     * 查找出现次数
     *
     * @param string    被查找字符串
     * @param character 查找正则
     * @return 出现次数
     */
    public static int findTimes(String string, String character) {
        Matcher slashMatcher = Pattern.compile(character).matcher(string);
        int mIdx = 0;
        while (slashMatcher.find()) {
            mIdx++;
        }
        return mIdx;
    }

    /**
     * 查找第N个字符串位置
     *
     * @param string    被查找字符串
     * @param character 查找正则
     * @param i         第几个
     * @return 位置
     */
    public static int getCharacterPosition(String string, String character, int i) {
        Matcher slashMatcher = Pattern.compile(character).matcher(string);
        int mIdx = 0;
        boolean ok = false;
        while (ok = slashMatcher.find()) {
            mIdx++;
            if (mIdx == i) {
                break;
            }
        }
        return ok ? slashMatcher.start() : -1;
    }

    public static String replaceByCharPosition(String value, String search, String replacement, int pos) {
        int index = getCharacterPosition(value, search, pos);
        StringBuffer sb = new StringBuffer();
        sb.append(value.substring(0, index));
        sb.append(replacement);
        sb.append(value.substring(index + 1, value.length()));
        return sb.toString();
    }

    public static String[] getFunctionArguments(String str, String functionName, int index) {
        String value = getOrElse(str);
        int p = getCharacterPosition(value.toUpperCase(), functionName.toUpperCase() + "\\(", index);
        int len = functionName.length() + 1;
        int argsBegin = p + len;
        int lltimes = findTimes(value.subSequence(0, p).toString(), "\\(");
        int altimes = findTimes(value, "\\(");
        int argsEnd = getCharacterPosition(value.toUpperCase(), "\\)", altimes - lltimes);
        char[] argchars = value.substring(argsBegin, argsEnd).toCharArray();
        //处理args中的括号问题
        List<String> args = new ArrayList<String>();
        int moreLeft = 0;
        StringBuffer argBuffer = new StringBuffer();
        for (char c : argchars) {
            if (c == ',') {
                if (moreLeft == 0) {
                    args.add(argBuffer.toString());
                    argBuffer = new StringBuffer();
                } else {
                    argBuffer.append(c);
                }
            } else {
                if (c == '(') {
                    moreLeft++;
                } else if (c == ')') {
                    moreLeft--;
                }
                argBuffer.append(c);
            }
        }
        if (StringUtil.isNotEmpty(argchars.toString().trim())) {
            args.add(argBuffer.toString());
        }
        return args.toArray(new String[0]);
    }

    public static String[] splitByFunction(String str, String functionName, int index) {
        String value = getOrElse(str);
        int p = getCharacterPosition(value.toUpperCase(), functionName.toUpperCase() + "\\(", index);
        //int len = functionName.length() + 1;
        //int argsBegin = p + len;
        int lltimes = findTimes(value.substring(0, p).toString(), "\\(");
        int altimes = findTimes(value, "\\(");
        int argsEnd = getCharacterPosition(value.toUpperCase(), "\\)", altimes - lltimes);
        //String args = value.substring(argsBegin, argsEnd);
        return new String[]{value.substring(0, p), value.substring(argsEnd + 1)};
    }

    final static Pattern PATTERN_SORT_NAME = Pattern.compile("(\\s+(De|DE|dE|de|a|A)[S|s][C|c]\\s*,)");

    /**
     * 转换SortName到数据库列名
     *
     * @param sortName 原sortName
     * @return 转换后sort_name
     */
    public static String autoTransSortName(String sortName) {
        StringBuffer result = new StringBuffer();
        if (StringUtil.isNotEmpty(sortName)) {
            try {
                Matcher m = PATTERN_SORT_NAME.matcher(sortName);
                List<String> leftChars = new ArrayList<String>();
                List<String> splitors = new ArrayList<String>();
                StringBuffer left = new StringBuffer();
                while (m.find()) {
                    String group = m.group(0);
                    m.appendReplacement(left, "");
                    leftChars.add(underScoreName(left.toString().replaceAll("[^0-9a-zA-Z_.]", "")));
                    splitors.add(group);
                    left = new StringBuffer();
                }
                m.appendTail(left);
                leftChars.add(underScoreName(left.toString().replaceAll("[^0-9a-zA-Z_.]", "")));

                int i = 0;
                for (; i < splitors.size(); i++) {
                    result.append(leftChars.get(i)).append(splitors.get(i));
                }
                result.append(leftChars.get(i));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * Exception栈内容
     *
     * @param ex Exception
     * @return Exception栈内容
     */
    public static String getExceptionStackTrace(Exception ex) {
        if (ex != null) {
            StackTraceElement[] stackTraces = ex.getStackTrace();
            return stackTraceToString(stackTraces);
        }
        return BLANK;
    }

    /**
     * 将stack trace 转换为文本
     *
     * @param stackTraces
     * @return
     */
    public static String stackTraceToString(Object[] stackTraces) {
        StringBuffer buffer = new StringBuffer();
        if (stackTraces != null) {
            for (int i = 0; i < stackTraces.length; i++) {
                String line = StringUtil.getOrElse(stackTraces[i]);
                if (i > 0) {
                    buffer.append("\r\n");
                }
                buffer.append(line);
            }
        }
        return buffer.toString();
    }

    /**
     * Trim
     *
     * @param s 原字符串
     * @return 返回
     */
    public static String trim(Object s) {
        if (s == null) {
            return "";
        }
        return StringUtils.trim(s.toString());
    }

    /**
     * 删除所有空格
     *
     * @param value
     * @return
     */
    public static String removeSpace(String value) {
        return getOrElse(value).replaceAll(" ", BLANK);
    }

    /**
     * 格式化消息
     *
     * @param pattern 消息模板：使用{0}{1}占位符
     * @param args    替换参数
     * @return 格式化后消息
     */
    public static String formatMessage(String pattern, Object... args) {
        return MessageFormat.format(pattern, args);
    }

    static Pattern PATTERN_URL_PARAMS = Pattern.compile("\\#([a-zA-Z0-9]*?)\\#");

    /**
     * 功能:查找传入的URL中匹配到的##中的字符，如：/client/news_#pageSize#.action
     * <p>
     * 开发:YangDong 2017年3月20日
     *
     * @param url String
     * @return 匹配到的所有字符，不包括#
     */
    public static List<String> compileUrlParams(String url) {
        Matcher matcher = PATTERN_URL_PARAMS.matcher(url);
        List<String> arrayList = new ArrayList<String>();
        while (matcher.find()) {
            String ms = matcher.group(1);
            if (isNotEmpty(ms)) {
                arrayList.add(ms);
            }
        }
        return arrayList;
    }

    /**
     * 功能:对指定的字符串value，替换掉指定的REGEX字符为指定的字符replacement
     * <p>
     * 开发:YangDong 2017年3月21日
     *
     * @param value       原字符串
     * @param regex       被替换的字符串
     * @param replacement 替换成的字符串
     * @return String
     */
    public static String replaceAll(String value, String regex, Object replacement) {
        return value.replaceAll(regex, trim(replacement));
    }

    /**
     * 功能:替换掉html中的标签和转义符
     * <p>
     * 开发:YangDong 2017年3月22日
     *
     * @param value 被替换的字符串
     * @return 替换后的只剩下文本的字符串
     */
    public static String safeText(String value) {
        return // script标签
                trim(value).replaceAll("<[\\s]*?[s|S][c|C][r|R][i|I][p|P][t|T][^>]*>(.|[\r\n])*?<[\\s]*?\\/[s|S][c|C][r|R][i|I][p|P][t|T][^>]*>", "")
                        // style标签
                        .replaceAll("<[\\s]*?[s|S][t|T][y|Y][l|L][e|E][^>]*>(.|[\r\n])*?<[\\s]*?\\/[s|S][t|T][y|Y][l|L][e|E][^>]*>", "")
                        // <标签>和html字符转义
                        .replaceAll("</?[^>]+>", "").replaceAll("&[a-z]+;", "");
    }

    /**
     * 截取指定长度文本，并过滤掉HTML
     *
     * @param value HTML
     * @param len   指定长度
     * @return 文本
     */
    public static String safeText(String value, int len) {
        return StringUtils.substring(safeText(value), 0, len);
    }

    public static String preReplace(String replacement) {
        if (replacement == null) {
            return "";
        }
        String result = replacement.replaceAll("\\\\", "\\\\\\\\");
        return result.replaceAll("\\$", "\\\\\\$");
    }

    /**
     * 取文件扩展名
     * 从FileUtil移动到StringUtil 2018-09-21 11:05:09
     *
     * @param path PATH
     * @return 文件扩展名
     */
    public static String getFileExtName(String path) {
        if (StringUtil.isEmpty(path)) {
            return path;
        }
        int index = path.lastIndexOf(FILE_NAME_EXT_SPLIT);
        return index >= 0 ? path.substring(index + 1) : StringUtil.BLANK;
    }


    /**
     * 从FileUtil移动到StringUtil 2018-09-21 11:15:16
     * 反斜杠转换成斜杠路径
     *
     * @param path 原路径
     * @return 斜杠路径
     */
    public static String safePath(String path) {
        return StringUtil.getOrElse(path).replaceAll(PATH_BACKSLASH, PATH_SLASH);
    }

}
