package com.coral.cloud.monitor.common.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.common.config.datasource.MonitorDataSourceProperty;
import com.coral.cloud.monitor.dto.job.DataSourceInfoDTO;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * jdbc缓存
 */
@Slf4j
public class JdbcCacheUtil {
    private final static Map<String, JdbcTemplate> JDBC_CACHE = new ConcurrentHashMap<>(32);
    private final static Map<String, String> JDBC_MD5_CACHE = new ConcurrentHashMap<>(32);

    /**
     * 获取jdbc
     *
     * @param metricsExecConf
     * @param monitorDataSource
     * @return
     */
    public static Optional<JdbcTemplate> getJdbcTemplate(MetricsExecConfInfoDTO metricsExecConf, MonitorDataSourceProperty monitorDataSource) {
        refreshJdbcTemplate(metricsExecConf, monitorDataSource);
        JdbcTemplate template = JDBC_CACHE.get(metricsExecConf.getDataSourceNo());
        if (Objects.isNull(template)) {
            log.error(">>>>无法获取到数据源.入参:{}", metricsExecConf);
        }
        return Optional.ofNullable(template);
    }

    /**
     * 构建数据源
     *
     * @param metricsExecConf
     * @return
     */
    public static DataSourceInfoDTO buildDataSourceInfo(MetricsExecConfInfoDTO metricsExecConf) {
        DataSourceInfoDTO dataSourceInfo = JsonUtil.toPojo(metricsExecConf.getExtraConfigs(), DataSourceInfoDTO.class);
        dataSourceInfo.setUrl(metricsExecConf.getDataSource());
        dataSourceInfo.setDataSourceNo(metricsExecConf.getDataSourceNo());
        return dataSourceInfo;
    }

    /**
     * 刷存jdbc缓存
     *
     * @param metricsExecConf
     * @param monitorDataSource
     * @return
     */
    public static void refreshJdbcTemplate(MetricsExecConfInfoDTO metricsExecConf, MonitorDataSourceProperty monitorDataSource) {
        DataSourceInfoDTO dataSourceInfo = buildDataSourceInfo(metricsExecConf);
        String key = dataSourceInfo.getDataSourceNo();
        String val = String.join("", dataSourceInfo.getUrl(),
                dataSourceInfo.getDriverClassName(),
                dataSourceInfo.getUsername(),
                dataSourceInfo.getPassword()
        );
        String md5Key = DigestUtils.md5Hex(val);
        String oldMd5Val = JDBC_MD5_CACHE.getOrDefault(key, "");
        if (md5Key.equals(oldMd5Val) && JDBC_CACHE.containsKey(key)) {
            log.info(">>>>>[jdbc编码:{}]数据无变动,不刷新配置", key);
            return;
        } else {
            log.info(">>>>>[jdbc编码:{}]数据变动,重新刷新配置", key);
        }

        try {
            DataSource dataSource = initDataSource(dataSourceInfo, monitorDataSource);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            JDBC_CACHE.put(key, jdbcTemplate);
            JDBC_MD5_CACHE.put(key, md5Key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static DataSource initDataSource(DataSourceInfoDTO dataSourceInfo, MonitorDataSourceProperty monitorDataSource) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(dataSourceInfo.getDriverClassName());
        dataSource.setUrl(dataSourceInfo.getUrl());
        dataSource.setUsername(dataSourceInfo.getUsername());
        dataSource.setPassword(dataSourceInfo.getPassword());

        dataSource.setFilters(String.join(",", monitorDataSource.getFilterClassNames()));
        dataSource.setMaxActive(monitorDataSource.getMaxActive());
        dataSource.setInitialSize(monitorDataSource.getInitialSize());
        dataSource.setMaxWait(monitorDataSource.getMaxWait());
        dataSource.setMinIdle(monitorDataSource.getMinIdle());
        dataSource.setTimeBetweenEvictionRunsMillis(monitorDataSource.getTimeBetweenEvictionRunsMillis());
        dataSource.setMaxEvictableIdleTimeMillis(monitorDataSource.getMaxEvictableIdleTimeMillis());
        dataSource.setMinEvictableIdleTimeMillis(monitorDataSource.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(monitorDataSource.getValidationQuery());
        dataSource.setTestWhileIdle(monitorDataSource.isTestWhileIdle());
        dataSource.setTestOnBorrow(monitorDataSource.isTestOnBorrow());
        dataSource.setTestOnReturn(monitorDataSource.isTestOnReturn());
        dataSource.setKeepAlive(monitorDataSource.isKeepAlive());
        dataSource.setConnectProperties(monitorDataSource.getConnectProperties());
        dataSource.init();
        return dataSource;
    }

}
