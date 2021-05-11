package com.coral.web.auth.security.handler;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coral.base.common.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyAuthenticationSuccessWithClientHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String HEADER_AUTHORIZATION_VAL_PREFIX = "Basic";

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        logger.info(">>>>>登录成功");

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith(HEADER_AUTHORIZATION_VAL_PREFIX)) {
            throw new UnapprovedClientAuthenticationException("no client information in the request header");
        }
        String[] tokens = this.extractAndDecodeHeader(header, request);

        assert tokens.length == 2;

        // 获取clientId 和 clientSecret
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        // 获取 ClientDetails
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);

        if (clientDetails == null) {
            throw new UnapprovedClientAuthenticationException("client_id does not exist:" + clientId);
            // 判断 方言 是否一致
        } else if (!passwordEncoder.matches(clientSecret, clientDetails.getClientSecret())) {
            throw new UnapprovedClientAuthenticationException("client_secret does not match:" + clientId);
        }

        // 密码授权 模式, 组建 authentication
        TokenRequest tokenRequest =
            new TokenRequest(Collections.emptyMap(), clientId, clientDetails.getScope(), "password");

        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);

        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        // 判断是json 格式返回 还是 view 格式返回
        // 将 authention 信息打包成json格式返回
        response.setContentType("application/json;charset=UTF-8");
        Result result = new Results().success(token);
        response.getWriter().write(JsonUtil.toJson(result));
    }

    /**
     * 解码请求头
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            decoded = Base64.getDecoder().decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("invalid basic authentication token");
        } else {
            return new String[] {token.substring(0, delim), token.substring(delim + 1)};
        }
    }
}
