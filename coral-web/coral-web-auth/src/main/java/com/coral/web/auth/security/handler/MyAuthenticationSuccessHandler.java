package com.coral.web.auth.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coral.base.common.json.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        logger.info(">>>>>登录成功");

        // 判断是json 格式返回 还是 view 格式返回
        // 将 authention 信息打包成json格式返回
        response.setContentType("application/json;charset=UTF-8");
        Result result = new Results().success();
        response.getWriter().write(JsonUtil.toJson(result));
    }

}
