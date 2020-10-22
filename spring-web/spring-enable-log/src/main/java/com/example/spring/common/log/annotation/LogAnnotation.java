package com.example.spring.common.log.annotation;

import java.lang.annotation.*;

/**
 * @description: 日志
 * @author: huss
 * @time: 2020/10/21 10:55
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 模块名称
     * 
     * @return
     */
    String value();

    /**
     * 记录执行参数
     * 
     * @return
     */
    boolean recordRequestParam() default true;

}
