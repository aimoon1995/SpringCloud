package com.moon.consul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MoonTestController
 * @Description: TODO
 * @Author zyl
 * @Date 2021/9/27
 * @Version V1.0
 **/
@RestController
@RequestMapping("/moon")
public class MoonTestController {


    @RequestMapping("/test")
    public String test () {
        return  "moon";
    }
}
