package com.moon.accept_num.utils;

import com.alibaba.fastjson.JSONObject;
import com.moon.accept_num.config.AppConfig;
import com.moon.moon_commons.vo.WxMssVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName WxSendMsgUtil
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/28
 * @Version V1.0
 **/
@Component
public class WxSendMsgUtil {
    private RestTemplate restTemplate;

    private RedisTemplate<String, Object> cache;


    public WxSendMsgUtil() {
        restTemplate = (RestTemplate) SpringContextUtil.getBean("restTemplate");
        cache = (RedisTemplate) SpringContextUtil.getBean("redisTemplate");
    }

    public ResponseEntity getForm(String url) throws HttpClientErrorException, IOException {
        try {
            //  输出结果
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            return forEntity;
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    /**
     * 获取access_token
     *
     * @param AppId
     * @param AppSecret
     * @return
     * @throws Exception
     */
    public String getAccessToken(String AppId, String AppSecret) throws IOException {
        if (null != cache.opsForValue().get("access_token")) {
            return cache.opsForValue().get("access_token").toString();
        }
        String access_tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        access_tokenurl = access_tokenurl.replace("APPID", AppConfig.getWxAppid());
        access_tokenurl = access_tokenurl.replace("APPSECRET", AppConfig.getWxAppSecret());
        String accesstoken = "";
        ResponseEntity jsonObject = getForm(access_tokenurl);
        if (jsonObject != null) {
            accesstoken = JSONObject.parseObject(jsonObject.getBody().toString()).getString("access_token");
            cache.opsForValue().set("access_token", accesstoken, 100, TimeUnit.MINUTES);
        }
        return accesstoken;
    }


    public String push(WxMssVo wxMssVo) throws IOException {
        //这里简单起见我们每次都获取最新的access_token（时间开发中，应该在access_token快过期时再重新获取）
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken(AppConfig.getWxAppid(), AppConfig.getWxAppSecret());
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(url, wxMssVo, String.class);
        return responseEntity.getBody();
    }


}
