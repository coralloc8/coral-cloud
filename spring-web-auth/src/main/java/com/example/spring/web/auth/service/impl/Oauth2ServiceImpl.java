package com.example.spring.web.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.common.cache.ICacheService;
import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.common.jpa.service.impl.JpaBaseServiceImpl;
import com.example.spring.database.test.entity.OauthClientDetails;
import com.example.spring.database.test.entity.SysUser;
import com.example.spring.database.test.repository.OauthClientDetailsRepository;
import com.example.spring.database.test.repository.SysUserRepository;
import com.example.spring.web.auth.service.IOauth2Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class Oauth2ServiceImpl extends JpaBaseServiceImpl<OauthClientDetails, Long, OauthClientDetailsRepository>
    implements IOauth2Service {

    @Autowired
    private SysUserRepository sysUserRepository;

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
}
