package com.example.spring.common.exception;

/**
 * 缓存保存异常
 * 
 * @author huss
 */
public class CacheSaveException extends SystemRuntimeException {
    public CacheSaveException(String message) {
        super(message);
    }

    public CacheSaveException(String message, Throwable throwable) {
        super(message, throwable);
    }
}