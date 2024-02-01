package com.coral.test.spring.graphql_jdk8.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 *
 * @author huss
 * @date 2024/1/6 17:11
 * @packageName com.coral.test.spring.graphql_jdk8.config
 * @className CaffeineConfig
 */
@EnableCaching
@Configuration
public class CaffeineConfig {

    /**
     * 配置缓存管理器
     *
     * @return 缓存管理器
     */
    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(buildCaffeine());
        return cacheManager;
    }

    @Bean
    public Cache<Object, Object> caffeine() {
        // 构建cache对象
        return buildCaffeine().build();

    }

    private Caffeine<Object, Object> buildCaffeine() {
        return Caffeine.newBuilder()
                //0) 驱逐策略：基于容量，时间，引用。
                //0.1 基于时间
                .expireAfterWrite(1, TimeUnit.MINUTES)
                //0.2.1 基于容量
                //初始容量
                .initialCapacity(100)
                .maximumSize(10000)
//                //0.2.2 权重
//                .weigher(((key, value) -> {
//                    if (key.equals(1)) {
//                        return 1;
//                    } else {
//                        return 2;
//                    }
//                }))
                //0.3 基于引用
                //0.3.1 当进行GC的时候进行驱逐
//                .softValues()
                //0.3.2 当key和缓存元素都不再存在其他强引用的时候驱逐
//                .weakKeys()
//                .weakValues()
                ;
    }
}
