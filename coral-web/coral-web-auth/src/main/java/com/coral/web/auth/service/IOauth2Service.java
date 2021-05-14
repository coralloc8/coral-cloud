package com.coral.web.auth.service;

import java.util.List;

import com.coral.base.common.jpa.service.IJpaBaseService;
import com.coral.database.test.jpa.entity.OauthClientDetails;
import com.coral.database.test.jpa.entity.SysUser;

public interface IOauth2Service extends IJpaBaseService<OauthClientDetails, Long> {

    List<SysUser> findOauth2UserByUsername(String username);

    List<OauthClientDetails> findOauth2ClientByClientId(String clientId);

    List<String> findRolesByUser(Long userNo);
}
