/**
 * @Description:
 * @Author: ywang
 * @Date: 2019/5/23 5:36 AM
 */

package com.moon.accept_num.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "config.num")
@Component
public class AppConfig {

    private static String wxAppid;

    private static String wxAppSecret;

    private static String templeteId;

    public static String getTempleteId() {
        return templeteId;
    }

    @Value("${config.num.templeteId}")
    public  void setTempleteId(String templeteId) {
        AppConfig.templeteId = templeteId;
    }

    public static String getWxAppid() {
        return wxAppid;
    }

    @Value("${config.num.wxAppid}")
    public  void setWxAppid(String wxAppid) {
        AppConfig.wxAppid = wxAppid;
    }

    public static String getWxAppSecret() {
        return wxAppSecret;
    }

    @Value("${config.num.wxAppSecret}")
    public  void setWxAppSecret(String wxAppSecret) {
        AppConfig.wxAppSecret = wxAppSecret;
    }


}
