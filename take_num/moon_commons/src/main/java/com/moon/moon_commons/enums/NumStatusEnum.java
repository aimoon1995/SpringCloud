package com.moon.moon_commons.enums;

import org.springframework.util.StringUtils;

/**
 * @ClassName NumStatusEnum
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/28
 * @Version V1.0
 **/
public enum NumStatusEnum {

    TAKE_NUM_STATUS_WAITING(1, "等待中"),

    TAKE_NUM_STATUS_DOING(2, "进行中"),

    TAKE_NUM_STATUS_COMPLETED(3, "已完成"),

    TAKE_NUM_STATUS_CANCELED(4, "已作废");

    private Integer value;

    private String desc;

    NumStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer value) {
        if (!StringUtils.isEmpty(value)) {
            for (NumStatusEnum statusEnum : NumStatusEnum.values()) {
                if (value.equals(statusEnum.getValue())) {
                    return statusEnum.desc;
                }
            }
            return null;
        } else {
            return null;
        }

    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
