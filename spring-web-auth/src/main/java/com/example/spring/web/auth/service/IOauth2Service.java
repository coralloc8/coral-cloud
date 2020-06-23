package com.example.spring.web.auth.service;

import java.util.List;

import com.example.spring.common.jpa.service.IJpaBaseService;
import com.example.spring.database.test.entity.OauthClientDetails;
import com.example.spring.database.test.entity.SysUser;

public interface IOauth2Service extends IJpaBaseService<OauthClientDetails, Long> {

    List<SysUser> findOauth2UserByUsername(String username);

    List<OauthClientDetails> findOauth2ClientByClientId(String clientId);
}
