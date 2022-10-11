package com.coral.cloud.gateway.config;

import org.springframework.web.server.ServerWebExchange;

import java.util.function.Function;

/**
 * @author huss
 * @version 1.0
 * @className FilterCache
 * @description 过滤器缓存
 * @date 2022/10/11 8:44
 */
public interface FilterCache {

    /**
     * 缓存formData
     */
    String CACHED_FORM_DATA = "cacheFormData";

    /**
     * Cached ServerHttpRequestDecorator attribute name. Used when
     * {@link org.springframework.cloud.gateway.support.ServerWebExchangeUtils#cacheRequestBodyAndRequest(ServerWebExchange, Function)} is called.
     */
    String CACHED_SERVER_HTTP_REQUEST_DECORATOR_ATTR = "cachedServerHttpRequestDecorator";

    /**
     * Cached request body key. Used when
     * {@link org.springframework.cloud.gateway.support.ServerWebExchangeUtils#cacheRequestBodyAndRequest(ServerWebExchange, Function)} or
     * {@link org.springframework.cloud.gateway.support.ServerWebExchangeUtils#cacheRequestBody(ServerWebExchange, Function)} are called.
     */
    String CACHED_REQUEST_BODY_ATTR = "cachedRequestBody";
}
