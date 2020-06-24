package com.example.spring.web.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.common.cache.ICacheService;
import com.example.spring.web.core.aop.LogAop;
import com.example.spring.web.core.cache.RedisCacheServiceImpl;

/**
 * @description: bean
 * @author: huss
 * @time: 2020/6/23 15:44
 */
@Configuration
public class BeanConfig {
    /**
     * 请求日志打印
     *
     * @return
     */
    @Bean
    public LogAop logAop() {
        return new LogAop();
    }

    /**
     * redis缓存
     *
     * @return
     */
    @Bean
    public <V> ICacheService<String, V> redisCacheService() {
        return new RedisCacheServiceImpl<>();
    }

}
