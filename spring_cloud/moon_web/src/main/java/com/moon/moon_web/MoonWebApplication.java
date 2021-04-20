package com.moon.moon_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.moon.moon_web.feign")
public class MoonWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonWebApplication.class, args);
    }

}
