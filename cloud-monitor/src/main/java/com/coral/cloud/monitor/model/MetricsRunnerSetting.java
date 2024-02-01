package com.coral.cloud.monitor.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.coral.cloud.monitor.common.enums.NormalEnabled;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className MetricsRunnerSetting
 * @description 指标运行配置
 * @date 2023/4/17 8:51
 */
@Data
@TableName("metrics_runner_setting")
public class MetricsRunnerSetting implements Serializable {

    /**
     * 主键
     */
    private Long id;

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


    /**
     * 启用状态
     */
    private NormalEnabled enabled;

}
