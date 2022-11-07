package com.coral.web.websocket.common.enums;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className BusinessType
 * @description 业务类型
 * @date 2022/10/28 10:49
 */
public enum BusinessType implements IEnum<BusinessType, String> {

    /**
     * 预问诊
     */
    YWZ("ywz", "预问诊"),

    /**
     * cdss
     */
    CDSS("cdss", "CDSS"),


    ;

    @Getter
    private String code;
    @Getter
    private String name;

    BusinessType(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
