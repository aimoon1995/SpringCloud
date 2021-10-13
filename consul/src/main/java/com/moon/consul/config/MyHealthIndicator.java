package com.moon.consul.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @ClassName MyHealthIndicator
 * @Description: TODO
 * @Author zyl
 * @Date 2021/9/27
 * @Version V1.0
 **/
@Component
public class MyHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        // 检查通过（方式一新建对象）
        return new Health.Builder().withDetail("usercount", 10) //自定义监控内容
                .withDetail("userstatus", "up").up().build();
    }
}