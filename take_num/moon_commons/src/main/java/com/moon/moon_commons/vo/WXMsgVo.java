package com.moon.moon_commons.vo;

import lombok.Data;

/**
 * @ClassName WXMsgVo
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/28
 * @Version V1.0
 **/
@Data
public class WXMsgVo {
    private String touser;
    private String templateId;
    private String title;
    private String remark;
    private String thing1;
    private String thing2;
    private String thing3;
    private String thing4;
    //是否跳转相关
    private String smUrl;
    private String smAppid;
    private String smpagepath;
    //备注字体颜色
    private String remakColor;
    //标题字体颜色
    private String titleColor;
}
