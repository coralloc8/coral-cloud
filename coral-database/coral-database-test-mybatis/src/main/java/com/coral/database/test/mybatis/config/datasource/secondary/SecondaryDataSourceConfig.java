package com.coral.database.test.mybatis.config.datasource.secondary;

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
 * @className SecondaryMybatisPlusConfig
 * @description
 * @date 2021/5/21 16:36
 */
@MapperScan(basePackages = "com.coral.**.secondary.**.mapper", sqlSessionTemplateRef = "secondarySqlSessionTemplate")
@Configuration
public class SecondaryDataSourceConfig {
    /**
     * 本数据源扫描的mapper路径
     */
    static final String MAPPER_LOCATION = "classpath*:mapper/secondary/*.xml";

    @Autowired
    private MybatisSessionFactory mybatisSessionFactory;


    @Bean(name = "secondarySqlSessionFactory")
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("dataSourceSecondary") DataSource dataSource) throws Exception {
        return mybatisSessionFactory.sqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    /**
     * 创建SqlSessionTemplate
     */
    @Bean(name = "secondarySqlSessionTemplate")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
