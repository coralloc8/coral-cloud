package com.coral.test.flink;

import com.coral.test.flink.fraud_detection.AlertSink;
import com.coral.test.flink.fraud_detection.FraudDetector;
import com.coral.test.flink.fraud_detection.Transaction;
import com.coral.test.flink.fraud_detection.TransactionSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * 欺诈检测
 *
 * @author huss
 * @date 2024/3/5 13:37
 * @packageName com.coral.test.flink.tag
 * @className FraudDetectionJobMain
 */
public class FraudDetectionJobMain {

    public static void main(String[] args) throws Exception {
        // stream环境
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var transactions = env.addSource(new TransactionSource()).name("transactions");
        var alerts = transactions.keyBy(Transaction::getAccountId).process(new FraudDetector()).name("fraud-detector");
        // 输出
        alerts.addSink(new AlertSink()).name("send-alerts");

        env.execute("Fraud Detection Job");
    }
}
