package com.coral.test.liteflow.cmp.drug;

import com.coral.base.common.CollectionUtil;
import com.coral.test.liteflow.slot.DiagnoseContext;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeIfComponent;

/**
 * @author huss
 * @version 1.0
 * @className DrugIfGroupCmp
 * @description 药品条件组件
 * @date 2023/3/27 16:09
 */
@LiteflowComponent(value = "drugIfGroup", name = "药品条件分组")
public class DrugIfGroupCmp extends NodeIfComponent {
    @Override
    public boolean processIf() throws Exception {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return diagnoseContext.getDrugs().stream().anyMatch(e -> e.equals("京都念慈菴"));
    }

    @Override
    public boolean isAccess() {
        DiagnoseContext diagnoseContext = this.getContextBean(DiagnoseContext.class);
        return CollectionUtil.isNotBlank(diagnoseContext.getDrugs());
    }
}
