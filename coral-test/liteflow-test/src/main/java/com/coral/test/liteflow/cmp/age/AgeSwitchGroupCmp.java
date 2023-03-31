package com.coral.test.liteflow.cmp.age;

import com.coral.test.liteflow.slot.DiagnoseContext;
import com.coral.test.liteflow.slot.DiagnoseResponse;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeSwitchComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className AgeSwitchGroupCmp
 * @description 年龄选择器分组
 * @date 2023/3/24 14:10
 */
@LiteflowComponent(value = "ageSwitchGroup", name = "年龄选择器分组")
@Slf4j
public class AgeSwitchGroupCmp extends NodeSwitchComponent {

    @Override
    public String processSwitch() throws Exception {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);

        if (diagnoseContext.getAge() >= 0 && diagnoseContext.getAge() <= 6) {
            return "babyGroup";
        } else if (diagnoseContext.getAge() >= 7 && diagnoseContext.getAge() <= 12) {
            return "childrenGroup";
        } else if (diagnoseContext.getAge() >= 13 && diagnoseContext.getAge() <= 17) {
            return "juniorGroup";
        } else if (diagnoseContext.getAge() >= 18 && diagnoseContext.getAge() <= 45) {
            return "youthGroup";
        } else {
            return "defAgeGroup";
        }
    }

    @Override
    public boolean isAccess() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return Objects.nonNull(diagnoseContext.getAge());
    }
}
