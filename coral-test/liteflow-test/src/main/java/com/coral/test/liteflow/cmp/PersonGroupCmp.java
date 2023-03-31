package com.coral.test.liteflow.cmp;

import com.coral.base.common.StringUtils;
import com.coral.test.liteflow.slot.DiagnoseContext;
import com.coral.test.liteflow.slot.DiagnoseResponse;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 * @version 1.0
 * @className PersonGroupCmp
 * @description 人群分组
 * @date 2023/3/24 11:22
 */
@Slf4j
@LiteflowComponent(value = "personGroup", name = "人群分组")
public class PersonGroupCmp extends NodeComponent {
    @Override
    public void process() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        DiagnoseResponse diagnoseResponse = this.getContextBean(DiagnoseResponse.class);

        diagnoseResponse.setPatientName(diagnoseContext.getPatientName());
    }

    @Override
    public boolean isAccess() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return StringUtils.isNotBlank(diagnoseContext.getPatientName());
    }
}
