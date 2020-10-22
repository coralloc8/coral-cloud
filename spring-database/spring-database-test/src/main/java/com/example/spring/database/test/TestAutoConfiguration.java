package com.example.spring.database.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.example.spring.database.test.config.YamlPropertyResourceFactory;

/**
 * @author huss
 */
@ComponentScan
@PropertySource(value = {"classpath:application-database-${spring.profiles.active}.yml"}, encoding = "utf-8",
    factory = YamlPropertyResourceFactory.class)
public class TestAutoConfiguration {

}
