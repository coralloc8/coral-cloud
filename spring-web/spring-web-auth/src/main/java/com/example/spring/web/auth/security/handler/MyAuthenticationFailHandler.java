package com.example.spring.web.auth.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.response.Results;
import com.example.spring.web.core.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义登录失败处理器
 * 
 * @author huss
 */
@Component
@Slf4j
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        logger.error(">>>>>登录失败:", exception);
        // 设置状态码
        response.setStatus(500);
        response.setContentType("application/json;charset=UTF-8");
        // 将 登录失败 信息打包成json格式返回
        Result result = new Results().failure();
        response.getWriter().write(JsonUtil.toJson(result));
    }
}
