package com.moon.moon_commons.util;

import com.alibaba.fastjson.JSONObject;
import com.moon.moon_commons.vo.WXMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @ClassName WxSendMsgUtil
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/28
 * @Version V1.0
 **/
public class WxSendMsgUtil {
    @Autowired
    private RestTemplate restTemplate;

    public  ResponseEntity getForm(String url, RestTemplate restTemplate) throws HttpClientErrorException, IOException {
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
    public static String getAccessToken(String AppId, String AppSecret, RestTemplate restTemplate) throws IOException {
        String access_tokenurl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        access_tokenurl = access_tokenurl.replace("APPID", AppId);
        access_tokenurl = access_tokenurl.replace("APPSECRET", AppSecret);
        String accesstoken = "";
        ResponseEntity jsonObject = getForm(access_tokenurl, restTemplate);
        if (jsonObject != null) {
            accesstoken = JSONObject.parseObject(jsonObject.getBody().toString()).getString("access_token");
        }
        return accesstoken;
    }


    /**
     * 公众号推送封装
     *
     * @param vo
     * @param ACCESS_TOKEN
     * @throws Exception
     */
    public static String sendMessagePush(WXMsgVo vo, String ACCESS_TOKEN) {
        JSONObject miniprogram = new JSONObject();
        JSONObject data = new JSONObject();
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
        if (vo.getTouser() == null) {
            return "touser不能为空";
        }
        if (vo.getTemplateId() == null) {
            return "TemplateId不能为空";
        }
        params.add("touser", vo.getTouser());
        params.add("template_id", vo.getTemplateId());
        params.add("value", vo.getTitle());
        miniprogram.put("appid", vo.getRemark());
        miniprogram.put("pagepath", vo.getRemark());
        params.add("touser", miniprogram);
        if (vo.getThing1() != null) {
            JSONObject thing1 = new JSONObject();
            thing1.put("value", vo.getThing1());
            MultiValueMap.put("thing1", thing1);
        }
        if (vo.getThing2() != null) {
            JSONObject thing2 = new JSONObject();
            thing2.put("value", vo.getThing2());
            data.put("thing2", thing2);
        }
        if (vo.getThing3() != null) {
            JSONObject thing3 = new JSONObject();
            thing3.put("value", vo.getThing3());
            text.put("thing3", thing3);
        }
        if (vo.getThing4() != null) {
            JSONObject thing4 = new JSONObject();
            thing4.put("value", vo.getThing4());
            text.put("thing4", thing4);
        }
        if (vo.getTitleColor() != null) {
            first.put("color", vo.getTitleColor());
        }
        if (vo.getRemakColor() != null) {
            remark.put("color", vo.getRemakColor());
        }
        text.put("first", first);
        text.put("remark", remark);
        json.put("data", text);
        //发送模板消息
        //发送模板消息
        String sendUrl="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        sendUrl=sendUrl.replace("ACCESS_TOKEN",ACCESS_TOKEN);
        String jsonstringParm =json.toString();
        JSONObject object=WXAdvancedUtil.httpsRequest(sendUrl,"POST",jsonstringParm);
        return object.toString();

    }

    private ResponseEntity postForm(String url, MultiValueMap<String, String> params) throws HttpClientErrorException, IOException {
        RestTemplate client = new RestTemplate();
        //请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交    
        HttpHeaders headers = new HttpHeaders();
        //封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //执行HTTP请求
        HttpEntity requestEntity = new HttpEntity(params, headers);
        try {
            //  输出结果
            ResponseEntity response = client.exchange(url, HttpMethod.POST, requestEntity, String.class);
            return response;
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }


}
