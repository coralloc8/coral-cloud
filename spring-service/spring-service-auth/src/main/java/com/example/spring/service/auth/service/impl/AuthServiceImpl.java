package com.example.spring.service.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.spring.common.StrFormatter;
import com.example.spring.common.cache.ICacheService;
import com.example.spring.common.exception.SystemRuntimeException;
import com.example.spring.common.jpa.enums.GlobalDeletedEnum;
import com.example.spring.database.test.entity.SysUser;
import com.example.spring.database.test.enums.CacheKeyEnum;
import com.example.spring.database.test.enums.file.FileModuleEnum;
import com.example.spring.database.test.repository.SysUserRepository;
import com.example.spring.service.auth.enums.AuthErrorMessageEnum;
import com.example.spring.service.auth.service.IAuthService;
import com.example.spring.service.core.support.LoginUser;
import com.example.spring.service.file.service.IFileService;
import com.example.spring.service.file.vo.FileResVO;

/**
 * @author huss
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Qualifier("redisCacheService")
    @Autowired
    private ICacheService<String, LoginUser> redisCacheService;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private IFileService fileService;

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

            List<SysUser> users = sysUserRepository.findByAccountAndDeleted(finalAccount, GlobalDeletedEnum.NO);

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
//            loginUserCache.setAuthorities(finalAuthorities);
            String avatar = this.findAvatar(user.getId());
            loginUserCache.setSuperAdmin(user.getIsAdmin() == null ? false : user.getIsAdmin());
            loginUserCache.setAvatar(avatar);
            return loginUserCache;
        };

        LoginUser loginUser = redisCacheService.get(cacheKey, supplier, CacheKeyEnum.LOGIN_USERNAME.getTimeUnit(),
            CacheKeyEnum.LOGIN_USERNAME.getCode(), LoginUser.class);

        if (loginUser instanceof LoginUser.EmptyLoginUser) {
            throw new SystemRuntimeException(AuthErrorMessageEnum.USER_NOT_EXIST);
        }

        return loginUser;
    }

    /**
     * 查询用户头像
     * 
     * @param userId
     * @return
     */
    private String findAvatar(Long userId) {
        Optional<FileResVO> fileResVOOptional =
            fileService.findByFileModuleAndInfoNo(FileModuleEnum.USER_HEADER_IMAGE, String.valueOf(userId));
        if (fileResVOOptional.isPresent()) {
            return fileResVOOptional.get().getUrl();
        }
        return "";
    }
}
