
package com.example.spring.common.exception;

import com.example.spring.common.exception.IErrorMessage;
import com.example.spring.common.exception.SystemRuntimeException;
import com.example.spring.common.exception.BaseErrorMessageEnum;

/**
 * 描述 : 不支持此操作
 *
 * @author huss
 */
public class NotSupportException extends SystemRuntimeException {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    public NotSupportException() {
        super(BaseErrorMessageEnum.OPERATION_NOT_SUPPORT);
    }

    public NotSupportException(IErrorMessage errorMessage) {
        super(errorMessage);
    }
}
