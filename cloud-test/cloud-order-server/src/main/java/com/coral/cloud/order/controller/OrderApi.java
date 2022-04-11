package com.coral.cloud.order.controller;

import com.coral.cloud.order.common.constants.ApiVersion;
import com.coral.cloud.order.vo.OrderInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className OrderApi
 * @description 订单api
 * @date 2022/4/2 11:26
 */
@Tags
public interface OrderApi {

    /**
     * 查询所有订单信息
     *
     * @return
     */
    @Operation(summary = "查询所有订单信息", description = "查询所有订单信息", tags = {ApiVersion.V_1_0_0})
    @ApiResponses({
            @ApiResponse(description = "success", responseCode = "200",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = OrderInfoVO.class))))
    })
    ResponseEntity<List<OrderInfoVO>> findOrderInfos();


}
