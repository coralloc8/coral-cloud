package com.coral.web.auth.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SecurityProperties {

    /**
     * 当请求需要身份认证时，默认跳转的url
     */
    private String loginUrl = "/login.html";
    /**
     * form表单用户名参数名
     */
    private String usernameParameter = "username";

    /**
     * form表单密码参数名
     */
    private String passwordParameter = "password";

    /**
     * 认证的url
     */
    private String loginProcessingUrl = "/login";

    private String logout = "/logout";

    private int rememberMeSeconds = 3600 * 24 * 20;
    /**
     * 同一个用户在系统中的最大session数，默认1
     */
    private int maximumSessions = 1;
    /**
     * 达到最大session时是否阻止新的登录请求，默认为false，不阻止，新的登录会将老的登录失效掉
     */
    private boolean maxSessionsPreventsLogin;
    private String jsessionid = "JSESSIONID";
}