package com.coral.database.test.mybatis.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.coral.database.test.mybatis.config.datasource.primary.DataSourcePrimaryProperty;
import com.coral.database.test.mybatis.config.datasource.secondary.DataSourceSecondaryProperty;
import com.coral.database.test.mybatis.config.datasource.tertiary.DataSourceTertiaryProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.sql.SQLException;

/**
 * @author huss
 * @version 1.0
 * @className DataSourceConfiguration
 * @description
 * @date 2021/5/21 13:21
 */
@Configuration
public class DataSourceConfig {

    @Autowired
    private DataSourcePrimaryProperty dataSourcePrimaryProperty;

    @Autowired
    private DataSourceSecondaryProperty dataSourceSecondaryProperty;

    @Autowired
    private DataSourceTertiaryProperty dataSourceTertiaryProperty;

    @Bean(name = "dataSourcePrimary")
    public DataSource dataSourcePrimary() {
        DruidXADataSource druidXADataSource = this.convert(dataSourcePrimaryProperty);
        //
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("dataSourcePrimary");
        this.setAtomikosDataSourceBean(xaDataSource, dataSourcePrimaryProperty);
        return xaDataSource;
    }


    @Bean(name = "dataSourceSecondary")
    public DataSource dataSourceSecondary() {
        DruidXADataSource druidXADataSource = this.convert(dataSourceSecondaryProperty);
        //
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("dataSourceSecondary");
        this.setAtomikosDataSourceBean(xaDataSource, dataSourceSecondaryProperty);
        return xaDataSource;
    }

    @Bean(name = "dataSourceTertiary")
    public DataSource dataSourceTertiary() {
        DruidXADataSource druidXADataSource = this.convert(dataSourceTertiaryProperty);
        //
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(druidXADataSource);
        xaDataSource.setUniqueResourceName("dataSourceTertiary");
        this.setAtomikosDataSourceBean(xaDataSource, dataSourceTertiaryProperty);
        return xaDataSource;
    }

//    @Bean(name = "transactionManager")
//    @Primary
//    public TransactionManager regTransactionManager() {
//        UserTransactionManager userTransactionManager = new UserTransactionManager();
//        UserTransaction userTransaction = new UserTransactionImp();
//        return new JtaTransactionManager(userTransaction, userTransactionManager);
//    }

    private void setAtomikosDataSourceBean(AtomikosDataSourceBean atomikosDataSourceBean,
                                           AtomikosDataSourceProperty atomikosDataSourceProperty) {
        try {
            atomikosDataSourceBean.setMinPoolSize(atomikosDataSourceProperty.getMinPoolSize());
            atomikosDataSourceBean.setMaxPoolSize(atomikosDataSourceProperty.getMaxPoolSize());
            atomikosDataSourceBean.setMaxLifetime(atomikosDataSourceProperty.getMaxLifetime());
            atomikosDataSourceBean.setBorrowConnectionTimeout(atomikosDataSourceProperty.getBorrowConnectionTimeout());
            atomikosDataSourceBean.setLoginTimeout(atomikosDataSourceProperty.getLoginTimeout());

            atomikosDataSourceBean.setMaintenanceInterval(atomikosDataSourceProperty.getMaintenanceInterval());
            atomikosDataSourceBean.setMaxIdleTime(atomikosDataSourceProperty.getMaxIdleTime());
            atomikosDataSourceBean.setTestQuery(atomikosDataSourceProperty.getTestQuery());
        } catch (SQLException r) {
            r.printStackTrace();
        }
    }

    private DruidXADataSource convert(DruidDataSource dataSource) {
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
