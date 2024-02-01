package com.coral.cloud.monitor.metrics;

import com.coral.base.common.StringPool;
import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.common.util.ApiUtil;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.ApiResponseInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.ErrorResponseInfoDTO;
import com.coral.cloud.monitor.dto.metresponse.IResponse;
import com.google.common.util.concurrent.AtomicDouble;
import io.micrometer.core.instrument.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.coral.cloud.monitor.common.constants.MetricsKey.*;


/**
 * @author huss
 * @version 1.0
 * @className MetricsFactory
 * @description 指标工厂
 * @date 2023/4/12 10:54
 */
@Slf4j
@Component
public class MetricsFactory {

    /**
     * 此处仅考虑单节点部署
     */
    private Map<String, Object> METER_MAP = new ConcurrentHashMap<>(32);

    @Autowired
    private MeterRegistry meterRegistry;

    /**
     * 记录数据
     *
     * @param metricsExecConf
     * @param exceptionClass
     * @param value
     */
    public void record(MetricsExecConfInfoDTO metricsExecConf, String exceptionClass, double value) {
        log.info(">>>>>[Metrics] record. metricsExecConf:{},value:{}",
                JsonUtil.toJson(metricsExecConf), value);
        switch (metricsExecConf.getMetricsType()) {
            case COUNTER:
                counterIncrement(metricsExecConf, exceptionClass);
                break;
            case GAUGE:
                gaugeSet(metricsExecConf, exceptionClass, value);
                break;
            case SUMMARY:
                summaryRecord(metricsExecConf, exceptionClass, value);
                break;
        }
    }


    /**
     * 开始计时
     *
     * @param watchId
     * @param metricsExecConf
     * @param value
     */
    public void watchStart(String watchId, MetricsExecConfInfoDTO metricsExecConf, double value) {
        log.info(">>>>>[Metrics] start. watchId:{},metricsExecConf:{},value:{}",
                watchId, JsonUtil.toJson(metricsExecConf), value);
        switch (metricsExecConf.getMetricsType()) {
            case LONG_TASK_TIMER:
                longTaskTimerStart(watchId, metricsExecConf, null);
                break;
            case TIMER:
                timerStart(metricsExecConf.getApplicationKey(), metricsExecConf.getMetricsKey(), watchId);
                break;
        }
    }

    /**
     * 结束计时
     *
     * @param watchId
     * @param metricsExecConf
     * @param response
     */
    public void watchStop(String watchId, MetricsExecConfInfoDTO metricsExecConf, IResponse response) {
        log.info(">>>>>[Metrics] stop. watchId:{},response:{}",
                watchId, Objects.nonNull(response) ? JsonUtil.toJson(response) : null);
        switch (metricsExecConf.getMetricsType()) {
            case TIMER:
                timerStop(watchId, metricsExecConf, response);
                break;
            case LONG_TASK_TIMER:
                if (MetricsSourceType.API.equals(metricsExecConf.getSourceType())) {
                    longTaskTimerStop(metricsExecConf.getApplicationKey(), metricsExecConf.getMetricsKey(), watchId);
                }
                break;
        }
    }


    /**
     * 计算器增加值
     *
     * @param metricsExecConf
     * @param exceptionClass
     */
    private void counterIncrement(MetricsExecConfInfoDTO metricsExecConf, String exceptionClass) {
        String name = this.createMetricsKey(metricsExecConf.getApplicationKey(), metricsExecConf.getMetricsKey());
        Counter counter = (Counter) METER_MAP.computeIfAbsent(name, t -> {
            Iterable<Tag> tags = this.createAllTags(metricsExecConf, exceptionClass);
            return meterRegistry.counter(name, tags);
        });
        counter.increment();
    }

    /**
     * 计数器值获取
     *
     * @param application
     * @param metricsTag
     * @return
     */
    public double counterCount(String application, String metricsTag) {
        Counter counter = this.getByKey(application, metricsTag);
        return Objects.nonNull(counter) ? counter.count() : 0;
    }


    /**
     * 仪表盘设置值
     *
     * @param metricsExecConf
     * @param exceptionClass
     * @param num
     */
    private void gaugeSet(MetricsExecConfInfoDTO metricsExecConf, String exceptionClass, double num) {
        String name = this.createMetricsKey(metricsExecConf.getApplicationKey(), metricsExecConf.getMetricsKey());
        AtomicDouble gauge = (AtomicDouble) METER_MAP.computeIfAbsent(name, t -> {
            Tags tags = this.createAllTags(metricsExecConf, exceptionClass);
            return meterRegistry.gauge(name, tags, new AtomicDouble(0));
        });
        gauge.set(num);
    }

    /**
     * 仪表盘值获取
     *
     * @param application
     * @param metricsTag
     * @return
     */
    public double gaugeGet(String application, String metricsTag) {
        AtomicDouble atomicDouble = this.getByKey(application, metricsTag);
        return Objects.nonNull(atomicDouble) ? atomicDouble.get() : 0;
    }

    /**
     * 摘要记录
     *
     * @param metricsExecConf
     * @param exceptionClass
     * @param num
     */
    private void summaryRecord(MetricsExecConfInfoDTO metricsExecConf, String exceptionClass, double num) {
        String name = this.createMetricsKey(metricsExecConf.getApplicationKey(), metricsExecConf.getMetricsKey());
        DistributionSummary summary = (DistributionSummary) METER_MAP.computeIfAbsent(name, t -> {
            Tags tags = this.createAllTags(metricsExecConf, exceptionClass);
            return meterRegistry.summary(name, tags);
        });
        summary.record(num);
    }

    /**
     * 摘要次数
     *
     * @param application
     * @param metricsTag
     * @return
     */
    public double summaryCount(String application, String metricsTag) {
        DistributionSummary summary = this.getByKey(application, metricsTag);
        return Objects.nonNull(summary) ? summary.count() : 0;
    }

    /**
     * 摘要总值
     *
     * @param application
     * @param metricsTag
     * @return
     */
    public double summaryTotalAmount(String application, String metricsTag) {
        DistributionSummary summary = this.getByKey(application, metricsTag);
        return Objects.nonNull(summary) ? summary.totalAmount() : 0;
    }

    /**
     * 摘要最大值
     *
     * @param application
     * @param metricsTag
     * @return
     */
    public double summaryMax(String application, String metricsTag) {
        DistributionSummary summary = this.getByKey(application, metricsTag);
        return Objects.nonNull(summary) ? summary.max() : 0;
    }

    /**
     * 摘要平均值
     *
     * @param application
     * @param metricsTag
     * @return
     */
    public double summaryMean(String application, String metricsTag) {
        DistributionSummary summary = this.getByKey(application, metricsTag);
        return Objects.nonNull(summary) ? summary.mean() : 0;
    }


    /**
     * 定时器开启
     *
     * @param application
     * @param metricsTag
     * @param watchId
     * @return
     */
    private void timerStart(String application, String metricsTag, String watchId) {
        String name = this.createMetricsKey(application, metricsTag);
        String timerSampleKey = this.createTimerSampleCacheKey(name);
        String sampleUid = this.createTimerUid(timerSampleKey, watchId);
        METER_MAP.computeIfAbsent(sampleUid, t -> Timer.start());
    }

    /**
     * 定时器停止
     *
     * @param watchId
     * @param metricsExecConf
     * @param response
     */
    private void timerStop(String watchId, MetricsExecConfInfoDTO metricsExecConf, @NonNull IResponse response) {
        String name = this.createMetricsKey(metricsExecConf.getApplicationKey(), metricsExecConf.getMetricsKey());
        String timerSampleKey = this.createTimerSampleCacheKey(name);
        String sampleUid = this.createTimerUid(timerSampleKey, watchId);
        try {
            Timer.Sample sample = this.getByKey(sampleUid);

            Tags tags = this.createAllTags(metricsExecConf, null);
            boolean hasError = response instanceof ErrorResponseInfoDTO;
            if (hasError) {
                tags = tags.and(this.createExecptionResponseTags((ErrorResponseInfoDTO) response));
            }
            if (MetricsSourceType.API.equals(metricsExecConf.getSourceType()) && !hasError) {
                tags = tags.and(this.createHttpResponseTags((ApiResponseInfoDTO) response));
            }
            Timer timer = meterRegistry.timer(name, tags);
            sample.stop(timer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            METER_MAP.remove(sampleUid);
        }
    }

    /**
     * 长任务开启
     *
     * @param watchId
     * @param metricsExecConf
     * @param exceptionClass
     */
    private void longTaskTimerStart(String watchId, MetricsExecConfInfoDTO metricsExecConf, String exceptionClass) {
        String name = this.createMetricsKey(metricsExecConf.getApplicationKey(), metricsExecConf.getMetricsKey());
        String timerSampleKey = this.createTimerSampleCacheKey(name);
        String sampleUid = this.createTimerUid(timerSampleKey, watchId);
        METER_MAP.computeIfAbsent(sampleUid, t -> {
            Tags tags = this.createAllTags(metricsExecConf, exceptionClass);
            return meterRegistry.more().longTaskTimer(name, tags).start();
        });
    }

    /**
     * 长任务结束
     *
     * @param application
     * @param metricsTag
     * @param watchId
     */
    private void longTaskTimerStop(String application, String metricsTag, String watchId) {
        String name = this.createMetricsKey(application, metricsTag);
        String timerSampleKey = this.createTimerSampleCacheKey(name);
        String sampleUid = this.createTimerUid(timerSampleKey, watchId);
        try {
            LongTaskTimer.Sample sample = this.getByKey(sampleUid);
            sample.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            METER_MAP.remove(sampleUid);
        }
    }

    /**
     * 创建标签
     *
     * @param metricsExecConf
     * @param exceptionClass
     * @return
     */
    private Tags createAllTags(MetricsExecConfInfoDTO metricsExecConf, String exceptionClass) {
        MetricsSourceType sourceType = metricsExecConf.getSourceType();
        Tags tags = this.createDefTags(metricsExecConf);

        if (MetricsSourceType.API.equals(sourceType)) {
            String uri = ApiUtil.buildUrl(metricsExecConf.getDataSource(), metricsExecConf.getExecPath());
            String method = metricsExecConf.getParams().getOrDefault(METRICS_HTTP_METHOD_NAME, "POST")
                    .toString().toUpperCase();
            tags = tags.and(this.createHttpDefTags(uri, method, metricsExecConf.getParams()));
        } else if (MetricsSourceType.JDBC.equals(sourceType)) {
            tags = tags.and(this.createSqlTags(metricsExecConf.getDataSource(), metricsExecConf.getExecPath()));
        }

        if (StringUtils.isNotBlank(exceptionClass)) {
            tags = tags.and(this.createExceptionTags(exceptionClass));
        }
        return tags;
    }

    /**
     * 创建异常标签
     *
     * @param exceptionClass
     * @return
     */
    private Tags createExceptionTags(String exceptionClass) {
        return Tags.of(
                METRICS_EXCEPTION_NAME,
                getWithDef(exceptionClass)
        );
    }

    /**
     * 创建sql标签
     *
     * @param dataSource
     * @param sql
     * @return
     */
    private Tags createSqlTags(String dataSource, String sql) {
        return Tags.of(
                METRICS_DATASOURCE_NAME,
                getWithDef(dataSource),
                METRICS_SQL_NAME,
                getWithDef(sql)
        );
    }

    /**
     * 创建http标签
     *
     * @param uri
     * @param method
     * @param execParam
     * @return
     */
    private Tags createHttpDefTags(String uri, String method, Map<String, Object> execParam) {
        String routeParam = ApiUtil.buildRouteParamStr(execParam);
        return Tags.of(
                METRICS_HTTP_URL_NAME,
                getWithDef(uri),
                METRICS_HTTP_METHOD_NAME,
                getWithDef(method),
                METRICS_HTTP_ROUTE_PARAM_NAME,
                getWithDef(routeParam)
        );
    }

    /**
     * 创建http响应标签
     *
     * @param apiResponseInfoDTO
     * @return
     */
    private Tags createHttpResponseTags(@NonNull ApiResponseInfoDTO apiResponseInfoDTO) {
        return Tags.of(
                METRICS_HTTP_STATUS_NAME,
                getWithDef(apiResponseInfoDTO.getStatus()),
                METRICS_HTTP_ERR_CODE_NAME,
                getWithDef(apiResponseInfoDTO.getErrorCode()),
                METRICS_HTTP_ERR_MSG_NAME,
                getWithDef(apiResponseInfoDTO.getErrorMsg()),
                METRICS_EXCEPTION_NAME,
                getWithDef(apiResponseInfoDTO.getExceptionClass())
        );
    }

    private Tags createExecptionResponseTags(@NonNull ErrorResponseInfoDTO errorResponseInfoDTO) {
        return Tags.of(
                METRICS_EXCEPTION_NAME,
                getWithDef(errorResponseInfoDTO.getExceptionClass())
        );
    }

    /**
     * 创建默认标签
     *
     * @param metricsExecConf
     * @return
     */
    private Tags createDefTags(MetricsExecConfInfoDTO metricsExecConf) {
        return Tags.of(
                METRICS_DEF_NAME,
                METRICS_DEF_VAL,
                METRICS_SERVICE_NAME,
                getWithDef(metricsExecConf.getApplicationKey()),
                METRICS_SERVICE_DESC_NAME,
                getWithDef(metricsExecConf.getApplicationName()),
                METRICS_HOSPITAL_NAME,
                getWithDef(metricsExecConf.getHospitalCode()),
                METRICS_HOSPITAL_DESC_NAME,
                getWithDef(metricsExecConf.getHospitalName()),
                METRICS_METRICS_DESC_NAME,
                getWithDef(metricsExecConf.getMetricsName()),
                METRICS_TYPE_NAME,
                getWithDef(metricsExecConf.getSourceType().getCode())
        );
    }

    /**
     * 获取值，没有值则填充默认值
     *
     * @param value
     * @return
     */
    private String getWithDef(String value) {
        return StringUtils.isNotBlank(value) ? value : METRICS_DEF_TAG_VALUE;
    }

    /**
     * 创建指标key
     *
     * @param serviceTag 服务标签
     * @param metricsTag 指标标签
     * @return
     */
    private String createMetricsKey(String serviceTag, String metricsTag) {
        return String.join(StringPool.COLON, METRICS_DEF_VAL, getWithDef(serviceTag), getWithDef(metricsTag));
    }


    private String createTimerSampleCacheKey(String name) {
        String timerSampleKey = String.join("#", name, "sample");
        return timerSampleKey;
    }

    private String createTimerUid(String name, String uid) {
        return String.join("@", name, uid);
    }

    private <T> T getByKey(String application, String metricsTag) {
        String name = this.createMetricsKey(application, metricsTag);
        return this.getByKey(name);
    }

    private <T> T getByKey(String key) {
        Object obj = METER_MAP.get(key);
        return Objects.nonNull(obj) ? (T) obj : null;
    }
}
