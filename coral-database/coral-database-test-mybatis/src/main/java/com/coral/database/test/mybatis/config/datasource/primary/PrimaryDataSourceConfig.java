package com.coral.database.test.mybatis.config.datasource.primary;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

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


    /**
     * 本数据源扫描的mapper路径
     */
    static final String MAPPER_LOCATION = "classpath*:mapper/primary/*.xml";


    @Bean(name = "primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("dataSourcePrimary") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //设置mapper配置文件
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    /**
     * 创建SqlSessionTemplate
     */
    @Bean(name = "primarySqlSessionTemplate")
    public SqlSessionTemplate firstSqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }



}
