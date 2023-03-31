package com.coral.cloud.user.common.config;

import com.coral.cloud.user.common.config.openapi.OpenApiConfig;
import com.coral.cloud.user.common.config.openapi.OpenApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huss
 * @version 1.0
 * @className OpenDocConfig
 * @description doc日志
 * @date 2021/9/18 10:41
 */
@Configuration
@Slf4j
public class OpenDocConfig extends OpenApiConfig {

    public OpenDocConfig(OpenApiProperties openApiProperties, ApplicationContext context) {
        super(openApiProperties, context);
    }

    @Bean
    public GroupedOpenApi adminGroup() {
        log.info(">>doc init");
        return this.createGroupedOpenApi("api");
    }


}
