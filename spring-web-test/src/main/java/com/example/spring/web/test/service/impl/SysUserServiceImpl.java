package com.example.spring.web.test.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.common.jpa.service.impl.JpaBaseServiceImpl;
import com.example.spring.database.test.entity.SysUser;
import com.example.spring.database.test.repository.SysUserRepository;
import com.example.spring.web.test.service.ISysUserService;

/**
 * @author huss
 */
@Service
public class SysUserServiceImpl extends JpaBaseServiceImpl<SysUser, Long, SysUserRepository>
    implements ISysUserService {

    @Override
    public List<SysUser> findByAccount(String username) {
        return this.getRepository().findByAccountAndDeleted(username, GlobalDeletedEnum.NO);
    }
}
