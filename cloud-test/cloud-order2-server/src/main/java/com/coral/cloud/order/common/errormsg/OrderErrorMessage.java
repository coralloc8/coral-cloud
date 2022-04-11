package com.coral.cloud.order.common.errormsg;

import com.coral.base.common.exception.IErrorCodeMessage;
import com.coral.base.common.exception.IHttpStatus;
import lombok.Getter;

/**
 * @author huss
 * @version 1.0
 * @className OrderErrorMessage
 * @description 订单异常信息
 * @date 2022/4/2 9:16
 */
public enum OrderErrorMessage implements IErrorCodeMessage<OrderErrorMessage>, IHttpStatus {

    /**
     * 11 order-server  01 订单管理  001 具体错误码索引
     */

    /**
     * 订单不存在
     */
    ORDER_NOT_EXIST(OrderErrorMessageDesc.ORDER_NOT_EXIST_CODE,
            OrderErrorMessageDesc.ORDER_NOT_EXIST_DESC,
            OrderErrorMessageDesc.ORDER_NOT_EXIST_HTTP
    ),


    ;
    @Getter
    private Integer errorCode;

    @Getter
    private String errorMsg;

    @Getter
    private Integer httpStatus;

    OrderErrorMessage(String errorCode, String errorMsg, Integer httpStatus) {
        this.errorCode = Integer.valueOf(errorCode);
        this.errorMsg = errorMsg;
        this.httpStatus = httpStatus;
    }

}
