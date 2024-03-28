package com.coral.test.flink.fraud_detection;

import com.coral.test.flink.util.DbUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * AlertSink
 *
 * @author huss
 * @date 2024/3/5 14:45
 * @packageName com.coral.test.flink.fraud_detection
 * @className AlertSink
 */
@Slf4j
public class AlertSink extends RichSinkFunction<Alert> {
    private static final String JDBC_URL = "jdbc:mysql://192.168.29.66:3306/testdb?charset=utf8&serverTimezone=GMT%2B8&autoReconnect=true&failOverReadOnly=false";
    private static final String INSERT_SQL = "INSERT INTO `testdb`.`t_alert` (`account_id`, `message`) VALUES ('#{account_id}', '#{message}');";
    private static final List<Alert> ALERTS = new CopyOnWriteArrayList<>();
    private Connection connection;


    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        connection = DbUtil.open(JDBC_URL, "zhyx", "zhyx");
    }

    @Override
    public void invoke(Alert value, Context context) throws Exception {
        if (ALERTS.size() > 1000) {
            long count = DbUtil.batchInsert(connection, "t_alert", INSERT_SQL, ALERTS);
            if (count > 0) {
                log.debug(">>>>本批次数据插入完成(测试不考虑部分插入成功情况)...");
                ALERTS.clear();
                return;
            }
        }
        ALERTS.add(value);
    }

    @Override
    public void close() throws Exception {
        super.close();
        DbUtil.close(connection);
    }
}
