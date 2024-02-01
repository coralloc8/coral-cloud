package com.coral.cloud.monitor.repository;


import com.coral.cloud.monitor.dto.MetricsRunnerSettingInfoDTO;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className MetricsRunnerSettingRepository
 * @description 指标运行设置
 * @date 2023/4/17 11:15
 */
public interface MetricsRunnerSettingRepository {
    /**
     * 查询运行指标
     *
     * @param hospitalCode
     * @return
     */
    List<MetricsRunnerSettingInfoDTO> findRunnerSettings(String hospitalCode);
}
