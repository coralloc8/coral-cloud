package com.coral.test.spring.graphql_jdk8.config.exception;

/**
 * 业务异常
 *
 * @author huss
 * @date 2024/1/5 9:39
 * @packageName com.coral.test.spring.graphql.config.exception
 * @className BusinessException
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
