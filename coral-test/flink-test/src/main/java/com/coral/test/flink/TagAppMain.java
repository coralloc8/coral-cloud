package com.coral.test.flink;

import com.coral.base.common.CollectionUtil;
import com.coral.test.flink.tag.R000022Tag;
import com.coral.test.flink.tag.R000022_2Tag;
import com.coral.test.flink.tag.dto.MislOpnDTO;
import com.coral.test.flink.tag.dto.PatientInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TagAppMain {
    // 将数据广播下去
    private final static MapStateDescriptor<String, List<MislOpnDTO>> GLOBAL_DATA = new MapStateDescriptor<>("GLOBAL_DATA",
            Types.STRING, Types.LIST(Types.POJO(MislOpnDTO.class)));

    public static void main(String[] args) throws Exception {
        // 准备环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 患者信息
        PatientInfoDTO patientInfo = new PatientInfoDTO();
        patientInfo.setIptSn("0001064463003X");

        // 广播数据
        var globalDataStream = env.fromElements(patientInfo)
                .flatMap(new GlobalDataRichFlatMapFunction())
                .broadcast(GLOBAL_DATA);
        final KeyedBroadcastProcessFunction func = new PatientKeydBroadcastProcessFunction();
        var dataStream = env.fromElements(patientInfo)
                .keyBy(PatientInfoDTO::getIptSn)
                .connect(globalDataStream)
                .process(func)
                .print();
        R000022Tag r000022Tag = new R000022Tag(env, patientInfo);
        r000022Tag.execute();

        R000022_2Tag r000022_2Tag = new R000022_2Tag(env, patientInfo);
        r000022_2Tag.execute();

    }

    static class PatientKeydBroadcastProcessFunction extends KeyedBroadcastProcessFunction<String, PatientInfoDTO, MislOpnDTO, PatientInfoDTO> {

        // 缓存数据
        protected ListState<PatientInfoDTO> payloads;

        // 定时器
        protected ValueState<Boolean> setTimer;

        @Override
        public void onTimer(long timestamp, KeyedBroadcastProcessFunction<String, PatientInfoDTO, MislOpnDTO, PatientInfoDTO>.OnTimerContext ctx, Collector<PatientInfoDTO> out) throws Exception {
            super.onTimer(timestamp, ctx, out);
            payloads.get().forEach(out::collect);
            // 输出后把缓存清空防止重复输出.
            payloads.clear();
            // 执行完任务删除定时器
            ctx.timerService().deleteProcessingTimeTimer(timestamp);
            // 同时更新定时器的状态
            setTimer.update(false);

        }

        @Override
        public void processElement(PatientInfoDTO value, KeyedBroadcastProcessFunction<String, PatientInfoDTO, MislOpnDTO, PatientInfoDTO>.ReadOnlyContext ctx, Collector<PatientInfoDTO> out) throws Exception {
            var curState = ctx.getBroadcastState(GLOBAL_DATA);
            var className = value.getClass().getName();
            List<MislOpnDTO> curData = curState.get(className);

            if (CollectionUtil.isNotBlank(curData)) {
                curData = curData.stream().filter(e -> e.getIptSn().equalsIgnoreCase(value.getIptSn())).collect(Collectors.toList());
            }
            if (CollectionUtil.isNotBlank(curData)) {
                if (setTimer.value()) {
                    payloads.get().forEach(out::collect);
                    payloads.clear();
                }
                value.setOpns(curData);
                out.collect(value);
                setTimer.update(false);
            } else {
                payloads.add(value);
                if (setTimer.value()) {
                    long fireTime = ctx.timerService().currentProcessingTime() + 30000;
                    ctx.timerService().registerEventTimeTimer(fireTime);
                    setTimer.update(true);
                }
            }

        }

        @Override
        public void processBroadcastElement(MislOpnDTO value, KeyedBroadcastProcessFunction<String, PatientInfoDTO, MislOpnDTO, PatientInfoDTO>.Context ctx, Collector<PatientInfoDTO> out) throws Exception {
            var curState = ctx.getBroadcastState(GLOBAL_DATA);
            var className = value.getClass().getName();
            List<MislOpnDTO> curData = curState.get(className);
            if (Objects.isNull(curData)) {
                curData = new ArrayList<>();
            }
            curData.add(value);
            curState.put(className, curData);
        }


    }

    @Slf4j
    static class GlobalDataRichFlatMapFunction extends RichFlatMapFunction<PatientInfoDTO, Object> {
        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
        }

        @Override
        public void flatMap(PatientInfoDTO value, Collector<Object> out) throws Exception {
            log.info(">>>模拟获取手术记录 开始");
            Thread.sleep(1000);
            MislOpnDTO.init().forEach(out::collect);
            Thread.sleep(1500);
            MislOpnDTO.init().forEach(out::collect);
            log.info(">>>模拟获取手术记录 结束");
        }
    }

}
