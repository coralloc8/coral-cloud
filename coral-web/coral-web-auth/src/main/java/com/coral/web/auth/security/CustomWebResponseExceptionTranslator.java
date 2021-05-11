package com.coral.web.auth.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.web.core.enums.OauthErrorMessageEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义授权服务认证时返回的异常信息
 * 
 * @author huss
 */
@Component
@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator<BootOAuth2Exception> {
    @Override
    public ResponseEntity<BootOAuth2Exception> translate(Exception e) {

        log.error(">>>>>CustomWebResponseExceptionTranslator error:", e);

        OAuth2Exception oAuth2Exception = (OAuth2Exception)e;

        BootOAuth2Exception bootOAuth2Exception;
        if (oAuth2Exception instanceof BadClientCredentialsException
            || oAuth2Exception instanceof InvalidClientException) {
            bootOAuth2Exception = new BootOAuth2Exception(OauthErrorMessageEnum.INVALID_CLIENT);
        } else if (oAuth2Exception instanceof InvalidGrantException) {
            bootOAuth2Exception = new BootOAuth2Exception(OauthErrorMessageEnum.INVALID_GRANT);
        } else if (oAuth2Exception instanceof InvalidTokenException) {
            bootOAuth2Exception = new BootOAuth2Exception(OauthErrorMessageEnum.INVALID_TOKEN);
        } else if (oAuth2Exception instanceof InvalidRequestException) {
            bootOAuth2Exception = new BootOAuth2Exception(OauthErrorMessageEnum.INVALID_REQUEST);
        } else if (oAuth2Exception instanceof UnauthorizedClientException) {
            bootOAuth2Exception = new BootOAuth2Exception(OauthErrorMessageEnum.UNAUTHORIZED_CLIENT);
        } else if (oAuth2Exception instanceof UnsupportedGrantTypeException) {
            bootOAuth2Exception = new BootOAuth2Exception(OauthErrorMessageEnum.UNSUPPORTED_GRANT_TYPE);
        } else {
            bootOAuth2Exception = new BootOAuth2Exception(BaseErrorMessageEnum.SYSTEM_ERROR);
        }

        return ResponseEntity.status(oAuth2Exception.getHttpErrorCode()).body(bootOAuth2Exception);
    }
}