package com.example.spring.service.auth.service;

import com.example.spring.service.core.support.LoginUser;

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
