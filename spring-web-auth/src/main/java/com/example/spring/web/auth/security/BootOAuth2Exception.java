package com.example.spring.web.auth.security;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.example.spring.common.exception.IErrorMessage;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;

/**
 * 自定义授权服务认证时返回的异常信息
 * 
 * @author huss
 */
@JsonSerialize(using = BootOAuthExceptionJacksonSerializer.class)
public class BootOAuth2Exception extends OAuth2Exception {

    @Getter
    private IErrorMessage errorMessage;

    /**
     * <p>
     * Description: 构造函数
     * </p>
     *
     * @param errorMessage
     *            异常信息
     */
    public BootOAuth2Exception(IErrorMessage errorMessage) {
        super(errorMessage.getErrorMsg());
        this.errorMessage = errorMessage;
    }

    public BootOAuth2Exception(String message) {
        super(message);
    }

    public BootOAuth2Exception(String message, Throwable throwable) {
        super(message, throwable);
    }
}
