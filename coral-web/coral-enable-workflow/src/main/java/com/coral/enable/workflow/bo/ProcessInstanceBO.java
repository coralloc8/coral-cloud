package com.coral.enable.workflow.bo;

import java.util.Map;

import lombok.Data;

/**
 * @description: 流程参数
 * @author: huss
 * @time: 2020/12/29 16:33
 */
@Data
public class ProcessInstanceBO {

    /**
     * 流程实例key
     */
    private String processKey;

    /**
     * 业务key
     */
    private String businessKey;

    /**
     * 流程实例入参
     */
    private Map<String, Object> variables;
}
