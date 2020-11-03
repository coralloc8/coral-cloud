package com.example.spring.enable.task.annotation;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import com.example.spring.enable.task.selector.TaskImportSelector;

/**
 * @description: 启动日志框架
 * @author: huss
 * @time: 2020/10/21 10:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TaskImportSelector.class)
public @interface EnableAsyncTask {}
