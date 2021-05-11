package com.coral.enable.log.annotation;

import java.lang.annotation.*;

import com.coral.enable.log.selector.LogImportSelector;
import org.springframework.context.annotation.Import;

/**
 * @description: 启动日志框架
 * @author: huss
 * @time: 2020/10/21 10:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogImportSelector.class)
public @interface EnableLogging {}
