package com.coral.enable.workflow.bo;

import java.util.Map;

import lombok.Data;

/**
 * @description: 流程任务参数
 * @author: huss
 * @time: 2020/12/29 16:33
 */
@Data
public class TaskCompleteBO {

    /**
     * 任务id
     */
    private String taskDefinitionKey;

    /**
     * 入参
     */
    private Map<String, Object> variables;

}
