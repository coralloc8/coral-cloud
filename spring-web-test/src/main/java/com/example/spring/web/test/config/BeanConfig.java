package com.example.spring.web.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.example.spring.common.SnowFlakeCreator;
import com.example.spring.common.cache.ICacheService;
import com.example.spring.web.core.aop.LogAop;
import com.example.spring.web.core.cache.RedisCacheServiceImpl;
import com.example.spring.web.core.support.NumberCreatorFactory;
import com.example.spring.web.core.support.xss.XssFilter;

/**
 * @author huss
 */
@Configuration
public class BeanConfig {

    @Autowired
    private SnowFlakeCreatorProperty snowFlakeCreatorProperty;

    /**
     * 号码制造器
     * 
     * @return
     */
    @Bean
    public NumberCreatorFactory numberCreatorFactory() {
        return new NumberCreatorFactory(
            new SnowFlakeCreator(snowFlakeCreatorProperty.getDataCenterId(), snowFlakeCreatorProperty.getMachineId()));
    }

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

    /**
     * 防XSS注入
     *
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        return registration;
    }
}
