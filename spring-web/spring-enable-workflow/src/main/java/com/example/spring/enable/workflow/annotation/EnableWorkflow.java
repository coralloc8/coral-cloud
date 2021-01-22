package com.example.spring.enable.workflow.annotation;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import com.example.spring.enable.workflow.selector.WorkFlowImportSelector;

/**
 * @description: 启动工作流框架
 * @author: huss
 * @time: 2020/10/21 10:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(WorkFlowImportSelector.class)
public @interface EnableWorkflow {}
