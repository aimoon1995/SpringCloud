package com.moon.moon_api.config.annotation;

import java.lang.annotation.*;

/**
 * @ClassName MultiTransactional
 * @Description: TODO
 * @Author zyl
 * @Date 2021/4/22
 * @Version V1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiTransactional {

    String[] value() default {};
}
