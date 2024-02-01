package com.coral.test.spring.graphql.config.graphql;

import com.coral.test.spring.graphql.util.DateTimeFormatterUtil;
import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;

import static graphql.scalar.CoercingUtil.typeName;

/**
 * scalar LocalDateTime
 *
 * @author huss
 * @date 2023/12/28 17:10
 * @packageName com.coral.test.spring.graphql.config.scalar
 * @className ScalarLocalDateTime
 */
public class ScalarLocalDateTime implements Coercing<LocalDateTime, String> {

    public static final GraphQLScalarType SCALAR_LOCAL_DATE_TIME = GraphQLScalarType
            .newScalar().name("LocalDateTime")
            .description("LocalDateTime scalar")
            .coercing(new ScalarLocalDateTime()).build();

    //序列化方法，从Date Object到JSON String
    @Override
    public String serialize(@NotNull Object dataFetcherResult,
                            @NotNull GraphQLContext graphQLContext,
                            @NotNull Locale locale) throws CoercingSerializeException {
        if (dataFetcherResult instanceof LocalDateTime inputStr) {
            try {
                return DateTimeFormatterUtil.format(inputStr);
            } catch (DateTimeException e) {
                throw new CoercingParseValueException(e);
            }
        }
        return "";
    }


    //反序列化方法，从JSON String到Date Object
    @Override
    public LocalDateTime parseValue(@NotNull Object input,
                                    @NotNull GraphQLContext graphQLContext,
                                    @NotNull Locale locale) throws CoercingParseValueException {
        if (input instanceof String inputStr) {
            try {
                Optional<Instant> instantOpt = DateTimeFormatterUtil.parseDateTime(inputStr);
                if (instantOpt.isPresent()) {
                    return LocalDateTime.ofInstant(instantOpt.get(), ZoneId.systemDefault());
                }
            } catch (DateTimeException e) {
                throw new CoercingParseValueException(e);
            }
        }
        throw new CoercingParseValueException(
                "Expected a 'String' but was '" + typeName(input) + "'."
        );
    }

    //反序列化方法，从AST（Abstract Syntax Tree）到Date Object
    @Override
    public LocalDateTime parseLiteral(@NotNull Value<?> input,
                                      @NotNull CoercedVariables variables,
                                      @NotNull GraphQLContext graphQLContext,
                                      @NotNull Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue inputStr) {
            try {
                Optional<Instant> instantOpt = DateTimeFormatterUtil.parseDateTime(inputStr.getValue());
                if (instantOpt.isPresent()) {
                    return LocalDateTime.ofInstant(instantOpt.get(), ZoneId.systemDefault());
                }
            } catch (DateTimeException e) {
                throw new CoercingParseValueException(e);
            }
        }
        throw new CoercingParseLiteralException(
                "Expected AST type 'StringValue' but was '" + typeName(input) + "'."
        );
    }
}
