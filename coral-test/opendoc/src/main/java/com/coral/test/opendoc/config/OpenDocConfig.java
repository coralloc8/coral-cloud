package com.coral.test.opendoc.config;

import com.coral.test.opendoc.config.openapi.OpenApiConfig;
import com.coral.test.opendoc.config.openapi.OpenApiProperties;
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
    public GroupedOpenApi app() {
        return this.createGroupedOpenApi("app");
    }

    @Bean
    public GroupedOpenApi admin() {
        return this.createGroupedOpenApi("admin");
    }

}
