package com.coral.cloud.monitor.dto;


import com.coral.base.common.convert.PojoConvert;
import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.common.enums.MetricsType;
import com.coral.cloud.monitor.model.MetricsSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className MetricsSetting
 * @description 指标配置
 * @date 2023/4/17 8:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MetricsSettingInfoDTO implements Serializable, PojoConvert<MetricsSetting, MetricsSettingInfoDTO> {

    /**
     * 指标编码
     */
    private String metricsNo;

    /**
     * 应用标识
     */
    private String applicationKey;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 指标标识
     */
    private String metricsKey;
    /**
     * 指标名称
     */
    private String metricsName;
    /**
     * 指标类型
     */
    private MetricsType metricsType;

    /**
     * 执行路径
     */
    private String execPath;

    /**
     * 执行参数
     */
    private Map<String, Object> execConfig;

    /**
     * 执行规则
     */
    private String corn;

    @Override
    public MetricsSettingInfoDTO convert(MetricsSetting metricsSetting, Class<MetricsSettingInfoDTO> clazz) {
        MetricsSettingInfoDTO dto = PojoConvert.super.convert(metricsSetting, clazz);
        if (StringUtils.isNotBlank(metricsSetting.getExecConfig())) {
            dto.setExecConfig(JsonUtil.toMap(metricsSetting.getExecConfig()));
        }
        return dto;
    }
}
