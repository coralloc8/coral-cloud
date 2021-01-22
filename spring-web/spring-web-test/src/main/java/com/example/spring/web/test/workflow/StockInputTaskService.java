package com.example.spring.web.test.workflow;

import java.util.HashMap;
import java.util.Map;

import org.flowable.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.enable.workflow.IWorkflowService;
import com.example.spring.enable.workflow.bo.ProcessInstanceBO;
import com.example.spring.enable.workflow.bo.TaskCompleteBO;



/**
 * @description: TODO
 * @author: huss
 * @time: 2020/12/30 15:59
 */
@Service
public class StockInputTaskService {

    @Autowired
    private IWorkflowService workflowService;

    @Autowired
    private RepositoryService repositoryService;

//    public void deploy() {
//        // 加载流程
//        Deployment deployment = repositoryService.createDeployment()
//            .addClasspathResource("processes/stock_input_approval.bpmn20.xml").deploy();
//    }

    /**
     * 提交申请
     */
    public void submit() {
        ProcessInstanceBO processInstanceBO = new ProcessInstanceBO();
        processInstanceBO.setProcessKey("stock_input_approval");

        Map<String, Object> variables = new HashMap<>(4);
        variables.put("user", "xiaosan");
        variables.put("stock", 5);
        processInstanceBO.setVariables(variables);
        workflowService.startAndComplete(processInstanceBO);
    }

    public void groupLeaderApproval() {
        TaskCompleteBO taskCompleteBO = new TaskCompleteBO();
        taskCompleteBO.setTaskDefinitionKey("sid-2ADD758F-D4E3-4BD4-9A43-D5C091B41067");
        Map<String, Object> variables = new HashMap<>(4);
        variables.put("approval", true);
        variables.put("user", "xiaosan");
        taskCompleteBO.setVariables(variables);
        workflowService.complete(taskCompleteBO);
    }

}
