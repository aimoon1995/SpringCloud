package com.moon.moon_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.moon.moon_api.service.SysConfigService;
import com.moon.moon_commons.entity.OrderTimeFareItem;
import com.moon.moon_commons.entity.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName MoonSecController
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/21
 * @Version V1.0
 **/
@RestController
@RequestMapping("/api")
@Slf4j
public class MoonSecController {
    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping("/infomation")
    public List<SysConfig> queryConfigByType(String type) {
        return sysConfigService.queryConfigByType(type);
    };


}
