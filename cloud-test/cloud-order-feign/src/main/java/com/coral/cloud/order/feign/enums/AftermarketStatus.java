package com.coral.cloud.order.feign.enums;

import com.coral.base.common.enums.IEnum;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className AftermarketStatus
 * @description 订单售后状态
 * @date 2022/4/2 16:05
 */
public enum AftermarketStatus implements IEnum<AftermarketStatus, String> {
    /**
     * 待退款
     */
    PENDING_REFUND("pending_refund", "待退款"),

    /**
     * 退款关闭
     */
    REFUND_CLOSED("refund_closed", "退款关闭"),

    /**
     * 部分退款
     */
    PARTIAL_REFUND_COMPLETED("partial_refund_completed", "部分退款"),

    /**
     * 退款完成
     */
    REFUND_COMPLETED("refund_completed", "退款完成"),
    /**
     * 待换货
     */
    PENDING_REPLACE("pending_replace", "待换货"),

    /**
     * 换货关闭
     */
    REPLACE_CLOSED("replace_closed", "换货关闭"),

    /**
     * 部分换货
     */
    PARTIAL_REPLACE_COMPLETED("partial_replace_completed", "部分换货"),

    /**
     * 换货完成
     */
    REPLACE_completed("replace_completed", "换货完成"),

    ;
    @Getter
    private String code;

    @Getter
    private String name;

    AftermarketStatus(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
