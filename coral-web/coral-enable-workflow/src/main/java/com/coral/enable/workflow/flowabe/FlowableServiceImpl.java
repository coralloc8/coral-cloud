package com.coral.enable.workflow.flowabe;

import java.util.ArrayList;
import java.util.List;

import com.coral.enable.workflow.IWorkflowService;
import com.coral.enable.workflow.bo.ProcessInstanceBO;
import com.coral.enable.workflow.bo.ProcessInstanceStopBO;
import com.coral.enable.workflow.bo.TaskCompleteBO;
import com.coral.enable.workflow.bo.TaskMoveBO;
import com.coral.enable.workflow.message.WorkFlowErrorMessage;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.coral.base.common.exception.SystemRuntimeException;

/**
 * @description: flowable
 * @author: huss
 * @time: 2020/12/29 14:15
 */
public class FlowableServiceImpl implements IWorkflowService {

    /**
     * 流程运行控制服务
     */
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 任务管理服务
     */
    @Autowired
    private TaskService taskService;

    @Override
    public String start(ProcessInstanceBO processInstanceBO) {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processInstanceBO.getProcessKey(),
            processInstanceBO.getBusinessKey(), processInstanceBO.getVariables());
        return processInstance.getId();
    }

    @Override
    public void stop(ProcessInstanceStopBO processInstanceStopBO) {
        runtimeService.deleteProcessInstance(processInstanceStopBO.getProcessKey(), processInstanceStopBO.getReason());
    }

    @Override
    public void complete(TaskCompleteBO taskCompleteBO) {
        Task task =
            taskService.createTaskQuery().taskDefinitionKey(taskCompleteBO.getTaskDefinitionKey()).singleResult();
        if (task == null) {
            throw new SystemRuntimeException(WorkFlowErrorMessage.TASK_NOT_EXIST);
        }
        taskService.complete(task.getId(), taskCompleteBO.getVariables());
    }

    @Override
    public void startAndComplete(ProcessInstanceBO processInstanceBO) {
        String processInstanceId = this.start(processInstanceBO);
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        taskService.complete(task.getId(), processInstanceBO.getVariables());
    }

    @Override
    public void move(TaskMoveBO taskMoveBO) {
        Task currentTask =
            taskService.createTaskQuery().taskDefinitionKey(taskMoveBO.getTaskDefinitionKey()).singleResult();
        if (currentTask == null) {
            throw new SystemRuntimeException(WorkFlowErrorMessage.TASK_NOT_EXIST);
        }
        List<String> currentTaskKeys = new ArrayList<>();
        currentTaskKeys.add(currentTask.getTaskDefinitionKey());

        runtimeService.createChangeActivityStateBuilder().processInstanceId(currentTask.getProcessInstanceId())
            .moveActivityIdsToSingleActivityId(currentTaskKeys, taskMoveBO.getTargetTaskDefinitionKey()).changeState();
    }
}
