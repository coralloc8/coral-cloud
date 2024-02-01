package com.coral.cloud.monitor.common.config.datasource;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceConfig
 * @description 数据源配置
 * @date 2022/3/8 17:50
 */
@Slf4j
@Configuration
public class AtomikosDataSourceConfig {

    @Autowired
    private MonitorDataSourceProperty monitorDataSourceProperty;


    @Primary
    @QuartzDataSource
    @Bean(name = "monitorDataSource")
    public DataSource dataSource() {
        log.info(">>>>>monitorDataSource init...");
        DruidXADataSource druidXADataSource = this.convert(monitorDataSourceProperty);
        //
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("monitorDataSource");
        this.setAtomikosDataSourceBean(xaDataSource, monitorDataSourceProperty);
        return xaDataSource;
    }

    private void setAtomikosDataSourceBean(AtomikosDataSourceBean atomikosDataSourceBean,
                                           MonitorDataSourceProperty monitorDataSourceProperty) {
        try {
            MonitorDataSourceProperty.Atomikos atomikos = monitorDataSourceProperty.getAtomikos();
            atomikosDataSourceBean.setMinPoolSize(atomikos.getMinPoolSize());
            atomikosDataSourceBean.setMaxPoolSize(atomikos.getMaxPoolSize());
            atomikosDataSourceBean.setMaxLifetime(atomikos.getMaxLifetime());
            atomikosDataSourceBean.setBorrowConnectionTimeout(atomikos.getBorrowConnectionTimeout());
            atomikosDataSourceBean.setLoginTimeout(atomikos.getLoginTimeout());
            atomikosDataSourceBean.setMaintenanceInterval(atomikos.getMaintenanceInterval());
            atomikosDataSourceBean.setMaxIdleTime(atomikos.getMaxIdleTime());
            atomikosDataSourceBean.setTestQuery(atomikos.getTestQuery());
        } catch (SQLException r) {
            r.printStackTrace();
        }
    }

    private DruidXADataSource convert(MonitorDataSourceProperty dataSource) {
        try {
            DruidXADataSource druidDataSource = new DruidXADataSource();
            druidDataSource.setUrl(dataSource.getUrl());
            druidDataSource.setUsername(dataSource.getUsername());
            druidDataSource.setPassword(dataSource.getPassword());
            druidDataSource.setDriverClassName(dataSource.getDriverClassName());
            druidDataSource.setFilters(String.join(",", dataSource.getFilterClassNames()));
            druidDataSource.setMaxActive(dataSource.getMaxActive());
            druidDataSource.setInitialSize(dataSource.getInitialSize());
            druidDataSource.setMaxWait(dataSource.getMaxWait());
            druidDataSource.setMinIdle(dataSource.getMinIdle());
            druidDataSource.setTimeBetweenEvictionRunsMillis(dataSource.getTimeBetweenEvictionRunsMillis());
            druidDataSource.setMaxEvictableIdleTimeMillis(dataSource.getMaxEvictableIdleTimeMillis());
            druidDataSource.setMinEvictableIdleTimeMillis(dataSource.getMinEvictableIdleTimeMillis());
            druidDataSource.setValidationQuery(dataSource.getValidationQuery());
            druidDataSource.setTestWhileIdle(dataSource.isTestWhileIdle());
            druidDataSource.setTestOnBorrow(dataSource.isTestOnBorrow());
            druidDataSource.setTestOnReturn(dataSource.isTestOnReturn());
            druidDataSource.setKeepAlive(dataSource.isKeepAlive());
            druidDataSource.setConnectProperties(dataSource.getConnectProperties());
            return druidDataSource;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
