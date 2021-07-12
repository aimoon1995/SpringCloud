package com.moon.moon_commons.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName CustTakeInfoEntity
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Data
public class CustTakeInfoEntity {

    private  String uuid;

    private  String custUuid;

    private  String name;

    private  Integer permCount;

    private  Integer hairCutCount;

    private Date CreateTime;
}
