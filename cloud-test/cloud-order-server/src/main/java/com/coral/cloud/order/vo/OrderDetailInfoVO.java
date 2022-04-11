package com.coral.cloud.order.vo;

import com.coral.base.common.http.response.IResponse;
import com.coral.cloud.order.feign.enums.AftermarketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "订单详情")
public class OrderDetailInfoVO implements IResponse {

    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "商品spu编码")
    private String goodsSpuCode;

    @Schema(description = "商品spu名称")
    private String goodsSpuName;

    @Schema(description = "商品sku编码")
    private String goodsSkuCode;

    @Schema(description = "商品sku名称")
    private String goodsSkuName;

    @Schema(description = "规格")
    private String spec;

    @Schema(description = "商品原始价格 单位:分")
    private Long originalPrice;

    @Schema(description = "商品折后价格 单位:分")
    private Long discountPrice;

    @Schema(description = "商品数量")
    private Long count;

    @Schema(description = "总价 单位:分")
    private Long totalPrice;

    @Schema(description = "售后状态")
    private AftermarketStatus aftermarketStatus;


}