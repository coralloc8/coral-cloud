package com.coral.test.spring.graphql_jdk8.config.graphql.resolver;

import com.coral.test.spring.graphql_jdk8.config.exception.BusinessException;
import com.coral.test.spring.graphql_jdk8.config.graphql.GraphQlError;
import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

import javax.validation.ValidationException;
import java.util.Objects;

/**
 * graphql自定义异常
 *
 * @author huss
 * @date 2024/1/4 19:57
 * @packageName com.coral.test.spring.graphql.config.graphql
 * @className CustomExceptionResolver
 */
@Slf4j
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(@NotNull Throwable ex, @NotNull DataFetchingEnvironment env) {
        log.error("Error:", ex);
        ErrorClassification errorType = null;
        if (ex instanceof ValidationException) {
            errorType = GraphQlError.PARAMETER_VALIDATION_ERROR;
        } else if (ex instanceof BusinessException) {
            errorType = GraphQlError.BUSINESS_ERROR;
        }
        if (Objects.nonNull(errorType)) {
            return GraphqlErrorBuilder.newError()
                    .errorType(errorType)
                    .message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();
        }
        return super.resolveToSingleError(ex, env);
    }
}
