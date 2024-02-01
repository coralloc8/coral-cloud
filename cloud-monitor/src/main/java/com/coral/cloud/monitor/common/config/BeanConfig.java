package com.coral.cloud.monitor.common.config;

import com.coral.base.common.SnowFlakeCreator;
import com.coral.service.core.config.SnowFlakeCreatorProperty;
import com.coral.web.core.aop.LogAop;
import com.coral.web.core.json.MyJackson2ObjectMapperBuilder;
import com.coral.web.core.web.GlobalExceptionHandler;
import com.coral.web.core.web.GlobalResponseBodyAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;
import java.util.concurrent.Executor;

/**
 * @author huss
 */
@Configuration
@Slf4j
public class BeanConfig {

    /**
     * 跨域处理方式一
     *
     * @return
     */
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        log.info(">>>>>filter 设置跨域...");
        return new CorsWebFilter(source);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return MyJackson2ObjectMapperBuilder.json().build();
    }

    /**
     * 编码生成器配置属性
     *
     * @return
     */
    @Bean
    public SnowFlakeCreatorProperty snowFlakeCreatorProperty() {
        return new SnowFlakeCreatorProperty();
    }

    /**
     * 号码制造器
     *
     * @return
     */
    @Bean
    public SnowFlakeCreator numberCreatorFactory(SnowFlakeCreatorProperty snowFlakeCreatorProperty) {
        return new SnowFlakeCreator(snowFlakeCreatorProperty.getDataCenterId(), snowFlakeCreatorProperty.getMachineId());
    }

    /**
     * 请求日志打印
     *
     * @return
     */
    @Bean
    public LogAop logAop() {
        return new LogAop();
    }


    /**
     * 全局异常捕获
     *
     * @return
     */
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    /**
     * 自定义返回体
     *
     * @return
     */
    @Bean
    public ResponseBodyAdvice globalResponseBodyAdvice() {
        return new GlobalResponseBodyAdvice();
    }

    /**
     * 自定义线程池
     *
     * @return
     */
    @Bean("taskExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数5：线程池创建时候初始化的线程数
        executor.setCorePoolSize(50);
        //最大线程数5：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(500);
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(5000);
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(25);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("Executor-");
        executor.initialize();
        return executor;
    }

}
