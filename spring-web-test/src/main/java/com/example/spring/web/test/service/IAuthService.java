package com.example.spring.web.test.service;

import com.example.spring.web.core.support.LoginUser;

/**
 * @author huss
 */
public interface IAuthService {

    /**
     * 获取登录用户
     * @return
     */
    LoginUser getLoginUser();
}
