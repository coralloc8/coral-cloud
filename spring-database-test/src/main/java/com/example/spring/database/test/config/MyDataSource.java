package com.example.spring.database.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author huss
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.mysql")
public class MyDataSource extends DruidDataSource {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
