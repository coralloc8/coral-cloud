package com.coral.test.liteflow.cmp.symptom;

import com.coral.base.common.CollectionUtil;
import com.coral.test.liteflow.slot.DiagnoseContext;
import com.coral.test.liteflow.slot.DiagnoseResponse;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

/**
 * @author huss
 * @version 1.0
 * @className SymptomACmp
 * @description 症状组件
 * @date 2023/3/24 15:02
 */
@Slf4j
@LiteflowComponent(value = "symptomA", name = "症状A")
public class SymptomACmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        DiagnoseResponse diagnoseResponse = this.getContextBean(DiagnoseResponse.class);

        Thread.sleep(3000);

        int maxSize = diagnoseContext.getSymptoms().size();

        Random random = new Random();
        int index = random.nextInt(maxSize);

        String symptom = diagnoseContext.getSymptoms().get(index);

        System.out.println(String.format("SymptomACmp 执行时间3秒。 计算出的症状为：%s", symptom));
        diagnoseResponse.setSymptoms(Arrays.asList(symptom));
    }

    @Override
    public boolean isAccess() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return CollectionUtil.isNotBlank(diagnoseContext.getSymptoms());
    }
}
