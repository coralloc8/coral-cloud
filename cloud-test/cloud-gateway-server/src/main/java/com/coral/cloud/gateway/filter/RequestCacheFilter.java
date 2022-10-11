package com.coral.cloud.gateway.filter;

import com.coral.cloud.gateway.config.FilterCache;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className RequestCacheFilter
 * @description request 数据缓存
 * @date 2021/9/22 16:57
 */
@Component
@Slf4j
public class RequestCacheFilter implements GlobalFilter, Ordered, CommonFilter {

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        final HttpMethod method = exchange.getRequest().getMethod();

        if (HttpMethod.OPTIONS.equals(method) || HttpMethod.GET.equals(method)) {
            return chain.filter(exchange);
        }

        boolean isJson = MediaType.APPLICATION_JSON.includes(exchange.getRequest().getHeaders().getContentType());
        boolean isFormData = MediaType.APPLICATION_FORM_URLENCODED.includes(exchange.getRequest().getHeaders().getContentType());
        // 使用 Gateway 自带工具 构建缓存body
        if (isJson) {
            log.info(">>>>>缓存request body data");
            return ServerWebExchangeUtils.cacheRequestBodyAndRequest(exchange, (serverHttpRequest) -> {
                if (serverHttpRequest == exchange.getRequest()) {
                    return chain.filter(exchange);
                }
                return chain.filter(exchange.mutate().request(serverHttpRequest).build());
            });
        }

        if (isFormData) {
            return readFormData(exchange, chain);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> readFormData(ServerWebExchange exchange, GatewayFilterChain chain) {
        return exchange.getFormData().flatMap(mu -> {
            log.info(">>>>>缓存request form data");

            Charset charset = exchange.getRequest().getHeaders().getContentType().getCharset();
            charset = Objects.isNull(charset) ? StandardCharsets.UTF_8 : charset;

            Charset finalCharset = charset;
            String params = formDataToStr((Map) mu, finalCharset);

            exchange.getAttributes().put(FilterCache.CACHED_FORM_DATA, mu);

            int contentLength = params.length();
            final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return DataBufferUtils.read(new ByteArrayResource(params.getBytes(finalCharset)),
                            new NettyDataBufferFactory(ByteBufAllocator.DEFAULT), contentLength
                    );
                }
            };

            ServerHttpRequest copyRequest = mutatedRequest.mutate()
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength))
                    .build();

            return chain.filter(exchange.mutate().request(copyRequest).build());
        }).switchIfEmpty(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}