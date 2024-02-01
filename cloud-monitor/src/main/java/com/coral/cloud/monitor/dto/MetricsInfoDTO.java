package com.coral.cloud.monitor.dto;

import com.coral.cloud.monitor.common.enums.NormalEnabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huss
 * @version 1.0
 * @className MetricsInfoDTO
 * @description 指标信息
 * @date 2023/4/17 18:22
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MetricsInfoDTO implements Serializable {

    private String uuid;

    private MetricsDataSourceInfoDTO metricsDataSource;

    private MetricsSettingInfoDTO metricsSetting;

    private NormalEnabled enabled;
}
