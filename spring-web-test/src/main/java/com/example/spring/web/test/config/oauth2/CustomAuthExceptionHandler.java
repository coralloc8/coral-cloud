package com.example.spring.web.test.config.oauth2;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.spring.web.core.enums.OauthErrorMessageEnum;
import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.response.Results;
import com.example.spring.web.core.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 拦截resource返回的异常信息
 * 
 * @author huss
 */
@Component
@Slf4j
public class CustomAuthExceptionHandler
    implements AuthenticationEntryPoint, AccessDeniedHandler, AuthenticationFailureHandler {
    /**
     * AuthenticationEntryPoint
     * 
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException {
        log.error(">>>>>InvalidTokenException error:", authException);
        Throwable cause = authException.getCause();
        if (cause instanceof InvalidTokenException) {
            this.createHttpHeader(response, HttpServletResponse.SC_UNAUTHORIZED);
            // Token无效
            Result result = new Results().failure(OauthErrorMessageEnum.ACCESS_TOKEN_INVALID);
            response.getWriter().write(JsonUtil.toJson(result));
        } else {
            this.createHttpHeader(response, HttpServletResponse.SC_FORBIDDEN);
            // 资源未授权
            Result result = new Results().failure(OauthErrorMessageEnum.UNAUTHORIZED);
            response.getWriter().write(JsonUtil.toJson(result));
        }

    }

    /**
     * AccessDeniedHandler
     * 
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException {
        log.error(">>>>>AccessDeniedException error:", accessDeniedException);
        // 访问资源的用户权限不足
        this.createHttpHeader(response, HttpServletResponse.SC_FORBIDDEN);
        Result result = new Results().failure(OauthErrorMessageEnum.INSUFFICIENT_PERMISSIONS);
        response.getWriter().write(JsonUtil.toJson(result));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException {
        log.error(">>>>>onAuthenticationFailure error:", exception);
        this.createHttpHeader(response, HttpServletResponse.SC_FORBIDDEN);
        // 资源未授权
        Result result = new Results().failure(OauthErrorMessageEnum.UNAUTHORIZED);
        response.getWriter().write(JsonUtil.toJson(result));
    }

    private void createHttpHeader(HttpServletResponse response, int status) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        // CORS "pre-flight" request
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Cache-Control", "no-com.example.spring.common.cache");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");
    }

}