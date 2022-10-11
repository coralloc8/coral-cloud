package com.coral.cloud.gateway.filter;

import com.coral.base.common.StringUtils;
import com.coral.cloud.gateway.config.FilterCache;
import lombok.NonNull;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className CommonFilter
 * @description 通用filter
 * @date 2022/10/11 10:08
 */

public interface CommonFilter {

    /**
     * 获取url链接中的参数或者获取 form表单中的参数
     *
     * @param paramName
     * @param exchange
     * @return
     */
    default String getParam(String paramName, ServerWebExchange exchange) {
        if (StringUtils.isBlank(paramName) || Objects.isNull(exchange)) {
            return "";
        }

        String param = exchange.getRequest().getQueryParams().getFirst(paramName);

        if (StringUtils.isBlank(param)) {
            Map<String, Object> formDataMap = (Map<String, Object>) exchange.getAttributes().getOrDefault(FilterCache.CACHED_FORM_DATA, Collections.emptyMap());

            Object obj = formDataMap.getOrDefault(paramName, "");

            if (obj instanceof List) {
                param = ((List<?>) obj).get(0).toString();
            } else {
                param = obj.toString();
            }
        }
        return param;
    }


    /**
     * form表单转字符串
     *
     * @param mu
     * @param charset
     * @return
     */
    default <T extends Map<String, String>> String formDataToStr(T mu, final Charset charset) {
        String params = mu.entrySet().stream()
                .map(e -> {
                    Object obj = e.getValue();
                    String value = "";
                    if (Objects.nonNull(obj)) {
                        if (obj instanceof List && ((List) obj).size() == 1) {
                            value = ((List) obj).get(0).toString();
                        } else {
                            value = obj.toString();
                        }
                    }
                    try {
                        return e.getKey() + "=" + URLEncoder.encode(value, charset.name());
                    } catch (UnsupportedEncodingException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(Collectors.joining("&"));

        return params;
    }


    /**
     * 获取请求头中的第一个数据
     *
     * @param headerName
     * @param exchange
     * @return
     */
    default String getHeaderFirst(String headerName, ServerWebExchange exchange) {
        if (StringUtils.isBlank(headerName) || Objects.isNull(exchange)) {
            return "";
        }
        return exchange.getRequest().getHeaders().getFirst(headerName);
    }

    /**
     * 获取bearer token
     *
     * @param exchange
     * @return
     */
    default String getBearerToken(@NonNull ServerWebExchange exchange) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(token)) {
            token = token.substring(7);
        }

        return token;
    }
}
