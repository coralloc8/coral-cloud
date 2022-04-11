package com.coral.cloud.user.common.config.feign;

import com.coral.base.common.http.OkHttpConfiguration;
import com.coral.cloud.user.common.interceptor.FeignRequestInterceptor;
import feign.*;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.CircuitBreakerNameResolver;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Method;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author huss
 * @date 2021-02-22
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
@Slf4j
public class FeignConfig {

    /**
     * feign请求拦截器
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }

    @Bean
    public Logger.Level feignLevel() {
        return Logger.Level.FULL;
    }

    /**
     * 默认的重试策略
     *
     * @return
     */
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, SECONDS.toMillis(1), 3);
    }

    /**
     * 注入okhttp
     *
     * @return
     */
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpConfiguration okHttpConfiguration = new OkHttpConfiguration();
        OkHttpClient.Builder builder = okHttpConfiguration.okHttpClientBuilder();
        builder.addInterceptor(okHttpConfiguration.httpLoggingInterceptor())
                //
                .sslSocketFactory(okHttpConfiguration.sslSocketFactory(), okHttpConfiguration.x509TrustManager())
                //
                .hostnameVerifier((hostName, session) -> true).retryOnConnectionFailure(true).build();

        return builder.build();
    }


    /**
     * 可以自由更改断路器名称模式
     *
     * @return
     */
    @Bean
    public CircuitBreakerNameResolver circuitBreakerNameResolver() {
        return (String feignClientName, Target<?> target, Method method) -> feignClientName + "_" + method.getName();
    }

}
