package com.coral.enable.workflow;

import com.coral.enable.workflow.bo.ProcessInstanceBO;
import com.coral.enable.workflow.bo.ProcessInstanceStopBO;
import com.coral.enable.workflow.bo.TaskCompleteBO;
import com.coral.enable.workflow.bo.TaskMoveBO;

/**
 * @description: 工作流
 * @author: huss
 * @time: 2020/12/29 10:31
 */
public interface IWorkflowService {

    /**
     * 开始创建工作流实例
     * 
     * @param processInstanceBO
     * @return 返回实例ID
     */
    String start(ProcessInstanceBO processInstanceBO);

    /**
     * 停止工作流实例
     * 
     * @param processInstanceStopBO
     */
    void stop(ProcessInstanceStopBO processInstanceStopBO);

    /**
     * 完成流程中的任务
     * 
     * @param taskCompleteBO
     */
    void complete(TaskCompleteBO taskCompleteBO);

    void startAndComplete(ProcessInstanceBO processInstanceBO);

    void move(TaskMoveBO taskMoveBO);

}
