package com.coral.cloud.user.common.exception;

import com.coral.base.common.exception.*;
import com.coral.base.common.http.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 异常全局处理类
 *
 * @author
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handlerException(HttpServletResponse response, Exception e) {
        log.info("#####[error]#####", e);
        ErrorResponse result = null;
        try {
            if (e instanceof SystemRuntimeException) {
                IErrorMessage errorMessage = ((SystemRuntimeException) e).getErrorMessage();
                //设置状态码
                if (errorMessage instanceof IHttpStatus) {
                    response.setStatus(((IHttpStatus) errorMessage).getHttpStatus());
                } else {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
                String message = errorMessage.getErrorMsg();
                Integer code = BaseErrorMessageEnum.FAILURE.getErrorCode();
                if (errorMessage instanceof IErrorCodeMessage) {
                    code = ((IErrorCodeMessage<?>) errorMessage).getErrorCode();
                }

                result = new ErrorResponse(code, message);
            }

        } catch (Exception e1) {
            log.error("#####[handlerException error]", e1);
        }
        if (Objects.isNull(result)) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            result = new ErrorResponse(BaseErrorMessageEnum.FAILURE);
        }
        return result;
    }


}