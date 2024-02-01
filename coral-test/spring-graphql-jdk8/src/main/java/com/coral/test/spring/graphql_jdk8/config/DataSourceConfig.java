package com.coral.test.spring.graphql_jdk8.config;

import com.coral.test.spring.graphql_jdk8.config.datasource.MybatisSessionFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceConfig
 * @description 数据源配置
 * @date 2022/3/8 17:50
 */
@Configuration
public class DataSourceConfig {
    static final String MAPPER_LOCATION = "classpath*:mapper/*.xml";

    @Resource
    private MybatisSessionFactory mybatisSessionFactory;



    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        return mybatisSessionFactory.sqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    /**
     * 创建SqlSessionTemplate
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
