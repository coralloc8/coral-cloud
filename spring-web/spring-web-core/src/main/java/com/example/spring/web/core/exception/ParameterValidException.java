
package com.example.spring.web.core.exception;

import com.example.spring.common.exception.IErrorMessage;
import com.example.spring.common.exception.SystemRuntimeException;
import com.example.spring.web.core.enums.BaseErrorMessageEnum;

/**
 * 描述 : 参数校验错误
 *
 * @author huss
 */
public class ParameterValidException extends SystemRuntimeException {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    public ParameterValidException() {
        super(BaseErrorMessageEnum.PARAMETER_VERIFICATION_ERROR);
    }

    public ParameterValidException(IErrorMessage errorMessage) {
        super(errorMessage);
    }
}
