package com.coral.cloud.order.vo;

import com.coral.base.common.http.response.IResponse;
import com.coral.cloud.order.feign.enums.AftermarketStatus;
import com.coral.cloud.order.feign.enums.OrderStatus;
import com.coral.cloud.order.feign.enums.PayStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

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
@Schema(description = "用户信息")
public class OrderInfoVO implements IResponse {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "用户编码")
    private String userNo;

    @Schema(description = "支付单号")
    private String payNo;

    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付总金额 单位:分")
    private Long totalAmountPaid;

    @Schema(description = "优惠总金额 单位:分")
    private Long totalPreferentialAmount;

    @Schema(description = "订单状态")
    private OrderStatus orderStatus;

    @Schema(description = "支付状态")
    private PayStatus payStatus;

    @Schema(description = "售后状态")
    private AftermarketStatus aftermarketStatus;

    @Schema(description = "订单详情")
    private List<OrderDetailInfoVO> details;


    private static final List<OrderInfoVO> ORDERS = new ArrayList<>();

    static {
        IntStream.rangeClosed(0, 4)
                .forEach(e -> ORDERS.add(OrderInfoVO.createOrder(
                        String.valueOf(e + 1), Long.valueOf(e + 3), Long.valueOf(e + 4))));
    }

    public static List<OrderInfoVO> findAllOrders() {
        return ORDERS;
    }


    public static OrderInfoVO createOrder(String userNo, Long goodsCount1, Long goodsCount2) {
        String orderNo = UUID.randomUUID().toString().replaceAll("-", "");
        String payNo = UUID.randomUUID().toString().replaceAll("-", "");


        Long discountPrice = 7000L;
        Long totalPrice1 = discountPrice * goodsCount1;

        List<OrderDetailInfoVO> details = new ArrayList<>();
        OrderDetailInfoVO orderDetail = OrderDetailInfoVO.builder()
                .orderNo(orderNo)
                .goodsSpuName("可口可乐500ML")
                .goodsSpuCode("1001")
                .goodsSkuCode("31001")
                .goodsSkuName("可口可乐 Coca-Cola 汽水 碳酸饮料 500ML*24瓶 整箱装 可口可乐出品 冬奥铝瓶")
                .spec("500ML")
                .originalPrice(8000L)
                .discountPrice(discountPrice)
                .count(goodsCount1)
                .totalPrice(totalPrice1)
                .aftermarketStatus(null)
                .build();
        details.add(orderDetail);


        discountPrice = 2000L;
        Long totalPrice2 = discountPrice * goodsCount2;
        orderDetail = OrderDetailInfoVO.builder()
                .orderNo(orderNo)
                .goodsSpuName("乐事薯片40g")
                .goodsSpuCode("5001")
                .goodsSkuCode("85001")
                .goodsSkuName("乐·事无限40g*12桶小罐装原味黄瓜番茄味百事薯片网红休闲小吃零食 【6罐】原味2+番茄2+黄瓜味1+烤肉味1")
                .spec("40g")
                .originalPrice(2400L)
                .discountPrice(discountPrice)
                .count(goodsCount2)
                .totalPrice(totalPrice2)
                .aftermarketStatus(null)
                .build();
        details.add(orderDetail);


        Long totalPreferentialAmount = 5000L;
        Long totalAmountPaid = totalPrice1 + totalPrice2 - totalPreferentialAmount;
        OrderInfoVO order = OrderInfoVO.builder()
                .orderNo(orderNo)
                .userNo(userNo)
                .payNo(payNo)
                .orderTime(LocalDateTime.now().minusMinutes(20))
                .payTime(LocalDateTime.now())
                .totalAmountPaid(totalAmountPaid)
                .totalPreferentialAmount(5000L)
                .orderStatus(OrderStatus.PENDING_DELIVER)
                .payStatus(PayStatus.PAYMENT_COMPLETED)
                .aftermarketStatus(null)
                .details(details)
                .build();

        return order;
    }


}