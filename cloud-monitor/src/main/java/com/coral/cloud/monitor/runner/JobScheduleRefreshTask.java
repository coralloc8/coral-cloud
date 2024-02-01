package com.coral.cloud.monitor.runner;


import com.coral.base.common.CollectionUtil;
import com.coral.cloud.monitor.common.config.ServiceConfig;
import com.coral.cloud.monitor.common.util.job.ScheduleUtils;
import com.coral.cloud.monitor.dto.MetricsInfoDTO;
import com.coral.cloud.monitor.service.MetricsQueryService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className JobScheduleRefreshTask
 * @description 定时调度刷新指标任务
 * @date 2023/4/14 14:53
 */
@Component
@Slf4j
public class JobScheduleRefreshTask {


    private static final String LOG_PREFIX = "#####[Metrics Config Refresh Job]";

    @Autowired
    private ServiceConfig serviceConfig;

    @Autowired
    private MetricsQueryService metricsQueryService;

    @Autowired
    private Scheduler scheduler;


    @Async
    @Scheduled(cron = "0/30 * * * * ? ")
    public void refreshJob() {
        log.info("{} run.", LOG_PREFIX);
        try {
            List<MetricsInfoDTO> configInfos = metricsQueryService.findAllMetrics(serviceConfig.getKey());
            configInfos.forEach(e -> ScheduleUtils.runScheduleJob(scheduler, e));

            //删除已经不存在的job
            List<String> uuids = ScheduleUtils.JOB_CACHE.entrySet().stream()
                    .filter(e -> configInfos.stream().noneMatch(c -> c.getUuid().equals(e.getKey())))
                    .map(e -> e.getKey())
                    .collect(Collectors.toList());

            if (CollectionUtil.isNotBlank(uuids)) {
                log.info("{} 需要删除的任务列表为:{}.", LOG_PREFIX, uuids);
                uuids.forEach(e -> ScheduleUtils.deleteScheduleJob(scheduler, e));
            }

        } catch (Exception e) {
            log.error(LOG_PREFIX + " error.", e);
            throw new RuntimeException(e);
        }
        log.info("{} end.", LOG_PREFIX);
    }

    public void initJobs() throws SchedulerException {
        log.info(">>>>>[All Job Init] start.");
        ScheduleUtils.deleteAllJobs(scheduler);
        List<MetricsInfoDTO> configInfos = metricsQueryService.findAllMetrics(serviceConfig.getKey());
        configInfos.forEach(e -> ScheduleUtils.runScheduleJob(scheduler, e));
        log.info(">>>>>[All Job Init] end.");
    }


}
