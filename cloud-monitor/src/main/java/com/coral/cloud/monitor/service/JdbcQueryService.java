package com.coral.cloud.monitor.service;


import com.coral.cloud.monitor.common.config.datasource.MonitorDataSourceProperty;
import com.coral.cloud.monitor.common.util.JdbcCacheUtil;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className JdbcQueryService
 * @description jdbc查询
 * @date 2023/4/14 9:14
 */
@Slf4j
@Service
public class JdbcQueryService {

    @Autowired
    private MonitorDataSourceProperty monitorDataSource;

    /**
     * 查询数量
     *
     * @param metricsExecConfInfo
     * @return
     */
    public Long queryCount(MetricsExecConfInfoDTO metricsExecConfInfo) {
        String queryCountSql = metricsExecConfInfo.getExecPath();
        Optional<JdbcTemplate> jdbcTemplateOpt = JdbcCacheUtil.getJdbcTemplate(metricsExecConfInfo, monitorDataSource);
        if (!jdbcTemplateOpt.isPresent()) {
            return 0L;
        }
        JdbcTemplate jdbcTemplate = jdbcTemplateOpt.get();
        Long total = jdbcTemplate.queryForObject(queryCountSql, Long.class);
        log.info(">>>>>[SQL]:{},[结果集]:{}", queryCountSql, total);
        return total;
    }


}
