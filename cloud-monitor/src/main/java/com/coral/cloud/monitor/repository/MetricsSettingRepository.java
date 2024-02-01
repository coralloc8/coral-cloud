package com.coral.cloud.monitor.repository;


import com.coral.cloud.monitor.dto.MetricsSettingInfoDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className MetricsSettingRepository
 * @description 指标设置
 * @date 2023/4/17 11:15
 */
public interface MetricsSettingRepository {


    /**
     * 查询指标配置
     *
     * @param metricsNo
     * @return
     */
    Optional<MetricsSettingInfoDTO> findMetricsSetting(String metricsNo);


    /**
     * 查询指标配置
     *
     * @param metricsNos
     * @return
     */
    List<MetricsSettingInfoDTO> findMetricsSettings(Collection<String> metricsNos);

}
