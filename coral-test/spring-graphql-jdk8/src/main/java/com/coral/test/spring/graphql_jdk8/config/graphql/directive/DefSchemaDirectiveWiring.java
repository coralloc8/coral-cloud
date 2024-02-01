package com.coral.test.spring.graphql_jdk8.config.graphql.directive;

import cn.hutool.core.collection.CollUtil;
import graphql.execution.ValuesResolver;
import graphql.language.Argument;
import graphql.language.Directive;
import graphql.language.Value;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLAppliedDirectiveArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.idl.SchemaDirectiveWiring;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * DefSchemaDirectiveWiring
 *
 * @author huss
 * @date 2024/1/8 14:51
 * @packageName com.coral.test.spring.graphql_jdk8.config.graphql.directive
 * @className DefSchemaDirectiveWiring
 */
public interface DefSchemaDirectiveWiring extends SchemaDirectiveWiring {
    default Optional<Value> getArgByField(DataFetchingEnvironment environment, String directiveName, String argName) {
        List<Directive> directives = environment.getField().getDirectives(directiveName);
        if (CollUtil.isEmpty(directives)) {
            return Optional.empty();
        }
        Argument argument = directives.get(0).getArgument(argName);
        if (Objects.isNull(argument)) {
            return Optional.empty();
        }
        return Optional.of(argument.getValue());
    }

    default <T> T getArgByFieldDefinition(GraphQLFieldDefinition fieldDefinition, String directiveName, String argName) {
        Optional<GraphQLAppliedDirectiveArgument> from = Optional.ofNullable(fieldDefinition.getAppliedDirective(directiveName).getArgument(argName));
        return (T) from.map(arg -> ValuesResolver.getInputValueImpl(arg.getType(), arg.getArgumentValue())).orElse(null);
    }
}
