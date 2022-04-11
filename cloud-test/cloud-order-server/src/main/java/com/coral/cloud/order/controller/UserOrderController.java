package com.coral.cloud.order.controller;

import com.coral.cloud.order.vo.OrderInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className UserOrderController
 * @description 用户订单控制器
 * @date 2022/3/31 17:37
 */
@RestController
@RequestMapping("/users/{userNo}/orders")
@Slf4j
public class UserOrderController implements UserOrderApi {

    /**
     * 查询用户的所有订单信息
     *
     * @param userNo
     * @return
     */
    @GetMapping
    @Override
    public ResponseEntity<List<OrderInfoVO>> findUserOrderInfos(@PathVariable String userNo) {
        return ResponseEntity.ok(OrderInfoVO.findAllOrders().stream()
                .filter(e -> e.getUserNo().equals(userNo))
                .collect(Collectors.toList())
        );
    }


}
