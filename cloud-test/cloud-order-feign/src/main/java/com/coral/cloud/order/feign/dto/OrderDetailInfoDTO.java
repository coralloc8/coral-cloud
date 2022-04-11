package com.coral.cloud.order.feign.dto;

import com.coral.base.common.http.response.IResponse;
import com.coral.cloud.order.feign.enums.AftermarketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huss
 * @version 1.0
 * @className OrderDetailInfoVO
 * @description 订单详情
 * @date 2022/4/2 13:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailInfoDTO implements IResponse {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 商品spu编码
     */
    private String goodsSpuCode;

    /**
     * 商品spu名称
     */
    private String goodsSpuName;

    /**
     * 商品sku编码
     */
    private String goodsSkuCode;

    /**
     * 商品sku名称
     */
    private String goodsSkuName;

    /**
     * 规格
     */
    private String spec;

    /**
     * 商品原始价格 单位:分
     */
    private Long originalPrice;

    /**
     * 商品折后价格 单位:分
     */
    private Long discountPrice;

    /**
     * 商品数量
     */
    private Long count;

    /**
     * 总价 单位:分
     */
    private Long totalPrice;

    /**
     * 售后状态
     */
    private AftermarketStatus aftermarketStatus;

}