package com.coral.test.spring.graphql_jdk8.config;

import com.coral.test.spring.graphql_jdk8.config.graphql.TypeRuntimeWiringFactory;
import com.coral.test.spring.graphql_jdk8.config.graphql.directive.AuthorisationDirectiveWiring;
import com.coral.test.spring.graphql_jdk8.config.graphql.directive.DateFormatDirectiveWiring;
import com.coral.test.spring.graphql_jdk8.config.graphql.directive.PageDirectiveWiring;
import com.coral.test.spring.graphql_jdk8.config.graphql.interceptor.GraphQlRequestInterceptor;
import com.coral.test.spring.graphql_jdk8.config.graphql.resolver.CustomExceptionResolver;
import com.coral.test.spring.graphql_jdk8.config.graphql.scalar.ScalarLocalDateTime;
import com.coral.test.spring.graphql_jdk8.config.graphql.schema.PageSchema;
import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlInterceptor;

/**
 * GraphQl配置
 *
 * @author huss
 * @date 2023/12/28 16:12
 * @packageName com.coral.test.spring.graphql.config
 * @className GraphQlConfig
 */
@Configuration
public class GraphQlConfig {

    @Bean
    public WebGraphQlInterceptor graphQlRequestInterceptor() {
        return new GraphQlRequestInterceptor();
    }


//    @Bean
//    public GraphQlSourceBuilderCustomizer sourceBuilderCustomizer() {
//        // https://docs.spring.io/spring-graphql/docs/1.0.6/reference/html/#execution-graphqlsource
//        return builder -> {
//            builder.schemaFactory((typeDefinitionRegistry, runtimeWiring) -> {
//                PageSchema.parsePageDirective(typeDefinitionRegistry);
//                return new SchemaGenerator().makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
//            });
//        };
//    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        // https://github.com/graphql-java/graphql-java-extended-scalars
        return wiringBuilder -> {
            // 扩展数据类型
            wiringBuilder
                    .scalar(ExtendedScalars.DateTime)
                    .scalar(ExtendedScalars.Date)
                    .scalar(ExtendedScalars.Time)
                    .scalar(ExtendedScalars.LocalTime)
                    .scalar(ExtendedScalars.UUID)
                    .scalar(ExtendedScalars.GraphQLLong)
                    .scalar(ExtendedScalars.GraphQLShort)
                    .scalar(ExtendedScalars.Json)
                    .scalar(ScalarLocalDateTime.SCALAR_LOCAL_DATE_TIME);
            // 类型转换
            TypeRuntimeWiringFactory.autoCreate().forEach(wiringBuilder::type);
            // 指令
            /**
             * @see graphql.execution.ConditionalNodes#shouldInclude
             */
            wiringBuilder
                    // @auth(role:String)
                    .directive(AuthorisationDirectiveWiring.AUTH, AuthorisationDirectiveWiring.INSTANCE)
                    // @page
                    .directive(PageSchema.PAGE, PageDirectiveWiring.INSTANCE)
                    //
                    .directive(DateFormatDirectiveWiring.DATE_FORMAT, DateFormatDirectiveWiring.INSTANCE)

            ;

        };
    }

    @Bean
    public CustomExceptionResolver customExceptionResolver() {
        return new CustomExceptionResolver();
    }


}
