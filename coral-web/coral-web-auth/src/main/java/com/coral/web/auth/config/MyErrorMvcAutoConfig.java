package com.coral.web.auth.config;

import java.util.stream.Collectors;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coral.web.auth.web.CustomBasicErrorController;

/**
 * 自定义error请求
 * 
 * @author huss
 */
@Configuration
public class MyErrorMvcAutoConfig {

    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,
        ObjectProvider<ErrorViewResolver> errorViewResolvers) {
        return new CustomBasicErrorController(errorAttributes, this.serverProperties.getError(),
            errorViewResolvers.orderedStream().collect(Collectors.toList()));
    }
}
