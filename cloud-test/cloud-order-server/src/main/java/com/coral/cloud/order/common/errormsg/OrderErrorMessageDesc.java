package com.coral.cloud.order.common.errormsg;

import org.springframework.http.HttpStatus;

/**
 * @author huss
 * @version 1.0
 * @className OrderErrorMessageDesc
 * @description 订单错误码说明
 * @date 2022/4/2 11:11
 */
public interface OrderErrorMessageDesc {

    /**
     * 订单不存在
     */
    String ORDER_NOT_EXIST_CODE = "1101001";
    String ORDER_NOT_EXIST_DESC = "订单不存在";
    Integer ORDER_NOT_EXIST_HTTP = HttpStatus.NOT_FOUND.value();
}
