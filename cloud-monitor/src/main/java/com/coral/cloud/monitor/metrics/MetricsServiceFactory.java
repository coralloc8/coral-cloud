package com.coral.cloud.monitor.metrics;

import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className MetricsServiceFactory
 * @description 监控服务工厂
 * @date 2023/4/14 10:49
 */
@Slf4j
@Component
public class MetricsServiceFactory implements ApplicationContextAware {
    private static Map<MetricsSourceType, IMetricsService> METRICS_MAP = new ConcurrentHashMap<>(16);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        METRICS_MAP = applicationContext.getBeansOfType(IMetricsService.class).entrySet().stream()
                .collect(Collectors.toMap(e -> e.getValue().metricsSourceType(), v -> v.getValue(), (t1, t2) -> t2));
        log.info(">>>>>开始 Cache IMetricsService 实现类... 结果集:{}", METRICS_MAP);
    }


    /**
     * 获取监控服务实现
     *
     * @param metricsSourceType
     * @return
     */
    public static Optional<IMetricsService> findMessageHandler(@NonNull MetricsSourceType metricsSourceType) {
        return Optional.ofNullable(METRICS_MAP.get(metricsSourceType));
    }
}
