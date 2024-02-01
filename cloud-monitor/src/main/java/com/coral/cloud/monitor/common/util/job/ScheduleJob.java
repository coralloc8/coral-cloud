package com.coral.cloud.monitor.common.util.job;


import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.dto.MetricsInfoDTO;
import com.coral.cloud.monitor.runner.JobScheduleTask;
import com.coral.web.core.support.SpringContext;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Slf4j
public class ScheduleJob extends QuartzJobBean {
    private static final String TASK_DEF_NAME = "jobScheduleTask";

    @Override
    protected void executeInternal(JobExecutionContext context) {
        //任务开始时间
        long startTime = System.currentTimeMillis();
        MetricsInfoDTO metrics = null;
        try {
            String json = context.getMergedJobDataMap().getString(ScheduleUtils.JOB_PARAM_KEY);
            metrics = JsonUtil.parse(json, MetricsInfoDTO.class);
            //执行任务
            log.info("任务准备执行,任务ID：{}", metrics.getUuid());
            JobScheduleTask jobScheduleTask = SpringContext.getBeanByName(JobScheduleTask.class, TASK_DEF_NAME);
            jobScheduleTask.run(metrics);
            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.info("任务执行完毕，任务ID：{}  总共耗时：{} 毫秒", metrics.getUuid(), times);
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：{}", metrics.getUuid(), e);
        }
    }
}