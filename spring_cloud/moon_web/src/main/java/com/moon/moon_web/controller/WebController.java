package com.moon.moon_web.controller;

import com.alibaba.fastjson.JSONObject;
import com.moon.moon_commons.entity.OrderTimeFareItem;
import com.moon.moon_web.feign.MoonFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName WebController
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/16
 * @Version V1.0
 **/
@RestController
@RequestMapping("/api")
@Slf4j
public class WebController {

    @Resource
    private MoonFeign moonFeign;

    @RequestMapping("/info")
    public OrderTimeFareItem getMoon () {
       return moonFeign.getMoon();
    }
}
