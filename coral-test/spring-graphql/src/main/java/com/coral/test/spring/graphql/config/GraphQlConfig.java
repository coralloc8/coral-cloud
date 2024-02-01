package com.coral.test.spring.graphql.config;

import com.coral.test.spring.graphql.config.graphql.ScalarLocalDateTime;
import com.coral.test.spring.graphql.config.graphql.TypeRuntimeWiringFactory;
import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

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
            // 类型转换 (先手动一个个枚举类注册)
//            wiringBuilder.type(TypeRuntimeWiringFactory.create(Sex.class));
            TypeRuntimeWiringFactory.autoCreate().forEach(wiringBuilder::type);

            //
        };
    }


}
