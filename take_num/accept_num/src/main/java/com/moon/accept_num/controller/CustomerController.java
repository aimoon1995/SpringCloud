package com.moon.accept_num.controller;

import com.alibaba.fastjson.JSONObject;
import com.moon.accept_num.service.TakeNumService;
import com.moon.moon_commons.constants.CommonConstants;
import com.moon.moon_commons.util.ResponseBean;
import com.moon.moon_commons.util.StringUtil;
import com.moon.moon_commons.util.WxSendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName TakeNumController
 * @Description: TODO
 * @Author zyl
 * @Date 2021/7/9
 * @Version V1.0
 **/
@RestController
@RequestMapping("/take/num/")
@Slf4j
public class CustomerController {

    @Autowired
    private TakeNumService takeNumService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * @return
     * @Author zyl
     * @Description 请求获取微信openiD
     * @Date 2021/7/9
     **/
    @RequestMapping("/openid/get")
    @ResponseBody
    public ResponseBean getOpenId(@RequestParam String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wx6a81be5a43c7c94d&secret=21f24b17e4f48bd570b0a908e37df915&js_code=" + code + "&grant_type=authorization_code";
        ResponseEntity responseEntity = getForm(url);
        return ResponseBean.createSuccess("", JSONObject.parseObject(responseEntity.getBody().toString()));
    }

    public  ResponseEntity getForm(String url) throws HttpClientErrorException, IOException {
        try {
            //  输出结果
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            return forEntity;
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
    /**
     * @return
     * @Author zyl
     * @Description 查看用户前面还有多少人排队
     * @Date 2021/7/9
     **/
    @RequestMapping("/type/count")
    @ResponseBody
    public ResponseBean getTypeCount() {
        Map serMap = new HashMap();
        serMap.put("status", CommonConstants.TAKE_NUM_STATUS_WAITING);
        Map typeCountBeans = takeNumService.getTypeCount(serMap);
        return ResponseBean.createSuccess("", typeCountBeans);
    }


    /**
     * 取号
     *
     * @param permCount
     * @param hairCutCount
     * @param name
     * @param mobile
     * @return
     */
    @RequestMapping("/take")
    @ResponseBody
    public ResponseBean takeNum(
            @RequestParam Integer permCount,
            @RequestParam Integer hairCutCount,
            @RequestParam String name,
            @RequestParam String mobile,
            @RequestParam String openId
    ) {
        ResponseBean res = ResponseBean.createSuccess("");
        if (permCount == 0 && hairCutCount == 0) {
            ResponseBean.createError("必须取一个号");
        }
        if (StringUtil.isEmpty(openId)) {
            ResponseBean.createError("openId缺失,请重新进入小程序");
        }
        try {
            RLock lock = redissonClient.getLock("takeNum");
            // 第一个参数是等待时间，n毫秒内获取不到锁，则直接返回
            // 第二个参数 m毫秒后强制释放锁
            boolean suc = lock.tryLock(1000, 1000, TimeUnit.MILLISECONDS);
            if (suc) {
                res = takeNumService.takeNum(permCount, hairCutCount, name, mobile, openId);
            }

        } catch (Throwable e) {
            log.error("{}", e);
            throw new IllegalStateException(e);
        } finally {
            RLock lock = redissonClient.getLock("takeNum");
            if (lock != null && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return res;
    }


    /**
     * @return
     * @Author zyl
     * @Description 获取当前有效的号码
     * @Date 2021/7/9
     **/
    @RequestMapping("/type/showNum")
    @ResponseBody
    public ResponseBean getTypeCount( @RequestParam String openId) {
        if (StringUtil.isEmpty(openId)) {
            ResponseBean.createError("openId不可为空");
        }
        return  takeNumService.getCustUserAbleNum(openId);
    }

}
