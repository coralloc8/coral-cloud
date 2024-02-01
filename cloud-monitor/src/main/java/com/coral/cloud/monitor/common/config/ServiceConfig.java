package com.coral.cloud.monitor.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("coral.monitor")
@Configuration
@Data
public class ServiceConfig {

    //当前医院
    private String key;
}
