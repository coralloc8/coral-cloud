package com.coral.cloud.monitor.common.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className AtomikosDataSourceProperty
 * @description Atomikos 属性配置
 * @date 2021/7/13 19:35
 */

@Component
@ConfigurationProperties(prefix = "spring.datasource.monitor")
public class MonitorDataSourceProperty extends DruidDataSource {

    /**
     * jta
     */
    @Getter
    @Setter
    private Atomikos atomikos;

    @Data
    public static class Atomikos {
        private Integer minPoolSize;
        private Integer maxPoolSize;
        private Integer maxLifetime;
        private Integer borrowConnectionTimeout;
        private Integer loginTimeout;
        private Integer maintenanceInterval;
        private Integer maxIdleTime;
        private String testQuery;
    }


}
