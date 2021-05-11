package com.coral.database.test.enums;

import com.coral.base.common.enums.IEnum;
import com.coral.base.common.jpa.enums.AbstractEnumConvert;

/**
 * @formatter:off
 * 1i8n module
 * code: base64编码
 * @formatter:on
 * @author huss
 */

public enum I18nModuleEnum implements IEnum<I18nModuleEnum, String> {

    /**
     * menu
     */
    MENU("bWVudQ==", "menu"),

    /**
     * right
     */
    RIGHT("cmlnaHQ=", "right"),

    /**
     * role
     */
    ROLE("cm9sZQ==", "role"),

    /**
     * dictionary
     */
    DICTIONARY("ZGljdGlvbmFyeQ==", "dictionary"),

    ;

    private String code;

    private String name;

    I18nModuleEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static class Convert extends AbstractEnumConvert<I18nModuleEnum, String> {
        public Convert() {
            super(I18nModuleEnum.class);
        }
    }
}
