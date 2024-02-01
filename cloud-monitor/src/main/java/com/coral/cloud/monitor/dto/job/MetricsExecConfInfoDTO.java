package com.coral.cloud.monitor.dto.job;

import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.common.enums.MetricsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className MetricsExecConfInfoDTO
 * @description 指标执行配置信息
 * @date 2023/4/13 14:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricsExecConfInfoDTO {



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
     * 医院编码
     */
    private String hospitalCode;

    /**
     * 医院名称
     */
    private String hospitalName;

    /**
     * 数据源类型
     */
    private MetricsSourceType sourceType;

    /**
     * 数据源编号
     */
    private  String dataSourceNo;

    /**
     * 数据源
     */
    private String dataSource;

    /**
     * 扩展配置
     */
    private Map<String, Object> extraConfigs;

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
    private Map<String, Object> params;

}
