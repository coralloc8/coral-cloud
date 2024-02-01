package com.coral.cloud.monitor.dto;


import com.coral.base.common.convert.PojoConvert;
import com.coral.cloud.monitor.common.enums.NormalEnabled;
import com.coral.cloud.monitor.model.MetricsRunnerSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huss
 * @version 1.0
 * @className MetricsRunnerSetting
 * @description 指标运行配置
 * @date 2023/4/17 8:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MetricsRunnerSettingInfoDTO implements Serializable, PojoConvert<MetricsRunnerSetting, MetricsRunnerSettingInfoDTO> {

    /**
     * 医院编码
     */
    private String hospitalCode;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 指标编码
     */
    private String metricsNo;

    /**
     * 数据源唯一标识
     */
    private String dsNo;

    /**
     * 启用状态
     */
    private NormalEnabled enabled;

}
