package com.coral.cloud.user.common.config;

import com.coral.cloud.user.common.exception.GlobalExceptionHandler;
import com.coral.web.core.aop.LogAop;
import com.coral.web.core.json.MyJackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huss
 */
@Configuration
@Slf4j
public class BeanConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return MyJackson2ObjectMapperBuilder.json().build();
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
     * 全局异常捕获
     *
     * @return
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }


}
