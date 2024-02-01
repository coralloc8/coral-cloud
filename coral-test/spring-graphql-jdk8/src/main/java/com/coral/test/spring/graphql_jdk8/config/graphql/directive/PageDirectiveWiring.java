package com.coral.test.spring.graphql_jdk8.config.graphql.directive;

import com.coral.test.spring.graphql_jdk8.config.graphql.schema.GraphQlSchemaCache;
import com.coral.test.spring.graphql_jdk8.config.graphql.schema.PageSchema;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLNamedSchemaElement;
import graphql.schema.GraphQLOutputType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页
 *
 * @author huss
 * @date 2024/1/3 15:02
 * @packageName com.coral.test.spring.graphql.config.graphql
 * @className PageDirectiveWiring
 */
@Slf4j
public class PageDirectiveWiring implements SchemaDirectiveWiring {
    public final static SchemaDirectiveWiring INSTANCE = new PageDirectiveWiring();

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition field = environment.getElement();
        // 先schema化  再生成 object
        String name = ((GraphQLNamedSchemaElement) field.getType()).getName();
        String pageName = PageSchema.createPageName(name);
        GraphQLOutputType type = GraphQlSchemaCache.createGraphQlType(pageName, PageSchema.createPageType(pageName, field.getType()));
        field = field.transform(c -> c.type(type));
        return field;
    }


}
