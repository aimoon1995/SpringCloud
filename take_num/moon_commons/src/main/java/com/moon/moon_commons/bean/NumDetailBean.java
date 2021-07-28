package com.moon.moon_commons.bean;

import lombok.Data;

/**
 * @ClassName NumDetailBean
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/28
 * @Version V1.0
 **/
@Data
public class NumDetailBean {
    /**
     * uuid
     */
    private  String uuid;

    /**
     * 称呼
     */
    private  String name;

    /**
     * 手机号
     */
    private  String mobile;

    /**
     * 号数
     */
    private  Integer num;

    /**
     * 类型
     */
    private  Integer type;

    /**
     * 状态
     */
    private  Integer status;

    /**
     * openId
     */
    private  String openId;


}
