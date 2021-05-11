package com.coral.web.core.web;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.coral.base.common.enums.I18nMessageKey;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.coral.base.common.exception.IErrorMessage;
import com.coral.base.common.exception.SystemRuntimeException;
import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;
import com.coral.web.core.support.I18nMessageHelper;

import lombok.extern.slf4j.Slf4j;

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
    public Result handlerException(HttpServletResponse response, Exception e) {
        log.info("#####[error]#####", e);
        try {
            if (e instanceof SystemRuntimeException) {
                IErrorMessage errorMessage = ((SystemRuntimeException)e).getErrorMessage();
                Result result = new Results().failure(errorMessage);

                if (errorMessage instanceof I18nMessageKey) {
                    this.setI18nMessage((I18nMessageKey)errorMessage, result, ((SystemRuntimeException)e).getArgs());
                }
                return result;
            }

            Result result = new Results().failure();
            this.setI18nMessage(null, result);
            return result;
        } catch (Exception e1) {
            log.error("#####[handlerException error]", e1);
            return new Results().failure();
        }

    }

    private void setI18nMessage(I18nMessageKey i18nMessageKey, Result result, Object... args) {
        if (i18nMessageKey == null) {
            i18nMessageKey = BaseErrorMessageEnum.FAILURE;
        }

        Optional<String> messageOpt = I18nMessageHelper.getInstance().getMessage(i18nMessageKey, args);
        if (messageOpt.isPresent()) {
            result.setMessage(messageOpt.get());
        }

    }

}