package com.moon.moon_api;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({"com.moon.moon_api.mapper.moon","com.moon.moon_api.mapper.moonSec"})
public class MoonApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoonApiApplication.class, args);
    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    // public ResponseBean list(Integer flg, String key, String company, Integer pageSize, Integer pageNum) {
    //        PageHelper.startPage(pageNum,pageSize);
    //        List<Driver> list = driverMapper.list(flg,key,company);
    //        PageInfo<Driver> pageInfo = new PageInfo<>(list);
    //        for(Driver driver:list){
    //            driver.setMobile(driver.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
    //
    //            if(driver.getActualName().length()>2){
    //                driver.setActualName(driver.getActualName().replaceAll("([\\u4e00-\\u9fa5]{1})(.*)([\\u4e00-\\u9fa5]{1})","$1" + createAsterisk(driver.getActualName().length() - 2)+"$3"));
    //            } else {
    //                driver.setActualName(driver.getActualName().replaceAll("([\\u4e00-\\u9fa5]{1})(.*)", "$1" + createAsterisk(driver.getActualName().length() - 1)));
    //            }
    //        }
    //        JSONObject data = new JSONObject();
    //        data.put("data",list);
    //        data.put("pageNum", pageInfo.getPageNum());
    //        data.put("total", pageInfo.getTotal());
    //        return ResponseBean.createSuccess("查询成功",data);
    //    }
}
