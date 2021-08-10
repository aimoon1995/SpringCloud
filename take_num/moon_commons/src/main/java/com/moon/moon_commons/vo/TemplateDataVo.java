package com.moon.moon_commons.vo;

import lombok.Data;

/**
 * @ClassName TemplateDataVo
 * @Description: TODO
 * @Author zyl
 * @Date 2021/8/9
 * @Version V1.0
 **/
@Data
public class TemplateDataVo {
    private String value;

    public TemplateDataVo(String value) {
        this.value = value;
    }
}
