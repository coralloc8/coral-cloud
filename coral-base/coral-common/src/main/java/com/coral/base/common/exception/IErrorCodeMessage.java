package com.coral.base.common.exception;

/**
 * 获取错误信息和错误码
 * 
 * @author huss
 */
public interface IErrorCodeMessage<E extends Enum<E>> extends IErrorMessage {

    /**
     * 获取错误码
     * 
     * @return
     */
    Integer getErrorCode();

}
