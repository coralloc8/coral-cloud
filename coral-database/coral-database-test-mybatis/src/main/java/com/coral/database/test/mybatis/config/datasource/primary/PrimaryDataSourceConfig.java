package com.coral.database.test.mybatis.config.datasource.primary;

import com.coral.database.test.mybatis.config.datasource.MybatisSessionFactory;
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
 * @className PrimaryDataSourceConfiguration
 * @description
 * @date 2021/5/21 16:36
 */
@MapperScan(basePackages = "com.coral.**.primary.**.mapper", sqlSessionTemplateRef = "primarySqlSessionTemplate")
@Configuration
public class PrimaryDataSourceConfig {

    static final String MAPPER_LOCATION = "classpath*:mapper/primary/*.xml";

    @Autowired
    private MybatisSessionFactory mybatisSessionFactory;


    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("dataSourcePrimary") DataSource dataSource) throws Exception {
        return mybatisSessionFactory.sqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    /**
     * 创建SqlSessionTemplate
     */
    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate firstSqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
