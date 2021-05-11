package com.coral.base.common.exception;

/**
 * @author huss
 */
public class SerializeException extends SystemRuntimeException {
    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
