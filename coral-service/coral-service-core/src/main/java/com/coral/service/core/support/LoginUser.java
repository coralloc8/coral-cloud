package com.coral.service.core.support;

import lombok.Data;

/**
 * 模拟登陆用户
 * 
 * @author huss
 */

@Data
public class LoginUser {

    private Long userId;

    private String userName;

    private String email;

    private String account;

    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否超级管理员
     */
    private boolean isSuperAdmin = false;

    public static class EmptyLoginUser extends LoginUser {

    }

}
