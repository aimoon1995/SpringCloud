package com.moon.moon_web.feign;

import com.moon.moon_commons.entity.OrderTimeFareItem;
import com.moon.moon_web.config.FeignSimpleEncoderConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName MoonFeign
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/16
 * @Version V1.0
 **/
@FeignClient(value = "moon-api" ,configuration = FeignSimpleEncoderConfig.class)
//@FeignClient(name = "moonFeign", url = "192.168.100.152:9933")
//@FeignClient(name = "baseInfoFeign", url = "${url.hxyc.api}", configuration = FeignConfig.class)
public interface MoonFeign {

    @RequestMapping("/moon/api/info")
    //  @Headers({"Content-Type: application/json","Accept-Encoding: gzip","Connection: keep-alive","Accept-Charset: utf-8"})
    //    @RequestLine("POST /baseinfo/company")
    public OrderTimeFareItem getMoon ();
}
