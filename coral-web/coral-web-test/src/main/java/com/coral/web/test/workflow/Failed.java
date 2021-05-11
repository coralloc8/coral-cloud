package com.coral.web.test.workflow;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coral.enable.workflow.IWorkflowService;
import com.coral.enable.workflow.bo.TaskCompleteBO;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: TODO
 * @author: huss
 * @time: 2020/12/30 17:34
 */
@Component("failed")
@Slf4j
public class Failed implements JavaDelegate {

    @Autowired
    private IWorkflowService workflowService;

    @Override
    public void execute(DelegateExecution execution) {
        log.info(">>>>>failed：{}", execution);

        TaskCompleteBO taskCompleteBO = new TaskCompleteBO();
        taskCompleteBO.setTaskDefinitionKey(execution.getCurrentActivityId());
        Map<String, Object> variables = new HashMap<>(4);
        // variables.put("approval", true);
        // variables.put("user", "xiaosan");
        taskCompleteBO.setVariables(variables);
        workflowService.complete(taskCompleteBO);
    }
}