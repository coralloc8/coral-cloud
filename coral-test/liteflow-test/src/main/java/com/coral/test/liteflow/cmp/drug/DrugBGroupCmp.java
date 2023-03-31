package com.coral.test.liteflow.cmp.drug;

import com.coral.test.liteflow.slot.DiagnoseContext;
import com.coral.test.liteflow.slot.DiagnoseResponse;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

import java.util.Arrays;

/**
 * @author huss
 * @version 1.0
 * @className DrugBGroupCmp
 * @description 药品B
 * @date 2023/3/27 16:09
 */
@LiteflowComponent(value = "drugBGroup", name = "药品B")
public class DrugBGroupCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        DiagnoseResponse diagnoseResponse = this.getContextBean(DiagnoseResponse.class);
        diagnoseResponse.setDrugs(Arrays.asList("川贝枇杷膏"));
    }

}
