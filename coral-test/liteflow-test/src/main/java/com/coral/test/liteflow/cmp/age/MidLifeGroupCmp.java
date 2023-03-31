package com.coral.test.liteflow.cmp.age;

import com.coral.test.liteflow.slot.DiagnoseContext;
import com.coral.test.liteflow.slot.DiagnoseResponse;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className MidLifeGroupCmp
 * @description 中年组
 * @date 2023/3/24 14:10
 */
@LiteflowComponent(value = "midLifeGroup", name = "中年组")
@Slf4j
public class MidLifeGroupCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        DiagnoseResponse diagnoseResponse = this.getContextBean(DiagnoseResponse.class);
        diagnoseResponse.setPersonGroup("中年");
    }

    @Override
    public boolean isAccess() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return Objects.nonNull(diagnoseContext.getAge()) &&
                (diagnoseContext.getAge() <= 69 && diagnoseContext.getAge() >= 46);
    }
}
