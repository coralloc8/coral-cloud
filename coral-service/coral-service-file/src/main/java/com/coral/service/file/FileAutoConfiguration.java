package com.coral.service.file;

import com.coral.service.file.config.YamlPropertyResourceFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @description: TODO
 * @author: huss
 * @time: 2020/7/13 18:45
 */
@ComponentScan
@PropertySource(value = {"classpath:application-file-${spring.profiles.active}.yml"}, encoding = "utf-8",
    factory = YamlPropertyResourceFactory.class)
public class FileAutoConfiguration {
}
