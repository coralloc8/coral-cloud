package com.coral.test.spring.graphql_jdk8.config.graphql.schema;

import graphql.schema.GraphQLType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static graphql.schema.GraphQLTypeReference.typeRef;

/**
 * GraphQlSchemaUtil
 *
 * @author huss
 * @date 2024/1/6 15:01
 * @packageName com.coral.test.spring.graphql_jdk8.config.graphql.schema
 * @className SchemaUtil
 */
public class GraphQlSchemaCache {
    private static final Map<String, GraphQLType> CACHE = new ConcurrentHashMap<>();

    /**
     *
     * @param name
     * @param qlType
     * @return
     * @param <T>
     */
    public static <T extends GraphQLType> T createGraphQlType(String name, T qlType) {
        if (CACHE.containsKey(name)) {
            return (T) typeRef(name);
        }
        CACHE.put(name, qlType);
        return qlType;
    }


}
