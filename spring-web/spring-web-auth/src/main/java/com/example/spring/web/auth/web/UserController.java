package com.example.spring.web.auth.web;

import com.example.spring.enable.log.annotation.LogAnnotation;
import com.example.spring.database.test.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.service.auth.service.IAuthService;
import com.example.spring.service.core.support.LoginUser;
import com.example.spring.web.core.response.Result;
import com.example.spring.web.core.response.Results;

/**
 * @author huss
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IAuthService authService;


    @LogAnnotation("auth-user")
    @GetMapping
    public Result getCurrentUser(SysUser user) {
        LoginUser loginUser = authService.getLoginUser();
        return new Results().success(loginUser);

    }


}
