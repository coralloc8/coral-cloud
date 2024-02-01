package com.coral.test.flink;

import com.coral.base.common.NameStyleUtil;
import com.coral.base.common.StrFormatter;
import com.coral.base.common.json.JsonUtil;
import com.coral.test.flink.dto.desktop.EventRequest;
import com.coral.test.flink.dto.desktop.ProjectInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.BroadcastProcessFunction;
import org.apache.flink.util.Collector;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class DemoApp {

    /**
     * 你要使用的 API	                你需要添加的依赖项
     * DataStream	                    flink-streaming-java
     * DataStream Scala 版	            flink-streaming-scala_2.12
     * Table API	                    flink-table-api-java
     * Table API Scala 版	            flink-table-api-scala_2.12
     * Table API + DataStream	        flink-table-api-java-bridge
     * Table API + DataStream Scala 版	flink-table-api-scala-bridge_2.12
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 准备环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置运行模式
//        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        System.out.println("============================================================");
//        //加载数据源
//        DataStreamSource<String> sourcElements = env.fromElements("java1,scala1,php1,c++1", "java2,scala2,php2", "java3,scala3", "java4");
//        //数据转换
//        DataStream<String> dataStream = sourcElements.flatMap((ele, out) -> {
//            log.info("#####当前ele:{}", ele);
//            Stream.of(ele.split(",")).forEach(out::collect);
//        }, Types.STRING).map(str -> {
//            String st = str.toUpperCase() + ".";
//            return st;
//        });
//        //数据输出
//        dataStream.print("当前值");
//        //执行
//        env.execute();
        System.out.println("============================================================");
//
//        var dataStream = env.socketTextStream("192.168.29.116", 9999)
//                .flatMap((value, out) -> {
//                    Arrays.stream(value.split(" ")).forEach(e ->
//                            out.collect(Tuple2.of(e, 1))
//                    );
//                }, Types.TUPLE(Types.STRING, Types.INT))
//                .keyBy(value -> value.getField(0))
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
//                .sum(1);
//        dataStream.print();
//        env.execute("Window WordCount");

        System.out.println("============================================================");

//        var dataStream = env.socketTextStream("192.168.29.116", 9999)
////                .union(env.fromElements(String.class, "Flink", "Java"))
//                .flatMap((value, out) -> {
//                    Arrays.stream(value.split(" ")).forEach(e ->
//                            WordInfo.of(e).ifPresent(out::collect)
//                    );
//                }, Types.POJO(WordInfo.class))
//                .filter(e -> e.getLength() > 3)
//                .keyBy(WordInfo::getFirst)
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(5)))
////                .sum(LambdaFieldUtil.getFieldName(WordInfo::getLength))
//                .maxBy(LambdaFieldUtil.getFieldName(WordInfo::getLength))
//                .map(e -> StrFormatter.format("首单词:{} 单词:{} 总长度:{}", e.getFirst(), e.getWord(), e.getLength()));
//        dataStream.print();
//        env.execute("Window WordCount");

        System.out.println("============================================================");

//        FlatMapFunction<EventRequest, ProjectInfo> mapFunction = new DatabaseRichFunc();
//
//        var dataStream = env.fromElements(EventRequest.class,
//                        EventRequest.builder()
//                                .project("CDSS-CHECK")
//                                .funcCode("DOCTORADVICE")
//                                .build(),
//                        EventRequest.builder()
//                                .project("YWZ-YWZ")
//                                .funcCode("MEDICAL_RECORD")
//                                .build(),
//                        EventRequest.builder()
//                                .project("DIP-DOCTOR-ZYZK")
//                                .funcCode("IPTQUALITYLIST")
//                                .build(),
//                        EventRequest.builder()
//                                .project("QUARECORD-SXDH")
//                                .funcCode("SXDH_QUALITY")
//                                .build(),
//                        EventRequest.builder()
//                                .project("CDSS-CHECK")
//                                .funcCode("BLOOD_MISS_CHECK")
//                                .build(),
//                        EventRequest.builder()
//                                .project("CDSS-CHECK")
//                                .funcCode("EXAMINEINFO")
//                                .build()
//                )
//                .flatMap(mapFunction, Types.POJO(ProjectInfo.class))
//                .keyBy(ProjectInfo::getProject)
////                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
//                ;
//
//        dataStream.print();
//        env.execute("Window WordCount");

        System.out.println("============================================================");

        MapStateDescriptor<Integer, Tuple2<Integer, String>> userInfoState = new MapStateDescriptor<>("studentInfo", Types.INT, Types.TUPLE(Types.INT, Types.STRING));
        //学生 广播
        BroadcastStream<Tuple2<Integer, String>> studentDS = env.fromElements(
                Tuple2.of(1, "张三"),
                Tuple2.of(2, "李四"),
                Tuple2.of(3, "王五"),
                Tuple2.of(4, "赵六")
        ).broadcast(userInfoState);
        //分数
        DataStreamSource<Tuple3<Integer, String, Integer>> scoreDS = env.fromElements(
                Tuple3.of(1, "语文", 50),
                Tuple3.of(1, "数学", 70),
                Tuple3.of(1, "英语", 86),
                Tuple3.of(1, "物理", 60),

                Tuple3.of(2, "语文", 60),
                Tuple3.of(2, "数学", 120),
                Tuple3.of(2, "英语", 112),
                Tuple3.of(2, "物理", 85),

                Tuple3.of(3, "语文", 110),
                Tuple3.of(3, "数学", 90),
                Tuple3.of(3, "英语", 120),
                Tuple3.of(3, "物理", 52),

                Tuple3.of(4, "语文", 90),
                Tuple3.of(4, "数学", 85),
                Tuple3.of(4, "英语", 45),
                Tuple3.of(4, "物理", 60)
        );
        scoreDS.connect(studentDS)
                .process(new MyBroadcastProcessFunction(userInfoState))
                .print();
        env.execute("Flink broadcast");


    }


    @Slf4j
    static class MyBroadcastProcessFunction extends BroadcastProcessFunction<Tuple3<Integer, String, Integer>, Tuple2<Integer, String>, Tuple3<Integer, String, Integer>> {

        private final MapStateDescriptor<Integer, Tuple2<Integer, String>> userInfoState;


        public MyBroadcastProcessFunction(MapStateDescriptor<Integer, Tuple2<Integer, String>> userInfoState) {
            this.userInfoState = userInfoState;
        }

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);

        }

        @Override
        public void processBroadcastElement(Tuple2<Integer, String> value, Context ctx, Collector<Tuple3<Integer, String, Integer>> out) throws Exception {
            var studentBroadcast = ctx.getBroadcastState(userInfoState);
            System.out.println("studentBroadcast插值,ID:" + value.f0);
            studentBroadcast.put(value.f0, value);
        }

        @Override
        public void processElement(Tuple3<Integer, String, Integer> value, ReadOnlyContext ctx, Collector<Tuple3<Integer, String, Integer>> out) throws Exception {
            var studentBroadcast = ctx.getBroadcastState(userInfoState);
            if (Objects.isNull(studentBroadcast)) {
                System.out.println("广播变量为空:" + userInfoState);
                return;
            }
            Tuple2<Integer, String> student = studentBroadcast.get(value.f0);
            if (Objects.nonNull(student)) {
                System.out.println(StrFormatter.format(">>>>>Student[{}(ID:{})]匹配上,输出科目[{}]成绩.", student.f1, student.f0, value.f1));
                out.collect(value);
                return;
            }
            System.out.println("student为空,ID:" + value.f0);
        }


    }

    @Slf4j
    static class DatabaseRichFunc extends RichFlatMapFunction<EventRequest, ProjectInfo> {
        private Connection connection = null;

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            connection = DriverManager.getConnection("jdbc:mysql://192.168.29.66:3306/zhyx_base_desktop?charset=utf8&serverTimezone=GMT%2B8&autoReconnect=true&failOverReadOnly=false", "zhyx", "zhyx");
        }

        @Override
        public void close() throws Exception {
            super.close();
            if (Objects.nonNull(connection)) {
                connection.close();
            }
        }

        @Override
        public void flatMap(EventRequest value, Collector<ProjectInfo> out) throws Exception {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                stmt = connection.prepareStatement("select * from project_config where status = ? and project = ?");
                stmt.setQueryTimeout(30 * 1000);
                stmt.setInt(1, 1);
                stmt.setString(2, value.getProject());
                //结果集
                rs = stmt.executeQuery();
                ResultSetMetaData metaData = rs.getMetaData();
                while (rs.next()) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    for (int i = 0; i < metaData.getColumnCount(); i++) {
                        String columnLabel = metaData.getColumnLabel(i + 1);
                        Object columnValue = rs.getObject(columnLabel);
                        columnLabel = NameStyleUtil.lineToHump(columnLabel);
                        map.put(columnLabel, columnValue);
                    }
                    ProjectInfo projectInfo = JsonUtil.toPojo(map, ProjectInfo.class);
                    out.collect(projectInfo);
                }
            } finally {
                if (Objects.nonNull(rs)) {
                    rs.close();
                }
                if (Objects.nonNull(stmt)) {
                    stmt.close();
                }
            }
        }
    }


}
