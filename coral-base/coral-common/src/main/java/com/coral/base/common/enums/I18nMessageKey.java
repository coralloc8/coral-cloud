package com.coral.base.common.enums;

import com.coral.base.common.I18nMessageUtil;

/**
 * @author huss
 */
public interface I18nMessageKey {

    /**
     * 获取i18n唯一标识
     * 
     * @return
     */
    default String getMessageKey() {
        return I18nMessageUtil.getMessageKey(this);
    }

}
