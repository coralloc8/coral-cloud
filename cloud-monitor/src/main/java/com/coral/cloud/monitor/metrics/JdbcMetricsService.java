package com.coral.cloud.monitor.metrics;


import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.common.util.ApiUtil;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.ApiResponseInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.ErrorResponseInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.IResponse;
import com.coral.cloud.monitor.service.JdbcQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author huss
 * @version 1.0
 * @className JdbcMetricsService
 * @description jdbc指标服务
 * @date 2023/4/13 17:51
 */
@Component
@Slf4j
public class JdbcMetricsService implements IMetricsService {
    @Autowired
    private MetricsFactory metricsFactory;

    @Autowired
    private JdbcQueryService jdbcQueryService;

    private static final String LOG_PREFIX = ">>>>>[Jdbc Metrics Timer]";

    @Override
    public MetricsSourceType metricsSourceType() {
        return MetricsSourceType.JDBC;
    }

    @Override
    public void run(MetricsExecConfInfoDTO metricsExecConf) {
        this.timerRun(metricsExecConf);
        this.commonRun(metricsExecConf);
    }

    private void commonRun(MetricsExecConfInfoDTO metricsExecConf) {
        if (isTimer(metricsExecConf.getMetricsType())) {
            return;
        }
        log.info("{} run start.", LOG_PREFIX);
        double value = 0;
        String exceptionClass = "";
        try {
            value = this.queryTotal(metricsExecConf);
        } catch (Exception e) {
            log.error(LOG_PREFIX + " run error", e);
            exceptionClass = ApiUtil.getExceptionSimpleName(e);
        } finally {
            metricsFactory.record(metricsExecConf, exceptionClass, value);
        }
        log.info("{} run end.", LOG_PREFIX);
    }

    private void timerRun(MetricsExecConfInfoDTO metricsExecConf) {
        if (!isTimer(metricsExecConf.getMetricsType())) {
            return;
        }
        log.info("{} run start.", LOG_PREFIX);
        String watchId = UUID.randomUUID().toString().replace("-", "");
        IResponse response = null;
        try {
            metricsFactory.watchStart(watchId, metricsExecConf, 0);
            this.queryTotal(metricsExecConf);

            response = ApiResponseInfoDTO.builder()
                    .status("200")
                    .errorCode("0")
                    .errorMsg("success")
                    .build();
        } catch (Exception e) {
            log.error(LOG_PREFIX + " run error", e);
            response = new ErrorResponseInfoDTO(ApiUtil.getExceptionSimpleName(e));
        } finally {
            metricsFactory.watchStop(watchId, metricsExecConf, response);
            log.info("{} run end.", LOG_PREFIX);
        }
    }


    private Long queryTotal(MetricsExecConfInfoDTO metricsExecConf) {
        return jdbcQueryService.queryCount(metricsExecConf);
    }

}
