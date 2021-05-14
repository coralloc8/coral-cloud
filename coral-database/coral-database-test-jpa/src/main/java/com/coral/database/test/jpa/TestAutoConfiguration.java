package com.coral.database.test.jpa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.coral.database.test.jpa.config.YamlPropertyResourceFactory;

/**
 * @author huss
 */
@ComponentScan
@PropertySource(value = {"classpath:application-database-${spring.profiles.active}.yml"}, encoding = "utf-8",
    factory = YamlPropertyResourceFactory.class)
public class TestAutoConfiguration {

}
