package com.coral.test.spring.graphql_jdk8.config.graphql.scalar;

import com.coral.test.spring.graphql_jdk8.util.DateTimeFormatterUtil;
import graphql.language.StringValue;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static graphql.scalars.util.Kit.typeName;


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
    public String serialize(@NotNull Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof LocalDateTime) {
            try {
                LocalDateTime inputStr = (LocalDateTime) dataFetcherResult;
                return DateTimeFormatterUtil.format(inputStr);
            } catch (DateTimeException e) {
                throw new CoercingParseValueException(e);
            }
        }
        return dataFetcherResult.toString();
    }


    //反序列化方法，从JSON String到Date Object
    @Override
    public @NotNull LocalDateTime parseValue(@NotNull Object input) throws CoercingParseValueException {
        if (input instanceof String) {
            try {
                String inputStr = (String) input;
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
    public @NotNull LocalDateTime parseLiteral(@NotNull Object input) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            try {
                StringValue inputStr = (StringValue) input;
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
