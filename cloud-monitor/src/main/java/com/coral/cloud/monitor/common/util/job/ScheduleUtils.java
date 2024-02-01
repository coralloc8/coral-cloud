package com.coral.cloud.monitor.common.util.job;

import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.common.enums.NormalEnabled;
import com.coral.cloud.monitor.dto.MetricsInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ScheduleUtils {
    private final static String JOB_NAME = "[TASK]";

    public final static String GROUP_NAME = "MONITOR";
    /**
     * 任务调度参数key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    public static final Map<String, String> JOB_CACHE = new ConcurrentHashMap<>(32);


    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(String jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId, GROUP_NAME);
    }

    /**
     * 获取所有的job
     */
    public static List<JobKey> getAllJobs(Scheduler scheduler, String groupName) {
        List<JobKey> list = new ArrayList<>();
        try {
            if (StringUtils.isBlank(groupName)) {
                for (String gr : scheduler.getJobGroupNames()) {
                    list.addAll(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(gr)));
                }
            } else {
                list.addAll(scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void deleteAllJobs(Scheduler scheduler) throws SchedulerException {
        List<JobKey> jobKeys = getAllJobs(scheduler, GROUP_NAME);
        scheduler.deleteJobs(jobKeys);
        JOB_CACHE.clear();
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(String jobId) {
        return JobKey.jobKey(JOB_NAME + jobId, GROUP_NAME);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, String jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行任务（创建完成启动或修改后重新启动）
     *
     * @param scheduler
     * @param metrics
     */
    public synchronized static void runScheduleJob(Scheduler scheduler, MetricsInfoDTO metrics) {
        String json = JsonUtil.toJson(metrics);
        log.debug(">>>>>[Job run] start. metrics:{}", json);
        String md5 = DigestUtils.md5Hex(json);
        String cache = JOB_CACHE.get(metrics.getUuid());
        if (StringUtils.isBlank(cache)) {
            log.info(">>>>>[Job run] 任务不存在,创建. uuid:{}", metrics.getUuid());
            createScheduleJob(scheduler, metrics);
            JOB_CACHE.put(metrics.getUuid(), md5);
        } else if (StringUtils.isNotBlank(cache) && !md5.equals(cache)) {
            log.info(">>>>>[Job run] 任务存在,但数据MD5不一致,重新更新任务. uuid:{}", metrics.getUuid());
            updateScheduleJob(scheduler, metrics);
            JOB_CACHE.put(metrics.getUuid(), md5);
        }
        log.debug(">>>>>[Job run] end.");
    }

    /**
     * 创建定时任务
     */
    private static void createScheduleJob(Scheduler scheduler, MetricsInfoDTO metrics) {
        try {
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                    .withIdentity(getJobKey(metrics.getUuid()))
                    .build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(metrics.getMetricsSetting().getCorn())
                    .withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(metrics.getUuid()))
                    .withSchedule(scheduleBuilder).build();

            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(JOB_PARAM_KEY, JsonUtil.toJson(metrics));

            scheduler.scheduleJob(jobDetail, trigger);
            //暂停或恢复任务任务
            pauseOrResume(scheduler, metrics);

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        log.info(">>>>>[Job create] end.");
    }

    /**
     * 更新定时任务
     */
    private static void updateScheduleJob(Scheduler scheduler, MetricsInfoDTO metrics) {
        try {
            TriggerKey triggerKey = getTriggerKey(metrics.getUuid());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(metrics.getMetricsSetting().getCorn())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, metrics.getUuid());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            //参数
            trigger.getJobDataMap().put(JOB_PARAM_KEY, JsonUtil.toJson(metrics));

            scheduler.rescheduleJob(triggerKey, trigger);
            //暂停或恢复任务任务
            pauseOrResume(scheduler, metrics);

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停或恢复任务任务
     *
     * @param scheduler
     * @param metrics
     */
    private static void pauseOrResume(Scheduler scheduler, MetricsInfoDTO metrics) {
        if (metrics.getEnabled() == NormalEnabled.DISABLE) {
            pauseJob(scheduler, metrics.getUuid());
            //恢复任务
        } else if (metrics.getEnabled() == NormalEnabled.ENABLE) {
            resumeJob(scheduler, metrics.getUuid());
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, MetricsInfoDTO metrics) {
        try {
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(JOB_PARAM_KEY, metrics);

            scheduler.triggerJob(getJobKey(metrics.getUuid()), dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, String jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobId) {
        try {
            //暂停任务
            scheduler.pauseJob(getJobKey(jobId));
            //移除触发
            CronTrigger trigger = getCronTrigger(scheduler, jobId);
            if (Objects.isNull(trigger)) {
                JOB_CACHE.remove(jobId);
            }
            scheduler.unscheduleJob(trigger.getKey());
            //删除任务
            scheduler.deleteJob(getJobKey(jobId));
            JOB_CACHE.remove(jobId);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
}