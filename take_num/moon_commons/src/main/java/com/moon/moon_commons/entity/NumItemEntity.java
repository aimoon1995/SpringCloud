package com.moon.moon_commons.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName NumItemEntity
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@Data
public class NumItemEntity {

    private  String uuid;

    private  Integer num;

    private  Integer type;

    /**
     * 1 未开始 2进行中 3已完成 4已作废
     */
    private  Integer status;

    private  String takeUuid;

    private Date CreateTime;
}
