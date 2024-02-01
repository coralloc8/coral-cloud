package com.coral.cloud.monitor.dto;


import com.coral.base.common.convert.PojoConvert;
import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.model.MetricsDataSource;
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
 * @className MetricsDataSource
 * @description 指标数据源
 * @date 2023/4/17 8:51
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MetricsDataSourceInfoDTO implements Serializable, PojoConvert<MetricsDataSource, MetricsDataSourceInfoDTO> {

    /**
     * 数据源唯一标识
     */
    private String dsNo;

    /**
     * 医院编码
     */
    private String hospitalCode;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 数据源名称
     */
    private String dsName;

    /**
     * 数据源类型
     */
    private MetricsSourceType dsType;

    /**
     * 数据源地址
     */
    private String dsUrl;

    /**
     * 数据配置
     */
    private Map<String, Object> dsConfig;

    @Override
    public MetricsDataSourceInfoDTO convert(MetricsDataSource metricsDataSource, Class<MetricsDataSourceInfoDTO> clazz) {
        MetricsDataSourceInfoDTO dto = PojoConvert.super.convert(metricsDataSource, clazz);

        if (StringUtils.isNotBlank(metricsDataSource.getDsConfig())) {
            dto.setDsConfig(JsonUtil.toMap(metricsDataSource.getDsConfig()));
        }

        return dto;
    }
}
