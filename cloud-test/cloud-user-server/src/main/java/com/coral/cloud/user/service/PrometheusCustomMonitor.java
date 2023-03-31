package com.coral.cloud.user.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author huss
 * @version 1.0
 * @className PrometheusCustomMonitor
 * @description todo
 * @date 2022/8/2 18:18
 */
@Component
public class PrometheusCustomMonitor {

    /**
     *
     * scrape_configs:
     *   - job_name: 'federate'
     *     scrape_interval: 15s
     *     honor_labels: true
     *     metrics_path: '/federate'
     *     params:
     *       'match[]':
     *         - '{job="prometheus"}'
     *         - '{__name__=~"job:.*"}'
     *         - '{__name__=~"node.*"}'
     *     static_configs:
     *       - targets:
     *         - '192.168.77.11:9090'
     *         - '192.168.77.12:9090'
     *
     *为了有效的减少不必要的时间序列，通过params参数可以用于指定只获取某些时间序列的样本数据，例如
     *
     * "http://192.168.77.11:9090/federate?match[]={job%3D"prometheus"}&match[]={__name__%3D~"job%3A.*"}&match[]={__name__%3D~"node.*"}"
     *
     * 通过URL中的match[]参数指定我们可以指定需要获取的时间序列。match[]参数必须是一个瞬时向量选择器，例如up或者{job=“api-server”}。配置多个match[]参数，用于获取多组时间序列的监控数据。
     *
     * horbor_labels配置true可以确保当采集到的监控指标冲突时，能够自动忽略冲突的监控数据。如果为false时，prometheus会自动将冲突的标签替换为”exported_“的形式。
     *
     */

    private AtomicInteger monitorGaugeTotal;

    private Counter monitorCounter;

    private DistributionSummary monitorSummary;

    @PostConstruct
    public void init() {
        monitorGaugeTotal = Metrics.gauge("monitor_gauge_total", new AtomicInteger(0));
        monitorCounter = Metrics.counter("monitor_counter");
        monitorSummary = Metrics.summary("monitor_summary");
    }

    public void addMonitorGaugeTotal(Integer number) {
        monitorGaugeTotal.set(number);
    }

    public double getMonitorGaugeTotal() {
        return monitorGaugeTotal.get();
    }

    public void addCounter() {
        monitorCounter.increment();
    }

    public double getCounter() {
        return monitorCounter.count();
    }

    public void addSummary(double amount) {
        monitorSummary.record(amount);
    }

    public double getSummaryCount() {
        return monitorSummary.count();
    }

    public double getSummaryMean() {
        return monitorSummary.mean();
    }

    public double getSummaryTotalAmount() {
        return monitorSummary.totalAmount();
    }

    public double getSummaryMax() {
        return monitorSummary.max();
    }


}
