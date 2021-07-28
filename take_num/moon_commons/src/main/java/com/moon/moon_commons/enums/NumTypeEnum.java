package com.moon.moon_commons.enums;

import org.springframework.util.StringUtils;

/**
 * @ClassName NumTypeEnum
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/28
 * @Version V1.0
 **/
public enum  NumTypeEnum {
    PERM_TYPE(1, "烫发"),

    HAIR_TYPE(2, "理发");

    private Integer value;

    private String desc;

    NumTypeEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static String getDesc(Integer value) {
        if (!StringUtils.isEmpty(value)) {
            for (NumTypeEnum typeEnum : NumTypeEnum.values()) {
                if (value.equals(typeEnum.getValue())) {
                    return typeEnum.desc;
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
