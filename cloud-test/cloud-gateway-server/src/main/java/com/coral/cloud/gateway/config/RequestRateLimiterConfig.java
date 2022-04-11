package com.coral.cloud.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * @author huss
 * @version 1.0
 * @className RequestRateLimiterConfig
 * @description 限流配置
 * @date 2022/4/6 14:27
 */
@Configuration
public class RequestRateLimiterConfig {

    /**
     * 基于IP地址和接口地址进行限流
     */
    @Bean
    public KeyResolver ipAndUrlKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() + "_" +
                exchange.getRequest().getPath().value());
    }


}
