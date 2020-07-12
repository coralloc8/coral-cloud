package com.example.spring.database.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.ToString;

/**
 * @description: TODO
 * @author: huss
 * @time: 2020/7/10 18:28
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.primary")
@ToString(callSuper = true)
public class PrimaryDataSource extends DruidDataSource {

}
