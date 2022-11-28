package com.coral.test.groovy.config;

import com.coral.web.core.aop.LogAop;
import com.coral.web.core.json.MyJackson2ObjectMapperBuilder;
import com.coral.web.core.support.SpringContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Map;

/**
 * @author huss
 */

@Configuration
@Slf4j
public class BeanConfig {

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        return MyJackson2ObjectMapperBuilder.json().build();
    }

    @Bean
    public SpringContext springContext() {
        return new SpringContext();
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

}
