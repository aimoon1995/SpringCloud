package com.moon.moon_commons.vo;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName WxMssVo
 * @Description: TODO
 * @Author zyl
 * @Date 2021/8/9
 * @Version V1.0
 **/
@Data
public class WxMssVo {
    private String touser;//用户openid
    private String template_id;//订阅消息模版id
    private String page = "pages/index/index";//默认跳到小程序首页
    private Map<String, TemplateDataVo> data;//推送文字
}
