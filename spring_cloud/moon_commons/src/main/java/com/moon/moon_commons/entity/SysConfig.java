package com.moon.moon_commons.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * @ClassName SysConfig
 * @Description: TODO
 * @Author zyl
 * @Date 2021/2/23
 * @Version V1.0
 **/

@Data
public class SysConfig implements Serializable {

    /**
     * 主键
     **/
    private String uuid;

    /**
     * 主键
     **/
    private String key;

    /**
     * 存储的值
     **/
    private String value;

    /**
     * 配置类型（1：客服电话，2：发票相关，3：关于我们）
     **/
    private String type;

    /**
     * 创建时间
     **/
    private Timestamp createTime;

    /**
     * 修改时间
     **/
    private Timestamp updateTime;

    /**
     * 修改者
     **/
    private String updater;

    /**
     * 应用ID
     **/
    private String appid;


    /**
     * 上一次的更新时间
     **/
    private Timestamp lastUpdTime;


    /**
     * 系统配置参数名
     **/
    private String name;

    /**
     * 备注
     **/
    private String remark;

    /**
     * 配置类型（1：启用，2：不启用）
     **/
    private Integer status;
}
