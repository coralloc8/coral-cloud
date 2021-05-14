package com.coral.database.test.jpa.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huss
 * @version 1.0
 * @className PrimaryDataSource
 * @description 主数据源
 * @date 2021/5/13 9:23
 */
@ConfigurationProperties(prefix = "spring.datasource.primary")
@Configuration
public class PrimaryDataSource  extends DruidDataSource {
}
