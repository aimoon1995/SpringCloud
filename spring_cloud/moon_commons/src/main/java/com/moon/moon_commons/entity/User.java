package com.moon.moon_commons.entity;

import lombok.Data;

import java.sql.Timestamp;
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

    private  String name;

    private Date birth;
}
