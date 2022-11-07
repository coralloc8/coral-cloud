package com.coral.web.websocket.config;

import com.coral.web.core.json.MyJackson2ObjectMapperBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author huss
 */
@Configuration
@Slf4j
public class JsonConfig {

    @Primary
    @Bean
    public ObjectMapper objectMapper() {
        return MyJackson2ObjectMapperBuilder.json().build();
    }

}
