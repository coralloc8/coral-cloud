package com.example.spring.service.core.support;

import java.util.List;

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

    private List<String> authorities;

    /**
     * 是否超级管理员
     */
    private boolean isSuperAdmin = false;

    public static class EmptyLoginUser extends LoginUser {

    }

}
