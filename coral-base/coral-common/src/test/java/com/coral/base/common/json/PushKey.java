package com.coral.base.common.json;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className PushKey
 * @description 测试pushKey
 * @date 2022/2/8 13:30
 */
enum PushKey implements IEnum<PushKey, String> {
    /**
     * 微信
     */
    WEIXIN("001", "微信"),
    /**
     * 支付宝
     */
    ZHIFUBAO("002", "支付宝");

    @Getter
    private String code;

    @Getter
    private String name;

    PushKey(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
