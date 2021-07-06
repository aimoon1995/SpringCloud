package com.moon.moon_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.moon.moon_api.config.ResultException;
import com.moon.moon_api.service.AsyncService;
import com.moon.moon_api.service.MoonService;
import com.moon.moon_commons.entity.OrderTimeFareItem;
import com.moon.moon_commons.util.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired
    private MoonService moonService;
    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/info")
    public OrderTimeFareItem getMoon () {
        OrderTimeFareItem orderTimeFareItem = new OrderTimeFareItem();
        orderTimeFareItem.setFare(100);

        log.info("-------------{}", JSONObject.toJSONString(orderTimeFareItem));
        return orderTimeFareItem;
    }


    /**
     * @param pageSize
     * @param pageNum
     * @return com.moon.moon_commons.util.ResponseBean
     * @Author zyl
     * @Description 分页查询
     * @Date 2021/4/21
     **/
    @GetMapping("/list")
    public ResponseBean list(@RequestParam(required = false,defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false,defaultValue = "0") Integer pageNum){
        return moonService.list(pageSize,pageNum);
    }


    @GetMapping("/add")
    public void add() throws Exception {
        moonService.add();
    }
}
