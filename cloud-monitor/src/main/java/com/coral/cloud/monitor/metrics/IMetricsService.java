package com.coral.cloud.monitor.metrics;


import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.common.enums.MetricsType;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;

/**
 * @author huss
 * @version 1.0
 * @className IMetricsService
 * @description 指标服务
 * @date 2023/4/13 17:52
 */
public interface IMetricsService {

    /**
     * 指标源类型
     *
     * @return
     */
    MetricsSourceType metricsSourceType();

    /**
     * 执行
     *
     * @param metricsExecConf
     */
    void run(MetricsExecConfInfoDTO metricsExecConf);

    /**
     * 查找配置
     *
     * @param key
     * @param metricsExecConf
     * @param <T>
     * @return
     */
    default <T> T findConf(String key, MetricsExecConfInfoDTO metricsExecConf) {
        return metricsExecConf.getParams().containsKey(key) ?
                (T) metricsExecConf.getParams().get(key) : null;
    }

    default boolean isTimer(MetricsType metricsType) {
        return MetricsType.TIMER.equals(metricsType) || MetricsType.LONG_TASK_TIMER.equals(metricsType);
    }

}
