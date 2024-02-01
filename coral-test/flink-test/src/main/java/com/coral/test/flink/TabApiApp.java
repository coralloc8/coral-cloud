package com.coral.test.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TabApiApp {
    public static void main(String[] args) throws Exception {
        // 准备环境
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var tableEnv = StreamTableEnvironment.create(env);
        log.info("#####tableEnv:{}", tableEnv);

        var dataStreamSource = env.fromElements("Alice", "Bob", "John");

        var eventTable = tableEnv.fromDataStream(dataStreamSource);
        eventTable.printSchema();

        var resTable = tableEnv.sqlQuery("select UPPER(f0) from " + eventTable);
        resTable.printSchema();

        // 将Table Api支持的流数据 转换成 DataStream  Api支持的流数据
        var resultStream = tableEnv.toDataStream(resTable);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "hu");
        map.put("id", 1L);
        Tuple3<String, Integer, Map<String, Object>> tuple3 = Tuple3.of("Fred", 35, map);

        String person = tuple3.f0;
        Integer age = tuple3.f1;
        Map<String, Object> properties = tuple3.f2;


        resultStream.print("结果");
        env.execute();

    }

}
