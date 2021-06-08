package com.coral.test.r.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className RserveUtilTest
 * @description rserve测试
 * @date 2021/6/7 13:36
 */
@Slf4j
public class RserveUtilTest {


    @Test
    @DisplayName("CDSS_stat测试")
    public void test() {
        String host = "192.168.29.190";
        int port = 6311;
        String fileName = "/home/zhyx/cdss_java.r";
        String funcName = "CDSS_stat";

        Map<String, Object> params = new HashMap<>(16);
        params.put("dept_name", "妇科");
        params.put("time_select", "最近一个月");
        params.put("data_name", "（警告）多正常值检验项目异常结果警示");
        params.put("doctor_name", "赖紫玲");
        params.put("message", "查看检验报告");

        //CDSS_stat("妇科","赖紫玲","查看检验报告","（警告）多正常值检验项目异常结果警示","最近一个月")
        String result = RserveUtil.callRserve(host, port, fileName, funcName, params);
        log.info(">>>>>re:{}", result);
    }
}
