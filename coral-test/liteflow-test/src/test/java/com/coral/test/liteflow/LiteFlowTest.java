package com.coral.test.liteflow;

import com.coral.test.liteflow.slot.DiagnoseContext;
import com.coral.test.liteflow.slot.DiagnoseResponse;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huss
 * @version 1.0
 * @className LiteFlowTest
 * @description 测试
 * @date 2023/3/24 13:21
 */
@SpringBootTest
public class LiteFlowTest {

    @Autowired
    private FlowExecutor flowExecutor;

    private DiagnoseContext diagnoseContext;

    private DiagnoseResponse diagnoseResponse;


    @BeforeEach
    public void init() {
        diagnoseContext = DiagnoseContext.builder()
                .patientId("1327816")
                .inpatientNo("674396")
                .inpatientSerialNo("1327816")
                .patientName("杨莹")
                .sex("女")
                .age(25)
                .mainSuits(Arrays.asList("感冒3天"))
                .symptoms(Arrays.asList("咳嗽", "发烧", "头痛", "四肢乏力", "恶心", "吃不下饭"))
                .drugs(Arrays.asList("京都念慈菴"))
                .build();

        diagnoseResponse = new DiagnoseResponse();
        diagnoseResponse.setPatientId(diagnoseContext.getPatientId());
    }


    @DisplayName("测试1")
    @Test
    public void test1() {
        LiteflowResponse response = flowExecutor.execute2Resp("mainChain", null, diagnoseContext, diagnoseResponse);
        System.out.println("\n\n");
        System.out.println("###################################################################################");
        System.out.println("是否成功：" + response.isSuccess());
        if (!response.isSuccess()) {
            System.out.println("异常为：" + response.getCause());
        }
        Map<String, CmpStep> stepMap = response.getExecuteSteps();

        System.out.println("步骤信息为：" + stepMap);

        stepMap.entrySet().forEach(e -> System.out.println(e.getValue().getNodeName()));

        diagnoseResponse = response.getContextBean(DiagnoseResponse.class);

        System.out.println("诊断响应：" + diagnoseResponse);

    }

    public void test3() throws IOException {
        Files.readAllLines(Paths.get("C:\\Users\\Administrator\\Desktop\\test.txt")).stream().forEach(e -> {
            String pattern = "'.*\\{.*?\\ }.*'";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(e);
            String last = e;

            try {
                if (m.find()) {
                    String str = m.group();
                    int size = m.group().length();

                    int first = e.indexOf("IS");

                    last = e.substring(0, first + 2) + " " + str.replaceAll(" ", "") + e.substring(first + 3 + size);
                    System.out.println(last);
                    last += "\n";
                    Files.write(Paths.get("C:\\Users\\Administrator\\Desktop\\test_new.txt"), last.getBytes(), StandardOpenOption.APPEND);
                } else {
                    last += "\n";
                    Files.write(Paths.get("C:\\Users\\Administrator\\Desktop\\test_new.txt"), last.getBytes(), StandardOpenOption.APPEND);

                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        });
    }
}
