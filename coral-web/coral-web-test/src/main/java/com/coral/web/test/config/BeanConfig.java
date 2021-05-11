package com.coral.web.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coral.base.common.SnowFlakeCreator;
import com.coral.base.common.cache.ICacheService;
import com.coral.service.core.config.SnowFlakeCreatorProperty;
import com.coral.service.core.support.NumberCreatorFactory;
import com.coral.web.core.aop.LogAop;
import com.coral.web.core.cache.RedisCacheServiceImpl;
import com.coral.web.core.support.xss.XssFilter;

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
