package com.coral.service.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 雪花算法配置
 * @author: huss
 * @time: 2020/7/7 17:52
 */
@Configuration
@ConfigurationProperties(prefix = "snow-flake-creator")
@Data
@Slf4j
public class SnowFlakeCreatorProperty {
    private Long dataCenterId = 1L;
    private Long machineId = 1L;
}
