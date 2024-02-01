package com.coral.test.spring.graphql.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 常用配置
 *
 * @author huss
 * @date 2023/12/29 15:16
 * @packageName com.coral.test.spring.graphql.config
 * @className CommonConfig
 */
@Configuration
@ConfigurationProperties("graphql.config")
public class CommonProperty {

    private String enumScan;
}
