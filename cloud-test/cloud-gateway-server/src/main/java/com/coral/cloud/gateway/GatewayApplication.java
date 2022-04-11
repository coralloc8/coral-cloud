package com.coral.cloud.gateway;

import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.base.common.http.response.ErrorResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

/**
 * @author huss
 * @version 1.0
 * @className GatewayApplication
 * @description 网关
 * @date 2022/4/6 14:24
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


    @Bean
    public RouterFunction<ServerResponse> fallbackRouter(GatewayFallbackHandler gatewayFallbackHandler) {
        return RouterFunctions.route(
                RequestPredicates.GET("/fallback"), gatewayFallbackHandler::fallback);
    }

    @Component
    static class GatewayFallbackHandler {
        public Mono<ServerResponse> fallback(ServerRequest request) {
            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new ErrorResponse(BaseErrorMessageEnum.INTERFACE_UNAVAILABLE)), ErrorResponse.class);
        }
    }
}
