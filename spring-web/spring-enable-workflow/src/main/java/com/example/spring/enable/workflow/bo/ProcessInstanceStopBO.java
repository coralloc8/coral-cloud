package com.example.spring.enable.workflow.bo;

import lombok.Data;

/**
 * @description: 流程参数
 * @author: huss
 * @time: 2020/12/29 16:33
 */
@Data
public class ProcessInstanceStopBO {

    /**
     * 流程实例key
     */
    private String processKey;

    /**
     * 原因
     */
    private String reason;

}
