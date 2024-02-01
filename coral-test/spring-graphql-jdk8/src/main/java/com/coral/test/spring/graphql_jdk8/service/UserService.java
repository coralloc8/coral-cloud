package com.coral.test.spring.graphql_jdk8.service;

import com.coral.test.spring.graphql_jdk8.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 用户服务
 *
 * @author huss
 * @date 2024/1/5 13:24
 * @packageName com.coral.test.spring.graphql.service
 * @className UserService
 */
@Service
@Slf4j
public class UserService {
    private static final List<UserInfoVO> USER_LIST = new CopyOnWriteArrayList<>();


    static {
        USER_LIST.add(new UserInfoVO("xiaoxiao", "小小", "管理员"));
        USER_LIST.add(new UserInfoVO("lingling", "玲玲", "管理员"));
        USER_LIST.add(new UserInfoVO("xiaosan", "小三", "跟随者"));
        USER_LIST.add(new UserInfoVO("xiaoer", "小二", "开发者"));
        USER_LIST.add(new UserInfoVO("xiaosi", "小四", "主人"));
    }


    @Cacheable(key = "#account", value = "caffeineCacheManager")
    public Optional<UserInfoVO> findUserInfo(String account) {
        log.info(">>>>>[findUserInfo]. account:{}", account);
        return USER_LIST.stream().filter(user -> user.getAccount().equals(account)).findAny();
    }
}
