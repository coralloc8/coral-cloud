package com.coral.cloud.order.controller;

import com.coral.cloud.order.vo.OrderInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className OrderController
 * @description 订单控制器
 * @date 2022/3/31 17:37
 */
@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController implements OrderApi {

    /**
     * 查询所有订单信息
     *
     * @return
     */
    @GetMapping
    @Override
    public ResponseEntity<List<OrderInfoVO>> findOrderInfos() {
        return ResponseEntity.ok(OrderInfoVO.findAllOrders());
    }


}
