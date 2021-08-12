package com.coral.simple.web1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className MyProperties
 * @description todo
 * @date 2021/7/30 11:09
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "test")
@PropertySource(value = {"classpath:my.properties"})
public class MyProperties {

    private String name;

}
