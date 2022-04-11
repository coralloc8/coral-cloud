package com.coral.cloud.order.feign.enums;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className OrderStatus
 * @description 订单状态
 * @date 2022/4/2 14:41
 */
public enum OrderStatus implements IEnum<OrderStatus, String> {

    /**
     * 待付款
     */
    PENDING_PAYMENT("pending_payment", "待付款"),
    /**
     * 订单关闭
     */
    CLOSED("closed", "订单关闭"),
    /**
     * 待发货
     */
    PENDING_DELIVER("pending_deliver", "待发货"),
    /**
     * 待收货
     */
    PENDING_RECEIPT("pending_receipt", "待收货"),
    /**
     * 待评价
     */
    PENDING_EVALUATE("pending_evaluate", "待评价"),
    /**
     * 订单完成
     */
    COMPLETED("completed", "订单完成"),

    ;
    @Getter
    private String code;

    @Getter
    private String name;

    OrderStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
