package com.coral.base.common.enums;

import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className ProtocolType
 * @description 应用层通信协议
 * @date 2021/4/23 13:14
 */
public enum ProtocolType implements IEnum<ProtocolType, String> {

    SOAP("SOAP"),

    HTTP("HTTP"),
    ;

    @Getter
    private String code;


    ProtocolType(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return code;
    }
}
