package com.coral.cloud.order.feign.service.fallback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author huss
 * @version 1.0
 * @className UserOrderFeignFallbackFactory
 * @description UserOrderFeignFallbackFactory
 * @date 2022/4/8 14:06
 */
@Component
@Slf4j
public class UserOrderFeignFallbackFactory implements FallbackFactory<UserOrderFeignFallback> {
    @Override
    public UserOrderFeignFallback create(Throwable cause) {
        log.error("<<<<<调用[user-server]服务异常 错误原因:{}>>>>>", cause.getMessage());
        return new UserOrderFeignFallback();
    }
}
