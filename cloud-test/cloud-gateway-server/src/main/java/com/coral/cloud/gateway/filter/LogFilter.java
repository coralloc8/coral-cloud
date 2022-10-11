package com.coral.cloud.gateway.filter;

import com.coral.base.common.StringUtils;
import com.coral.base.common.exception.IErrorCodeMessage;
import com.coral.base.common.exception.OauthErrorMessageEnum;
import com.coral.base.common.http.response.ErrorResponse;
import com.coral.base.common.json.JsonUtil;
import com.coral.cloud.gateway.config.CommonParam;
import com.coral.cloud.gateway.config.FilterCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 日志打印filter
 */
@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered, CommonFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getResponse().isCommitted()) {
            log.debug(">>>>>该请求已经执行完毕...");
            return Mono.empty();
        }

        if (isOptions(exchange)) {
            return chain.filter(exchange);
        }

        String token = getBearerToken(exchange);

        if (StringUtils.isBlank(token)) {
            token = getParam(CommonParam.TOKEN, exchange);
        }

        log.info("###当前token:{}", token);

        // 测试用 强制url添加token
        if (StringUtils.isBlank(token)) {
            return responseError(exchange.getResponse(), OauthErrorMessageEnum.INVALID_TOKEN);
        }

        DataBuffer bodyDataBuffer = exchange.getAttribute(FilterCache.CACHED_REQUEST_BODY_ATTR);
        if (Objects.nonNull(bodyDataBuffer)) {
            String body = bodyDataBuffer.toString(StandardCharsets.UTF_8);
            log.info("###当前body:\n{}", body);
        }

        Object formData = exchange.getAttributeOrDefault(FilterCache.CACHED_FORM_DATA, "");
        log.info("###当前form data:\n{}", formData);

        //对请求进行路由
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }


    private boolean isOptions(ServerWebExchange exchange) {
        final ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatusCode(HttpStatus.OK);
            response.setComplete();
            return true;
        }

        return false;
    }


    /**
     * 将异常信息响应给前端
     */
    private Mono<Void> responseError(final ServerHttpResponse response, IErrorCodeMessage errorCodeMessage) {
        ErrorResponse responseMessage = new ErrorResponse(errorCodeMessage);

        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String json = JsonUtil.toJson(responseMessage);
        final DataBuffer wrap = response.bufferFactory().wrap(json.getBytes(StandardCharsets.UTF_8));
        response.setStatusCode(HttpStatus.UNAUTHORIZED);

        return response.writeWith(Mono.just(wrap));
    }

}
