package com.coral.cloud.monitor.runner;


import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.dto.MetricsInfoDTO;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;
import com.coral.cloud.monitor.metrics.IMetricsService;
import com.coral.cloud.monitor.metrics.MetricsServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className JobScheduleTask
 * @description 定时调度任务
 * @date 2023/4/14 14:53
 */
@Component
@Slf4j
public class JobScheduleTask {

    private static final String LOG_PREFIX = "#####[Metrics job]";

    @Async
    public void run(MetricsInfoDTO metricsInfo) {
        log.info("{} run.uuid:{}", LOG_PREFIX, metricsInfo.getUuid());
        try {
            MetricsSourceType metricsSourceType = metricsInfo.getMetricsDataSource().getDsType();

            MetricsExecConfInfoDTO execConf = MetricsExecConfInfoDTO.builder()
                    .applicationKey(metricsInfo.getMetricsSetting().getApplicationKey())
                    .applicationName(metricsInfo.getMetricsSetting().getApplicationName())
                    .hospitalCode(metricsInfo.getMetricsDataSource().getHospitalCode())
                    .hospitalName(metricsInfo.getMetricsDataSource().getHospitalName())
                    .metricsKey(metricsInfo.getMetricsSetting().getMetricsKey())
                    .metricsName(metricsInfo.getMetricsSetting().getMetricsName())
                    .sourceType(metricsInfo.getMetricsDataSource().getDsType())
                    .dataSourceNo(metricsInfo.getMetricsDataSource().getDsNo())
                    .dataSource(metricsInfo.getMetricsDataSource().getDsUrl())
                    .extraConfigs(metricsInfo.getMetricsDataSource().getDsConfig())
                    .metricsType(metricsInfo.getMetricsSetting().getMetricsType())
                    .execPath(metricsInfo.getMetricsSetting().getExecPath())
                    .params(metricsInfo.getMetricsSetting().getExecConfig())
                    .build();

            Optional<IMetricsService> metricsServiceOpt = MetricsServiceFactory.findMessageHandler(metricsSourceType);

            metricsServiceOpt.ifPresent(service -> service.run(execConf));
        } catch (Exception e) {
            log.error(LOG_PREFIX + " error.", e);
            throw new RuntimeException(e);
        }
        log.info("{} end.", LOG_PREFIX);
    }


}
