package com.coral.test.spring.graphql_jdk8.config.graphql.schema;

import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLType;
import lombok.extern.slf4j.Slf4j;

import static graphql.Scalars.GraphQLBoolean;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLList.list;
import static graphql.schema.GraphQLNonNull.nonNull;
import static graphql.schema.GraphQLObjectType.newObject;
import static graphql.schema.GraphQLTypeReference.typeRef;

/**
 * 分页 schema
 *
 * @author huss
 * @date 2024/1/6 10:27
 * @packageName com.coral.test.spring.graphql_jdk8.config.graphql.schema
 * @className PageSchema
 */
@Slf4j
public class PageSchema {

    public final static String PAGE = "page";

    //    public static void createPageDefinition(TypeDefinitionRegistry typeDefinitionRegistry, String name) {
//        String pageName = createPageName(name);
//        boolean exist = typeDefinitionRegistry.getType(pageName).isPresent();
//        if (!exist) {
//            log.info(">>>>>[{}]不存在,开始创建.", pageName);
//            typeDefinitionRegistry.add(createPage(name));
//            typeDefinitionRegistry.add(PAGE_INFO_DEFINITION);
//        }
//    }
    public static String createPageName(String name) {
        return String.join("", name, "Page");
    }

    public static GraphQLObjectType createPageType(String name, GraphQLType recordType) {
        return newObject()
                .name(name)
                .description("A page to a list of items.")
                .field(newFieldDefinition()
                        .name("records")
                        .description("a list of edges")
                        .type(list(recordType)))
                .field(newFieldDefinition()
                        .name("pageInfo")
                        .description("details about this specific page")
                        .type(nonNull(PAGE_INFO_TYPE)))
                .build();
    }

    private static final GraphQLObjectType PAGE_INFO_TYPE = newObject()
            .name("PageInfo")
            .description("Information about pagination in a connection.")
            .field(newFieldDefinition()
                    .name("hasNextPage")
                    .type(nonNull(GraphQLBoolean))
                    .description("是否有下一页"))
            .field(newFieldDefinition()
                    .name("hasPreviousPage")
                    .type(nonNull(GraphQLBoolean))
                    .description("是否有上一页"))
            .field(newFieldDefinition()
                    .name("pages")
                    .type(typeRef("Long"))
                    .description("总页数"))
            .field(newFieldDefinition()
                    .name("size")
                    .type(typeRef("Long"))
                    .description("当前页数"))
            .field(newFieldDefinition()
                    .name("total")
                    .type(typeRef("Long"))
                    .description("总数据量"))
            .build();


//    private static ObjectTypeDefinition createPage(String name) {
//        return ObjectTypeDefinition.newObjectTypeDefinition()
//                .name(createPageName(name))
//                .description(createDescription("A page to a list of items."))
//                .fieldDefinition(
//                        new FieldDefinition("records", new ListType(new TypeName(name)))
//                                .transform(de -> de.description(createDescription("a list of records"))))
//                .fieldDefinition(
//                        new FieldDefinition("pageInfo", new TypeName("PageInfo"))
//                                .transform(de -> de.description(createDescription("details about this specific page"))))
//                .build();
//    }


//    private static final ObjectTypeDefinition PAGE_INFO_DEFINITION = ObjectTypeDefinition.newObjectTypeDefinition()
//            .name("PageInfo")
//            .description(createDescription("Information about pagination in a page"))
//            .fieldDefinition(
//                    new FieldDefinition("hasNextPage", new TypeName("Boolean"))
//                            .transform(de -> de.description(createDescription("是否有下一页"))))
//            .fieldDefinition(
//                    new FieldDefinition("hasPreviousPage", new TypeName("Boolean"))
//                            .transform(de -> de.description(createDescription("是否有上一页"))))
//            .fieldDefinition(
//                    new FieldDefinition("pages", new TypeName("Long"))
//                            .transform(de -> de.description(createDescription("总页数"))))
//            .fieldDefinition(
//                    new FieldDefinition("size", new TypeName("Long"))
//                            .transform(de -> de.description(createDescription("当前页数"))))
//            .fieldDefinition(
//                    new FieldDefinition("total", new TypeName("Long"))
//                            .transform(de -> de.description(createDescription("总数据量"))))
//            .build();


//    private static Description createDescription(String content) {
//        return new Description(content, SourceLocation.EMPTY, false);
//    }


}
