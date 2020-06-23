package com.example.spring.web.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.spring.common.cache.ICacheService;
import com.example.spring.web.core.aop.LogAop;
import com.example.spring.web.core.cache.RedisCacheServiceImpl;

/**
 * @author huss
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
     * 内置远程缓存为redis缓存
     *
     * @return
     */
    @Bean
    public <R> ICacheService<String, R> redisCacheService() {
        return new RedisCacheServiceImpl();
    }
}
