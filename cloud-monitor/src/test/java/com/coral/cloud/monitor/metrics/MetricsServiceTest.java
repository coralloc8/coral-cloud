package com.coral.cloud.monitor.metrics;


import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.monitor.MonitorTestApplication;
import com.coral.cloud.monitor.common.enums.MetricsSourceType;
import com.coral.cloud.monitor.common.enums.MetricsType;
import com.coral.cloud.monitor.dto.job.MetricsExecConfInfoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coral.cloud.monitor.common.constants.MetricsKey.*;


/**
 * @author huss
 * @version 1.0
 * @className MetricsServiceTest
 * @description 监控测试
 * @date 2023/4/14 10:57
 */
public class MetricsServiceTest extends MonitorTestApplication {

    private List<MetricsExecConfInfoDTO> configs;

    @Test
    @DisplayName("指标收集测试")
    public void test1() {
        System.out.println(JsonUtil.toJson(configs));
        configs.forEach(e -> {
            MetricsServiceFactory.findMessageHandler(e.getSourceType())
                    .ifPresent(service -> {
                        service.run(e);
                    });
        });
    }


    @BeforeEach
    public void init() {
        configs = Arrays.asList(
                //jdbc gauge
                MetricsExecConfInfoDTO.builder()
                        .applicationKey("alpha")
                        .applicationName("预问诊")
                        .hospitalCode("test")
                        .hospitalName("test名称")
                        .metricsKey("his_register_yygh_today_total")
                        .metricsName("当天挂号数据量")
                        .sourceType(MetricsSourceType.JDBC)
                        .dataSource("jdbc:mysql://192.168.29.188:3306/dr_alpha?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useOldAliasMetadataBehavior=true&allowMultiQueries=true&rewriteBatchedStatements=true&autoReconnect=true&failOverReadOnly=false")
                        .metricsType(MetricsType.GAUGE)
                        .execPath("select count(0) from his_register_yygh where create_time >= CONCAT(DATE_FORMAT(NOW(),'%Y-%m-%d'),' 00:00:00') ")
                        .extraConfigs(
                                MapData.create()
                                        .put("driverClassName", "com.mysql.cj.jdbc.Driver")
                                        .put("username", "zhyx")
                                        .put("password", "zhyx")
                                        .get()
                        )

                        .build(),
                //api timer
                MetricsExecConfInfoDTO.builder()
                        .applicationKey("alpha")
                        .applicationName("预问诊")
                        .hospitalCode("test")
                        .hospitalName("test名称")
                        .metricsKey("aplha_base_api_duration")
                        .metricsName("接口耗时")
                        .sourceType(MetricsSourceType.API)
                        .dataSource("http://192.168.29.185:19092")
                        .metricsType(MetricsType.TIMER)
                        .execPath("/user/api/feedback/items/query")
                        .params(
                                MapData.create()
                                        .put(EXEC_PARAM_API_METHOD, "POST")
                                        .put(EXEC_PARAM_API_APPLICATION_TYPE, "FORM")
                                        .put(EXEC_PARAM_API_ERR_CODE, "code")
                                        .put(EXEC_PARAM_API_ERR_MSG, "msg")
                                        .put(EXEC_PARAM_API_DATA_CODE, "data")
                                        .get()
                        )
                        .build(),
                //api timer
                MetricsExecConfInfoDTO.builder()
                        .applicationKey("survey")
                        .applicationName("表单")
                        .hospitalCode("test")
                        .hospitalName("test名称")
                        .metricsKey("survey_base_api_duration")
                        .metricsName("接口耗时")
                        .sourceType(MetricsSourceType.API)
                        .dataSource("http://192.168.29.186:16100")
                        .metricsType(MetricsType.TIMER)
                        .execPath("/app/layouts/{formNo}")
                        .params(
                                MapData.create()
                                        .put(EXEC_PARAM_API_METHOD, "GET")
                                        .put(EXEC_PARAM_API_APPLICATION_TYPE, "FORM")
                                        .put(EXEC_PARAM_API_ROUTE_PARAMS,
                                                MapData.create()
                                                        .put("formNo", "1")
                                                        .get()
                                        )
                                        .put(EXEC_PARAM_API_ERR_CODE, "code")
                                        .put(EXEC_PARAM_API_ERR_MSG, "message")
                                        .put(EXEC_PARAM_API_DATA_CODE, "data")
                                        .get()
                        )
                        .build(),

                // api counter
                MetricsExecConfInfoDTO.builder()
                        .applicationKey("survey")
                        .applicationName("表单")
                        .hospitalCode("test")
                        .hospitalName("test名称")
                        .metricsKey("survey_form_query_total")
                        .metricsName("接口请求次数")
                        .sourceType(MetricsSourceType.API)
                        .dataSource("http://192.168.29.186:16100")
                        .metricsType(MetricsType.COUNTER)
                        .execPath("/app/layouts/{formNo}")
                        .params(
                                MapData.create()
                                        .put(EXEC_PARAM_API_METHOD, "GET")
                                        .put(EXEC_PARAM_API_APPLICATION_TYPE, "FORM")
                                        .put(EXEC_PARAM_API_ROUTE_PARAMS,
                                                MapData.create()
                                                        .put("formNo", "100")
                                                        .get()
                                        )
                                        .put(EXEC_PARAM_API_ERR_CODE, "code")
                                        .put(EXEC_PARAM_API_ERR_MSG, "message")
                                        .put(EXEC_PARAM_API_DATA_CODE, "data.total")
                                        .get()
                        )
                        .build()
        );
    }


    static class MapData {
        private Map<String, Object> data;

        private MapData() {
            this.data = new HashMap<>(32);
        }


        public static MapData create() {
            return new MapData();
        }


        public Map<String, Object> get() {
            return this.data;
        }

        public MapData put(String key, Object value) {
            this.data.put(key, value);
            return this;
        }
    }

}
