package com.moon.moon_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.moon.moon_commons.entity.OrderTimeFareItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName moonController
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/15
 * @Version V1.0
 **/
@RestController
@RequestMapping("/api")
@Slf4j
public class MoonController {

    @RequestMapping("/info")
    public OrderTimeFareItem getMoon () {
        OrderTimeFareItem orderTimeFareItem = new OrderTimeFareItem();
        orderTimeFareItem.setFare(100);

        log.info("-------------{}", JSONObject.toJSONString(orderTimeFareItem));
        return orderTimeFareItem;
    }


}
