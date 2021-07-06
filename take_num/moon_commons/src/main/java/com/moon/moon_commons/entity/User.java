package com.moon.moon_commons.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/21
 * @Version V1.0
 **/
@Data
public class User {

    private String uuid;

    private String name;

    /**
     * 1 男 2女
     */
    private Integer sex;

    private Date birth;

    private Date createTime;

    private String creater;

    private Date updatetime;

}
