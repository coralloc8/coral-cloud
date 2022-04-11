package com.coral.cloud.order.feign.dto;

import com.coral.base.common.http.response.IResponse;
import com.coral.cloud.order.feign.enums.AftermarketStatus;
import com.coral.cloud.order.feign.enums.OrderStatus;
import com.coral.cloud.order.feign.enums.PayStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className OrderInfoVO
 * @description 订单信息
 * @date 2022/4/2 13:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfoDTO implements IResponse {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户编码
     */
    private String userNo;

    /**
     * 支付单号
     */
    private String payNo;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付总金额 单位:分
     */
    private Long totalAmountPaid;

    /**
     * 优惠总金额 单位:分
     */
    private Long totalPreferentialAmount;

    /**
     * 订单状态
     */
    private OrderStatus orderStatus;

    /**
     * 支付状态
     */
    private PayStatus payStatus;

    /**
     * 售后状态
     */
    private AftermarketStatus aftermarketStatus;

    /**
     * 订单详情
     */
    private List<OrderDetailInfoDTO> details;


}