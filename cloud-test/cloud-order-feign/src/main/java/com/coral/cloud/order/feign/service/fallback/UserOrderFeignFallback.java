package com.coral.cloud.order.feign.service.fallback;

import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.base.common.exception.SystemRuntimeException;
import com.coral.cloud.order.feign.dto.OrderInfoDTO;
import com.coral.cloud.order.feign.service.UserOrderFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huss
 * @version 1.0
 * @className UserOrderFeignFallback
 * @description 用户订单熔断
 * @date 2022/4/8 9:39
 */
@Slf4j
@Component
public class UserOrderFeignFallback implements UserOrderFeign {
    @Override
    public ResponseEntity<List<OrderInfoDTO>> findUserOrderInfos(String userNo) {
        log.error(">>>>>[findUserOrderInfos] 接口不可用,触发熔断,userNo:{}...", userNo);
        throw new SystemRuntimeException(BaseErrorMessageEnum.INTERFACE_UNAVAILABLE);
    }
}
