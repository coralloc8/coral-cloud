package com.coral.test.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

@Slf4j
public class TabApi2App {
    public static void main(String[] args) throws Exception {
        // 准备环境
        var env = StreamExecutionEnvironment.getExecutionEnvironment();
        var tableEnv = StreamTableEnvironment.create(env);
        log.info("#####tableEnv:{}", tableEnv);

        /**
         * 事件时间 可以基于Watermark处理乱序和延迟问题 适用于要求结果准确性的场景
         * 摄入时间 适用于介于事件时间和处理时间的折衷场景
         * 处理时间 适用于实时性要求较高的场景
         */
        // "id","event_log_id","event_id","project","func_code","send_time","receive_time","response_data","error_code","error_msg","status"
        String inputSql = """
                CREATE TABLE ipt_event_log (
                	id BIGINT,
                	event_log_id string,
                	event_id string,
                	project string,
                	func_code string,
                	send_time TIMESTAMP ( 3 ),
                	receive_time TIMESTAMP ( 3 ),
                	response_data string,
                	error_code string,
                	error_msg string,
                	STATUS INT,
                	watermark FOR send_time AS send_time - INTERVAL '5' SECOND,
                	processing_time AS proctime (),
                	PRIMARY KEY ( id ) NOT ENFORCED
                ) WITH (
                	'connector' = 'jdbc',
                	'url' = 'jdbc:mysql://192.168.29.66:3306/zhyx_base_desktop?charset=utf8&serverTimezone=GMT%2B8&autoReconnect=true&failOverReadOnly=false',
                	'driver' = 'com.mysql.cj.jdbc.Driver',
                	'table-name' = 'event_log',
                	'username' = 'zhyx',
                    'password' = 'zhyx'
                )
                """;
        tableEnv.executeSql(inputSql);

        /**
         * 滚动窗口
         * 基于时间字段 send_time，对表 ipt_event_log 中的数据开了大小为 10分钟 的滚动窗口。窗口会将表中的每一行数据，按照它们 send_time 的值分配到一个指定的窗口中。
         *
         * 应用场景：统计每小时的pv，uv。
         */
        String tumbleSql = """
                SELECT
                	window_start,
                	window_end,
                	project,
                	func_code,
                	count( 1 ) AS event_times
                FROM
                	TABLE (
                	    tumble ( TABLE ipt_event_log, descriptor ( send_time ), INTERVAL '10' MINUTE )
                	)
                GROUP BY
                	window_start,
                	window_end,
                	project,
                	func_code;
                """;
        /**
         * 滑动窗口
         * 基于时间属性 send_time，在表 ipt_event_log 上创建了大小为 10分钟 的滑动窗口，每 30秒 滑动一次。
         * 需要注意的是，紧跟在时间属性字段后面的第三个参数是步长（slide），第四个参数才是窗口大小（size）
         *
         * 应用场景：每5分钟统计一下最近一小时的pv，uv。
         */
        String hopSql = """
                SELECT
                	window_start,
                	window_end,
                	project,
                	func_code,
                	count( 1 ) AS event_times
                FROM
                	TABLE (
                	    hop ( TABLE ipt_event_log, descriptor ( send_time ), INTERVAL '30' SECOND, INTERVAL '10' MINUTE )
                	)
                GROUP BY
                	window_start,
                	window_end,
                	project,
                	func_code;
                """;
        /**
         * 累计窗口
         * 基于时间属性 send_time，在表 ipt_event_log 上定义了一个统计周期为 1 天、累积步长为 1 分钟的累积窗口。
         * 注意第三个参数为步长 step ，第四个参数则是最大窗口长度。
         *
         * 应用场景：每小时统计一次当天的pv，uv。（如果用 1 天的滚动窗口，那需要到每天 24 点才会计算一次，输出频率太低；如果用滑动窗口，计算频率可以更高，但统计的就变成了“过去 24 小时的 PV”。
         *      所以我们真正希望的是，还是按照自然日统计每天的PV，不过需要每隔 1 小时就输出一次当天到目前为止的 PV 值）。
         */
        String cumulateSql = """
                SELECT
                	window_start,
                	window_end,
                	project,
                	func_code,
                	count( 1 ) AS event_times
                FROM
                	TABLE (
                	    cumulate ( TABLE ipt_event_log, descriptor ( send_time ), INTERVAL '1' MINUTE, INTERVAL '1' DAY )
                	)
                GROUP BY
                	window_start,
                	window_end,
                	project,
                	func_code;
                """;

//        /**
//         * 会话窗口
//         *
//         * 特点：
//         * 会话窗口不重叠，没有固定的开始和结束时间
//         * 与翻滚窗口和滑动窗口相反, 当会话窗口在一段时间内没有接收到元素时会关闭会话窗口。
//         * 后续的元素将会被分配给新的会话窗口
//         *
//         */
//        String sessionSql = """
//                SELECT
//                	window_start,
//                	window_end,
//                	project,
//                	func_code,
//                	count( 1 ) AS event_times
//                FROM
//                	TABLE (
//                	    session ( TABLE ipt_event_log, descriptor ( send_time ), INTERVAL '1' MINUTE)
//                	)
//                GROUP BY
//                	window_start,
//                	window_end,
//                	project,
//                	func_code;
//                """;


        String sql = "select * from ipt_event_log limit 10";

        var resTab = tableEnv.sqlQuery(sql);

        var resultStream = tableEnv.toDataStream(resTab);
        resultStream.print("结果");
        env.execute();
    }


}
