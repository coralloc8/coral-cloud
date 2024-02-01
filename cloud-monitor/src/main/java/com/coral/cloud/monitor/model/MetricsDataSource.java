package com.coral.cloud.monitor.model;

import com.baomidou.mybatisplus.annotation.TableName;

import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.common.enums.NormalEnabled;
import com.coral.cloud.monitor.common.enums.NormalStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className MetricsDataSource
 * @description 指标数据源
 * @date 2023/4/17 8:51
 */
@Data
@TableName("metrics_data_source")
public class MetricsDataSource implements Serializable {

    /**
     * 主键
     */
    private Long id;


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
    private String dsConfig;

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
