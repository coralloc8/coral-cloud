package com.coral.cloud.monitor.model;

import com.baomidou.mybatisplus.annotation.TableName;

import com.coral.cloud.monitor.common.enums.MetricsType;
import com.coral.cloud.monitor.common.enums.NormalEnabled;
import com.coral.cloud.monitor.common.enums.NormalStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className MetricsSetting
 * @description 指标配置
 * @date 2023/4/17 8:51
 */
@Data
@TableName("metrics_setting")
public class MetricsSetting implements Serializable {


    /**
     * 主键
     */
    private Long id;


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
    private String execConfig;

    /**
     * 执行规则
     */
    private String corn;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    private NormalStatus status;

    /**
     * 启用状态
     */
    private NormalEnabled enabled;

}
