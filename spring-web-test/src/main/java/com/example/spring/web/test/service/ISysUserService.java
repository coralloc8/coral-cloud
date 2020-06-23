package com.example.spring.web.test.service;

import java.util.List;

import com.example.spring.common.jpa.service.IJpaBaseService;
import com.example.spring.database.test.entity.SysUser;

public interface ISysUserService extends IJpaBaseService<SysUser, Long> {

    List<SysUser> findByAccount(String username);
}
