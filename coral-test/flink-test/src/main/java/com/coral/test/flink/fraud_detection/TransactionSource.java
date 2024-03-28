package com.coral.test.flink.fraud_detection;

import com.coral.test.flink.util.DbUtil;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.sql.Connection;

/**
 * TransactionSource
 * <p>
 * SourceFunction：非并行数据源(并行度=1)
 * RichSourceFunction：多功能非并行数据源(并行度=1)
 * ParallelSourceFunction: 并行数据源(并行度>=1)
 * RichParallelSourceFunction: 多功能并行数据源(并行度>=1)
 *
 * @author huss
 * @date 2024/3/5 13:43
 * @packageName com.coral.test.flink.fraud_detection
 * @className TransactionSource
 */
public class TransactionSource extends RichParallelSourceFunction<Transaction> {

    private static final String JDBC_URL = "jdbc:mysql://192.168.29.66:3306/testdb?charset=utf8&serverTimezone=GMT%2B8&autoReconnect=true&failOverReadOnly=false";
    private Connection connection;

    private volatile boolean isRunning = true;

    /**
     * 执行一次，相当于初始化
     *
     * @param parameters The configuration containing the parameters attached to the contract.
     * @throws Exception
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = DbUtil.open(JDBC_URL, "zhyx", "zhyx");
    }

    @Override
    public void run(SourceContext<Transaction> ctx) throws Exception {
        while (isRunning) {
            DbUtil.query(connection, Transaction.class, "select * from t_transaction").forEach(ctx::collect);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    @Override
    public void close() throws Exception {
        super.close();
        DbUtil.close(connection);
    }
}
