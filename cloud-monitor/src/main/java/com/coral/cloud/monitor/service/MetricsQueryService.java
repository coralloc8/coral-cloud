package com.coral.cloud.monitor.service;


import com.coral.base.common.CollectionUtil;
import com.coral.cloud.monitor.dto.MetricsDataSourceInfoDTO;
import com.coral.cloud.monitor.dto.MetricsInfoDTO;
import com.coral.cloud.monitor.dto.MetricsRunnerSettingInfoDTO;
import com.coral.cloud.monitor.dto.MetricsSettingInfoDTO;
import com.coral.cloud.monitor.repository.MetricsDataSourceRepository;
import com.coral.cloud.monitor.repository.MetricsRunnerSettingRepository;
import com.coral.cloud.monitor.repository.MetricsSettingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className MetricsQueryService
 * @description 指标查询
 * @date 2023/4/17 18:19
 */
@Slf4j
@Service
public class MetricsQueryService {
    @Autowired
    private MetricsDataSourceRepository metricsDataSourceRepository;
    @Autowired
    private MetricsSettingRepository metricsSettingRepository;
    @Autowired
    private MetricsRunnerSettingRepository metricsRunnerSettingRepository;


    /**
     * 查询所有指标信息
     *
     * @param hospitalCode
     * @return
     */
    public List<MetricsInfoDTO> findAllMetrics(String hospitalCode) {
        List<MetricsRunnerSettingInfoDTO> runners = metricsRunnerSettingRepository.findRunnerSettings(hospitalCode);
        if (CollectionUtil.isBlank(runners)) {
            return Collections.emptyList();
        }
        Set<String> metricsNos = new HashSet<>();
        Set<String> dsNos = new HashSet<>();
        for (MetricsRunnerSettingInfoDTO runner : runners) {
            metricsNos.add(runner.getMetricsNo());
            dsNos.add(runner.getDsNo());
        }
        List<MetricsDataSourceInfoDTO> dataSources = metricsDataSourceRepository.findDataSources(hospitalCode, dsNos);
        Map<String, MetricsDataSourceInfoDTO> dataSourceMap = dataSources.stream()
                .collect(Collectors.toMap(e -> e.getDsNo(), Function.identity(), (t1, t2) -> t2));

        List<MetricsSettingInfoDTO> settings = metricsSettingRepository.findMetricsSettings(metricsNos);
        Map<String, MetricsSettingInfoDTO> settingMap = settings.stream()
                .collect(Collectors.toMap(e -> e.getMetricsNo(), Function.identity(), (t1, t2) -> t2));

        return runners.stream()
                .filter(e -> dataSourceMap.containsKey(e.getDsNo()) && settingMap.containsKey(e.getMetricsNo()))
                .map(e -> {
                    MetricsDataSourceInfoDTO ds = dataSourceMap.get(e.getDsNo());
                    MetricsSettingInfoDTO se = settingMap.get(e.getMetricsNo());
                    String uuid = String.join(":", "HON#" + hospitalCode,
                            "DSN#" + ds.getDsNo(), "MEN#" + se.getMetricsNo());
                    return new MetricsInfoDTO(uuid, ds, se, e.getEnabled());
                }).collect(Collectors.toList());
    }


}
