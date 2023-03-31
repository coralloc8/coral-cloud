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
 * @className BabyGroupCmp
 * @description 婴幼儿组
 * @date 2023/3/24 14:10
 */
@LiteflowComponent(value = "babyGroup", name = "婴幼儿组")
@Slf4j
public class BabyGroupCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        DiagnoseResponse diagnoseResponse = this.getContextBean(DiagnoseResponse.class);
        diagnoseResponse.setPersonGroup("婴幼儿");
    }

    @Override
    public boolean isAccess() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return Objects.nonNull(diagnoseContext.getAge()) &&
                (diagnoseContext.getAge() <= 6 && diagnoseContext.getAge() >= 0);
    }
}
