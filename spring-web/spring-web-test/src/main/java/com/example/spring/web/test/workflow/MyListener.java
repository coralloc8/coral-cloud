package com.example.spring.web.test.workflow;

import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.spring.enable.workflow.IWorkflowService;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: TODO
 * @author: huss
 * @time: 2020/12/30 17:34
 */
@Component
@Slf4j
public class MyListener implements TaskListener {

    @Autowired
    private IWorkflowService workflowService;

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info(">>>>myListener:{}", workflowService);
    }
}