package com.coral.database.test.mybatis.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.coral.base.common.mybatis.dynamic.DbContextHolder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceConfiguration
 * @description
 * @date 2021/5/21 13:21
 */
@Configuration
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "dataSourcePrimary")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.primary")
    public DataSource dataSourcePrimary() {
        DataSource druidDataSource = DruidDataSourceBuilder.create().build();
//        DbContextHolder.addDataSource(DbTypeEnum.PRIMARY.getCode(), druidDataSource);
        return druidDataSource;
    }

    @Bean(name = "dataSourceSecondary")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.secondary")
    public DataSource dataSourceSecondary() {
        DataSource druidDataSource = DruidDataSourceBuilder.create().build();
//        DbContextHolder.addDataSource(DbTypeEnum.SECONDARY.getCode(), druidDataSource);
        return druidDataSource;
    }
//
//    @Primary
//    @Bean
//    public DynamicDataSource dataSource(@Qualifier("dataSourcePrimary") DataSource dataSourcePrimary,
//                                        @Qualifier("dataSourceSecondary") DataSource dataSourceSecondary) {
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        Map<Object, Object> targetDataResources = new HashMap<>(4);
//        targetDataResources.put(DbTypeEnum.PRIMARY.getCode(), dataSourcePrimary);
//        targetDataResources.put(DbTypeEnum.SECONDARY.getCode(), dataSourceSecondary);
//        //设置默认数据源
//        dynamicDataSource.setDefaultTargetDataSource(dataSourcePrimary);
//        dynamicDataSource.setTargetDataSources(targetDataResources);
//        DbContextHolder.setDefaultDs(DbTypeEnum.PRIMARY.getCode());
//        return dynamicDataSource;
//    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public DataSourceTransactionManager primaryTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourcePrimary());
        return transactionManager;
    }

    @Bean(name = "secondaryTransactionManager")
    public DataSourceTransactionManager secondaryTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSourceSecondary());
        return transactionManager;
    }

}
