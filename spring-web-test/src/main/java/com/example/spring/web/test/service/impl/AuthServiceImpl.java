package com.example.spring.web.test.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.spring.common.cache.ICacheService;
import com.example.spring.common.exception.SystemRuntimeException;
import com.example.spring.database.test.entity.SysUser;
import com.example.spring.database.test.enums.CacheKeyEnum;
import com.example.spring.web.core.enums.OauthMessageEnum;
import com.example.spring.web.core.support.LoginUser;
import com.example.spring.web.core.support.StrFormatter;
import com.example.spring.web.test.service.IAuthService;
import com.example.spring.web.test.service.ISysUserService;

/**
 * @author huss
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Qualifier("redisCacheService")
    @Autowired
    private ICacheService<String, LoginUser> redisCacheService;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public LoginUser getLoginUser() {
        String account = "";
        List<String> authorities = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            account = currentUserName;
            authorities =
                authentication.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList());
        }

        String cacheKey = StrFormatter.format(CacheKeyEnum.LOGIN_USERNAME.getName(), account);
        String finalAccount = account;
        final List<String> finalAuthorities = authorities;

        Supplier<LoginUser> supplier = () -> {

            List<SysUser> users = sysUserService.findByAccount(finalAccount);

            if (users == null || users.isEmpty()) {
                return new LoginUser.EmptyLoginUser();
            }
            SysUser user = users.get(0);

            LoginUser loginUserCache = new LoginUser();
            loginUserCache.setUserId(user.getId());
            loginUserCache.setAccount(finalAccount);
            loginUserCache.setEmail(user.getEmail());
            loginUserCache.setPhone(user.getPhone());
            loginUserCache.setUserName(user.getUsername());
            loginUserCache.setAuthorities(finalAuthorities);
            return loginUserCache;
        };

        LoginUser loginUser = redisCacheService.get(cacheKey, supplier, CacheKeyEnum.LOGIN_USERNAME.getTimeUnit(),
            CacheKeyEnum.LOGIN_USERNAME.getCode(), LoginUser.class);

        if (loginUser instanceof LoginUser.EmptyLoginUser) {
            throw new SystemRuntimeException(OauthMessageEnum.USER_NOT_EXIST);
        }

        return loginUser;
    }
}
