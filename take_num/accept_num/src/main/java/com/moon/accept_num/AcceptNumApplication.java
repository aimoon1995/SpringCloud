package com.moon.accept_num;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.moon.accept_num.mapper"})
public class AcceptNumApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcceptNumApplication.class, args);
    }

}
