package com.coral.cloud.user.common.config.feign;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.*;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author huss
 * @version 1.0
 * @className Resilience4jConfig
 * @description 熔断配置
 * @date 2022/4/6 14:39
 */
@Configuration
public class Resilience4jConfig {

    /**
     * 熔断配置
     *
     * @return
     * @see FeignAutoConfiguration
     * @see Resilience4JAutoConfiguration
     */
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> resilience4JCircuitBreakerFactoryCustomizer() {
        return factory -> factory.configureDefault(id ->
                new Resilience4JConfigBuilder(id)
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                //状态滚动收集器大小，close状态时收集多少请求状态，用于计算失败率。
                                .slidingWindowSize(100)
                                //进入halfOpen状态时，可以被调用次数，就算这些请求的失败率，低于设置的失败率变为close状态，否则变为open。
                                .permittedNumberOfCallsInHalfOpenState(2)
                                //失败率，错误率达到或高于该值则进入open状态
                                .failureRateThreshold(50)
                                //open状态变为half状态需要等待的时间，即熔断多久后开始尝试访问被熔断的服务。
                                .waitDurationInOpenState(Duration.ofSeconds(30))
                                //慢调用阀值，请求执行的时间大于该值时会标记为慢调用
                                .slowCallDurationThreshold(Duration.ofSeconds(60))
                                //慢调用熔断阀值，当慢调用率达到或高于该值时，进入open状态
                                .slowCallRateThreshold(100)
                                .build())
                        .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                        .build()
        );
    }

    /**
     * 配置信号量/线程池配置
     *
     * @return
     */
    @Bean
    public Customizer<Resilience4jBulkheadProvider> resilience4jBulkheadProviderCustomizer() {
        return factory -> factory.configureDefault(id ->
                new Resilience4jBulkheadConfigurationBuilder()
                        .bulkheadConfig(BulkheadConfig.ofDefaults())
                        .threadPoolBulkheadConfig(ThreadPoolBulkheadConfig.ofDefaults())
                        .build()
        );
    }

}
