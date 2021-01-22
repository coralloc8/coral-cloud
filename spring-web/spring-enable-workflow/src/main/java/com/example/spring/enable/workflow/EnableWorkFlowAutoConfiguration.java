package com.example.spring.enable.workflow;

import org.springframework.context.annotation.Bean;

import com.example.spring.enable.workflow.flowabe.FlowableServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 自动装配
 * @author: huss
 * @time: 2020/10/21 16:17
 */
@Slf4j
public class EnableWorkFlowAutoConfiguration {

    @Bean
    public IWorkflowService flowableServiceImpl() {
        log.info(">>>>>flowableServiceImpl init...");
        return new FlowableServiceImpl();
    }

}
