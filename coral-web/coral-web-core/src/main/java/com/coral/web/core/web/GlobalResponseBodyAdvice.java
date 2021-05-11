package com.coral.web.core.web;

import java.util.Optional;

import org.springframework.context.NoSuchMessageException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.web.core.response.Result;
import com.coral.web.core.support.I18nMessageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@RestControllerAdvice
@Slf4j
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;

    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
        ServerHttpResponse response) {
        try {
            // 只处理成功请求
            if (body instanceof Result
                && ((Result)body).getCode().equals(BaseErrorMessageEnum.SUCCESS.getErrorCode())) {

                Optional<String> messageOpt = I18nMessageHelper.getInstance().getMessage(BaseErrorMessageEnum.SUCCESS);
                if (messageOpt.isPresent()) {
                    ((Result)body).setMessage(messageOpt.get());
                }
            }
        } catch (NoSuchMessageException e) {
            log.error("#####[beforeBodyWrite error]", e);
            return body;
        }

        log.info("#####response boby:{}", body);
        return body;
    }

}