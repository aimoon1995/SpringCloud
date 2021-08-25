package com.moon.moon_commons.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName CustomerEntity
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Data
public class CustomerEntity {

    private  String uuid;

    private  String mobile;

    private  String openId;

    private Date createTime;

}
