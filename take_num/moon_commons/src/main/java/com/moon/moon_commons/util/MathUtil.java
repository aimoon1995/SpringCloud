package com.moon.moon_commons.util;


import java.math.BigDecimal;

/**
 * Created by zj on 2017/6/26.
 */
public class MathUtil {

    private static int fixed_scale = 2;

    /**
     * 进行加法运算（四舍五入，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return double
     */
    public static double add(double d1, double d2) {

        return add(d1, d2, fixed_scale);

    }

    /**
     * 进行加法运算（四舍五入）scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @param scale
     * @return double
     */
    public static double add(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return MathUtil.round(b1.add(b2).doubleValue(), scale);
    }

    /**
     * 进行加法运算（舍去保留位数的值，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return double
     */
    public static double addDown(double d1, double d2) {
        return addDown(d1, d2, fixed_scale);
    }

    /**
     * 进行加法运算（（舍去保留位数的值）scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double addDown(double d1, double d2, int scale) {

        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return MathUtil.roundDown(b1.add(b2).doubleValue(), scale);
    }


    /**
     * 进行减法运算（四舍五入，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double sub(double d1, double d2) {
        return sub(d1, d2, fixed_scale);
    }

    /**
     * 进行减法运算（四舍五入）scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double sub(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return MathUtil.round(b1.subtract(b2).doubleValue(), scale);
    }

    /**
     * 进行减法运算（舍去保留位数的值，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double subDown(double d1, double d2) {
        return subDown(d1, d2, fixed_scale);
    }

    /**
     * 进行减法运算（舍去保留位数的值）scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double subDown(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return MathUtil.roundDown(b1.subtract(b2).doubleValue(), scale);
    }


    /**
     * 进行乘法运算（四舍五入，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mul(double d1, double d2) {
        return mul(d1, d2, fixed_scale);
    }

    /**
     * 进行乘法运算（四舍五入） scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double mul(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return MathUtil.round(b1.multiply(b2).doubleValue(), scale);
    }

    /**
     * 进行乘法运算（舍去保留位数的值，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double mulDown(double d1, double d2) {
        return mulDown(d1, d2, fixed_scale);
    }

    /**
     * 进行乘法运算（舍去保留位数的值） scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @param scale
     * @return
     */
    public static double mulDown(double d1, double d2, int scale) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return MathUtil.roundDown(b1.multiply(b2).doubleValue(), scale);
    }


    /**
     * 进行除法运算（四舍五入，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double div(double d1, double d2) {
        return div(d1, d2, fixed_scale);
    }

    /**
     * 进行除法运算（四舍五入） scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double div(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 进行除法运算（四舍五入，保留2位小数）
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double divDown(double d1, double d2) {
        return divDown(d1, d2, fixed_scale);
    }

    /**
     * 进行除法运算（舍去保留位数的值） scale:要保留的位数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double divDown(double d1, double d2, int len) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, len, BigDecimal.ROUND_DOWN).doubleValue();
    }


    // 进行四舍五入操作
    public static double round(double d) {
        return round(d,2);
    }

    // 进行四舍五入操作
    public static double round(double d, int len) {
        if (len < 0) {
            throw new RuntimeException("保留的位数不能小于0");
        }
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    // 进行舍去操作
    public static double roundDown(double d, int len) {
        if (len < 0) {
            throw new RuntimeException("保留的位数不能小于0");
        }
        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
        return b1.divide(b2, len, BigDecimal.ROUND_DOWN).doubleValue();
    }


    //测试
    public static void main(String[] args) {

        System.out.println("加法运算：" + MathUtil.add(10.345d, 3.331d));
        System.out.println("乘法运算：" + MathUtil.mul(10.345d, 3.333d));
        System.out.println("除法运算：" + MathUtil.div(10.345, 3.333d, 3));
        System.out.println("减法运算：" + MathUtil.subDown(10.349d, 3.333d));
    }


}
