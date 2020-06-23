package com.example.spring.database.test.enums.file;

import com.example.spring.common.enums.IEnum;
import com.example.spring.common.jpa.enums.AbstractEnumConvert;

/**
 * @author huss
 */

public enum FileSaveTypeEnum implements IEnum<FileSaveTypeEnum, Integer> {

    /**
     * 本地磁盘
     */
    LOCAL_DISK(1, "local disk")

    ;

    private Integer code;

    private String name;

    FileSaveTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static class Convert extends AbstractEnumConvert<FileSaveTypeEnum, Integer> {
        public Convert() {
            super(FileSaveTypeEnum.class);
        }
    }
}
