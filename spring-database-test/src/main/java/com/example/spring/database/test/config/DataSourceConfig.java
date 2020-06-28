package com.example.spring.database.test.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@EnableConfigurationProperties(MyDataSource.class)
@Configuration
@Slf4j
public class DataSourceConfig {

    @Bean("mysqlDataSource")
    @Primary
    public DataSource mysqlSDataSource() {
        log.info("#####create mysqlSDataSource bean...");
        return new MyDataSource();
    }

}
