package com.example.spring.common.exception;

/**
 * 缓存保存异常
 * 
 * @author huss
 */
public class OperationNotSupportedException extends SystemRuntimeException {

    public OperationNotSupportedException() {
        super("this operation is not supported");
    }

    public OperationNotSupportedException(String message) {
        super(message);
    }

    public OperationNotSupportedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}