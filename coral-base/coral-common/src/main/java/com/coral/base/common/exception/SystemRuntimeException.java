/**
 * SystemRuntimeException.java Created at 2016-03-01 Created by wangkang Copyright (C) 2016 itkk.org, All rights
 * reserved.
 */
package com.coral.base.common.exception;

import lombok.Getter;

/**
 * @author huss
 */
public class SystemRuntimeException extends RuntimeException {

    /**
     * <p>
     * Field serialVersionUID: 序列号
     * </p>
     */
    private static final long serialVersionUID = 1L;

    @Getter
    private IErrorMessage errorMessage;

    @Getter
    private Object[] args;

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param errorMessage
     *            异常信息
     */
    public SystemRuntimeException(IErrorMessage errorMessage, Object... args) {
        super(errorMessage.getErrorMsg());
        this.errorMessage = errorMessage;
        this.args = args;
    }

    public SystemRuntimeException(String message) {
        super(message);
    }

    public SystemRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
