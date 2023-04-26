package com.coral.cloud.user.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusMeterRegistry;
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
