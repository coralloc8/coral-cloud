package com.coral.cloud.monitor.dto.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceInfoDTO
 * @description 数据源信息
 * @date 2023/4/14 9:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSourceInfoDTO {

    /**
     * 数据源编码
     */
    private String dataSourceNo;

    private String url;

    private String username;

    private String password;

    private String driverClassName;
}
