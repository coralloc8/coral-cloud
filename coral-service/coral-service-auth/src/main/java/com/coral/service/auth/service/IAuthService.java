package com.coral.service.auth.service;

import com.coral.service.core.support.LoginUser;

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
