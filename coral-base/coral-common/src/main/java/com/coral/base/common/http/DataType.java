package com.coral.base.common.http;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className DataType
 * @description 数据类型
 * @date 2021/4/20 9:30
 */
public enum DataType implements IEnum<DataType, String> {
    /**
     * json
     */
    JSON("JSON"),

    /**
     * xml
     */
    XML("XML");

    @Getter
    private String code;

    @Override
    public String getName() {
        return code;
    }

    DataType(String code) {
        this.code = code;
    }
}
