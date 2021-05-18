package com.coral.database.test.mybatis;

import com.coral.database.test.mybatis.config.YamlPropertyResourceFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @author huss
 */
@MapperScan("com.coral.**.mapper")
@ComponentScan
@PropertySource(value = {"classpath:application-database-${spring.profiles.active}.yml"}, encoding = "utf-8",
    factory = YamlPropertyResourceFactory.class)
public class TestAutoConfiguration {

}
