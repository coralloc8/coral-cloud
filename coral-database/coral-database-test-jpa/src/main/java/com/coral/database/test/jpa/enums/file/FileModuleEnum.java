package com.coral.database.test.jpa.enums.file;


import com.coral.base.common.enums.IEnum;
import com.coral.base.common.jpa.enums.AbstractEnumConvert;

/**
 * code: base64编码
 * 
 * @author huss
 */

public enum FileModuleEnum implements IEnum<FileModuleEnum, String> {

    /**
     * user_header_image
     */
    USER_HEADER_IMAGE("dXNlcl9oZWFkZXJfaW1hZ2U=", "user_header_image"),

    ;

    private String code;

    private String name;

    FileModuleEnum(String code, String name) {
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

    public static class Convert extends AbstractEnumConvert<FileModuleEnum, String> {
        public Convert() {
            super(FileModuleEnum.class);
        }
    }
}
