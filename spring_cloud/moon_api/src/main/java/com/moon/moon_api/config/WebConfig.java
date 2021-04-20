/**
 * @Description:
 * @Author: ywang
 * @Date: 2019/3/30 下午3:30
 */

package com.moon.moon_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        InterceptorRegistration addInterceptor = registry.addInterceptor(getRequestInterceptor());
//
//        // 排除配置
//        addInterceptor.excludePathPatterns("/", "/error", "/hxApi/**");
//        // 拦截配置
//        if( signSwitch ){
//            addInterceptor.addPathPatterns("/**");
//        }else{
//            addInterceptor.addPathPatterns("/excludePathPatterns/**");
//        }
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
