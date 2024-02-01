package com.coral.cloud.monitor.common.config.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author huss
 * @version 1.0
 * @className MonitorDataSourceConfig
 * @description 数据源配置
 * @date 2022/3/8 17:50
 */
@MapperScan(basePackages = "com.coral.cloud.monitor.mapper", sqlSessionTemplateRef = "monitorSqlSessionTemplate")
@Configuration
public class MonitorDataSourceConfig {
    static final String MAPPER_LOCATION = "classpath*:mapper/*.xml";

    @Autowired
    private MybatisSessionFactory mybatisSessionFactory;


    @Bean(name = "monitorSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("monitorDataSource") DataSource dataSource) throws Exception {
        return mybatisSessionFactory.sqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    /**
     * 创建SqlSessionTemplate
     */
    @Bean(name = "monitorSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("monitorSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
