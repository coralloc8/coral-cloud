package com.coral.cloud.order.feign.service;

import com.coral.cloud.order.feign.dto.OrderInfoDTO;
import com.coral.cloud.order.feign.service.fallback.UserOrderFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className UserOrderFeign
 * @description 用户订单feign
 * @date 2022/4/8 9:08
 */
@FeignClient(value = "order-server", fallbackFactory = UserOrderFeignFallbackFactory.class)
public interface UserOrderFeign {
    /**
     * 查询用户的所有订单信息
     *
     * @param userNo
     * @return
     */
    @GetMapping("/users/{userNo}/orders")
    ResponseEntity<List<OrderInfoDTO>> findUserOrderInfos(@PathVariable("userNo") String userNo);
}
