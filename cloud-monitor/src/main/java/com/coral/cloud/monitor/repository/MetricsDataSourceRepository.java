package com.coral.cloud.monitor.repository;


import com.coral.cloud.monitor.dto.MetricsDataSourceInfoDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className MetricsDataSourceRepository
 * @description 指标数据源
 * @date 2023/4/17 11:13
 */
public interface MetricsDataSourceRepository {

    /**
     * 查询数据源
     *
     * @param hospitalCode
     * @param dsNo
     * @return
     */
    Optional<MetricsDataSourceInfoDTO> findDataSource(String hospitalCode, String dsNo);

    /**
     * 查询数据源
     *
     * @param hospitalCode
     * @param dsNos
     * @return
     */
    List<MetricsDataSourceInfoDTO> findDataSources(String hospitalCode, Collection<String> dsNos);
}
