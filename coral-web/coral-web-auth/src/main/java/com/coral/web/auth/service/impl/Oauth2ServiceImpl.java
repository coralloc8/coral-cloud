package com.coral.web.auth.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.coral.database.test.jpa.primary.entity.*;
import com.coral.web.auth.service.IOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coral.base.common.CollectionUtil;
import com.coral.base.common.cache.ICacheService;
import com.coral.base.common.jpa.enums.GlobalDeletedEnum;
import com.coral.base.common.jpa.service.impl.JpaBaseServiceImpl;
import com.coral.database.test.jpa.primary.repository.OauthClientDetailsRepository;
import com.coral.database.test.jpa.primary.repository.SysRoleRepository;
import com.coral.database.test.jpa.primary.repository.SysUserRepository;
import com.coral.database.test.jpa.primary.repository.SysUserRoleRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Oauth2ServiceImpl extends JpaBaseServiceImpl<OauthClientDetails, Long, OauthClientDetailsRepository>
    implements IOauth2Service {

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserRoleRepository userRoleRepository;

    @Autowired
    private SysRoleRepository roleRepository;

    @Autowired
    private ICacheService<String, List<OauthClientDetails>> redisCacheService;

    @Override
    public List<SysUser> findOauth2UserByUsername(String username) {
        return sysUserRepository.findByAccountAndDeleted(username, GlobalDeletedEnum.NO);
    }

    @Override
    public List<OauthClientDetails> findOauth2ClientByClientId(String clientId) {
        // String key = StringUtils.getCacheKey("oauth2", "client", "clientId", clientId);
        // List<OauthClientDetails> obj = redisCacheService.get(key, () ->
        // this.getRepository().findByClientId(clientId),
        // TimeUnit.MINUTES, 5, OauthClientDetails.class);
        // log.info(">>>>obj:{}", obj);
        //
        // return CollectionUtil.convert(obj);

        return this.getRepository().findByClientId(clientId);
    }

    @Override
    public List<String> findRolesByUser(Long userNo) {
        List<SysUserRole> userRoles =
            (List<SysUserRole>)userRoleRepository.findAll(QSysUserRole.sysUserRole.userNo.eq(userNo));

        List<String> roleNos = CollectionUtil.isBlank(userRoles) ? Collections.emptyList()
            : userRoles.parallelStream().map(SysUserRole::getRoleNo).collect(Collectors.toList());
        List<SysRole> roles = (List<SysRole>)roleRepository.findAll(QSysRole.sysRole.no.in(roleNos));

        return CollectionUtil.isBlank(roles) ? Collections.emptyList()
            : roles.parallelStream().map(SysRole::getUniqueKey).collect(Collectors.toList());
    }
}
