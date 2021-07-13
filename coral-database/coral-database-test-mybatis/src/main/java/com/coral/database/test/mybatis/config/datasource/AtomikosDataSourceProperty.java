package com.coral.database.test.mybatis.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;

/**
 * @author huss
 * @version 1.0
 * @className AtomikosDataSourceProperty
 * @description Atomikos 属性配置
 * @date 2021/7/13 19:35
 */
@Data
public class AtomikosDataSourceProperty extends DruidDataSource {

    protected int minPoolSize;
    protected int maxPoolSize;
    protected int maxLifetime;
    protected int borrowConnectionTimeout;
    protected int loginTimeout;
    protected int maintenanceInterval;
    protected int maxIdleTime;
    protected String testQuery;

}
