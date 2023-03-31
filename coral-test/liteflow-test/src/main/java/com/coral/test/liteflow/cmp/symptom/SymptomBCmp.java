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
 * @className SymptomBCmp
 * @description 症状组件
 * @date 2023/3/24 15:02
 */
@Slf4j
@LiteflowComponent(value = "symptomB", name = "症状B")
public class SymptomBCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        DiagnoseResponse diagnoseResponse = this.getContextBean(DiagnoseResponse.class);

        Thread.sleep(5000);

        int maxSize = diagnoseContext.getSymptoms().size();

        Random random = new Random();
        int index = random.nextInt(maxSize);

        String symptom = diagnoseContext.getSymptoms().get(index);

        System.out.println(String.format("SymptomBCmp计算时间5秒。 计算出症状为：%s", symptom));
        diagnoseResponse.setSymptoms(Arrays.asList(symptom));

    }

    @Override
    public boolean isAccess() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return CollectionUtil.isNotBlank(diagnoseContext.getSymptoms());
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            System.out.println(random.nextInt(5));
        }

    }
}
