package com.example.spring.common.enums;

import com.example.spring.common.NameStyleUtil;

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
        String className = this.getClass().getName();
        className = NameStyleUtil.humpToLine(className).replaceFirst("_", "");
        if(this instanceof  Enum){
            return className.toLowerCase() + "." + this.toString().toLowerCase();
        }
        return className.toLowerCase();
    }

}
