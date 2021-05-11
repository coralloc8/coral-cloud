package com.coral.enable.workflow.message;

import com.coral.base.common.exception.IErrorCodeMessage;

/**
 * @description: 错误信息
 * @author: huss
 * @time: 2020/12/29 16:45
 */
public enum WorkFlowErrorMessage implements IErrorCodeMessage<WorkFlowErrorMessage> {
    /**
     * 110100：任务节点不存在
     */
    TASK_NOT_EXIST(110100, "任务节点不存在"),;


    private Integer errCode;

    private String errMsg;

    WorkFlowErrorMessage(Integer errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public Integer getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errMsg;
    }
}
