package com.coral.test.flink.fraud_detection;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * FraudDetector
 *
 * @author huss
 * @date 2024/3/5 14:38
 * @packageName com.coral.test.flink.fraud_detection
 * @className FraudDetector
 */
public class FraudDetector extends KeyedProcessFunction<String, Transaction, Alert> {
    @Override
    public void processElement(Transaction value, Context ctx, Collector<Alert> out) throws Exception {
        Alert alert = new Alert(value.getAccountId(), "触发交易金额报警");
        out.collect(alert);
    }
}
