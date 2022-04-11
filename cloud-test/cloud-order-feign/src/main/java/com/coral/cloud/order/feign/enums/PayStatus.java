package com.coral.cloud.order.feign.enums;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className PayStatus
 * @description 支付状态
 * @date 2022/4/2 15:03
 */
public enum PayStatus implements IEnum<PayStatus, String> {

    /**
     * 支付取消
     */
    PAYMENT_CANCELED("payment_canceled", "支付取消"),

    /**
     * 支付过期
     */
    PAYMENT_EXPIRED("payment_expired", "支付过期"),

    /**
     * 支付完成
     */
    PAYMENT_COMPLETED("payment_completed", "支付完成"),


    ;
    @Getter
    private String code;

    @Getter
    private String name;

    PayStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}