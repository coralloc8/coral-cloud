package com.coral.base.common.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.coral.base.common.mybatis.config.mybatis.DbConfig;
import com.coral.base.common.mybatis.config.mybatis.ParseSQLInnerInterceptor;
import com.coral.base.common.mybatis.config.mybatis.PropertiesCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author huss
 * @version 1.0
 * @className MybatisConfig
 * @description mybatis配置
 * @date 2021/5/12 9:11
 */
@Configuration
public class MybatisConfig {


    @Autowired
    private DataSource dataSource;
    /**
     * mybatis-plus 分页插件
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        interceptor.addInnerInterceptor(new ParseSQLInnerInterceptor(parseReplaceStr()));
        return interceptor;
    }


//    @Bean
//    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
//        return new PropertiesCustomizer(parseReplaceStr());
//    }

    private String parseReplaceStr() {
//        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
//        return DbConfig.getReplaceStr(DbType.getDbType(druidDataSource.getDbType()));
        return "";
    }


}
