package com.coral.test.spring.graphql_jdk8.config.graphql.interceptor;

import com.coral.base.common.StringUtils;
import com.coral.test.spring.graphql_jdk8.config.graphql.GraphQlError;
import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * graphql 拦截器
 *
 * @author huss
 * @date 2024/1/5 9:48
 * @packageName com.coral.test.spring.graphql.config.graphql
 * @className GraphQlRequestInterceptor
 */
@Slf4j
public class GraphQlRequestInterceptor implements WebGraphQlInterceptor {


    // https://docs.spring.io/spring-graphql/docs/1.0.6/reference/html/#server-interception-web
    @Override
    public @NotNull Mono<WebGraphQlResponse> intercept(@NotNull WebGraphQlRequest request, @NotNull Chain chain) {
        String account = request.getHeaders().getFirst("account");
        request.configureExecutionInput((executionInput, builder) -> {
            if (StringUtils.isNotBlank(account)) {
                builder.graphQLContext(Collections.singletonMap("account", account));
            }
            return builder.build();
        });

        return chain.next(request).map(response -> {
            if (response.isValid()) {
                return response;
            }

            List<GraphQLError> errors = response.getErrors().stream()
                    .map(error -> {
                        ErrorClassification errorType = GraphQlError.SYSTEM_ERROR;
                        if (ErrorType.DataFetchingException.equals(error.getErrorType())) {
                            errorType = GraphQlError.DATA_FETCHING_ERROR;
                        }
                        return GraphqlErrorBuilder.newError()
                                .errorType(errorType)
                                .message(error.getMessage())
                                .path(error.getParsedPath())
                                .locations(error.getLocations())
                                .build();

                    })
                    .collect(Collectors.toList());

            return response.transform(builder -> builder.errors(errors).build());
        }).doOnNext(response -> {
            /**
             *  String value = response.getExecutionInput().getGraphQLContext().get("cookieName");
             *  ResponseCookie cookie = ResponseCookie.from("cookieName", value).build();
             *  response.getResponseHeaders().add(HttpHeaders.SET_COOKIE, cookie.toString());
             */
            // todo 可以设置response
        });
    }
}
