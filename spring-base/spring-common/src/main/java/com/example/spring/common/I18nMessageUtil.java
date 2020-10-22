package com.example.spring.common;

/**
 * 生成i18n的唯一key
 * 
 * @author huss
 */
public class I18nMessageUtil {

    public static String getMessageKey(Enum enums) {
        String className = enums.getClass().getName();
        className = NameStyleUtil.humpToLine(className).replaceFirst("_", "");
        return className.toLowerCase() + "." + enums.toString().toLowerCase();
    }

}
