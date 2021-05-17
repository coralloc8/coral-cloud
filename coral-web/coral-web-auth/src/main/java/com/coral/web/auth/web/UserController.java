package com.coral.web.auth.web;

import com.coral.enable.log.annotation.LogAnnotation;
import com.coral.database.test.jpa.primary.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coral.service.auth.service.IAuthService;
import com.coral.service.core.support.LoginUser;
import com.coral.web.core.response.Result;
import com.coral.web.core.response.Results;

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
