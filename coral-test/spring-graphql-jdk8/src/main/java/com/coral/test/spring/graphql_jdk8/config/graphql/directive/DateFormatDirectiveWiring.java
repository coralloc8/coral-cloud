package com.coral.test.spring.graphql_jdk8.config.graphql.directive;

import com.coral.base.common.StringUtils;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * 时间格式化
 *
 * @author huss
 * @date 2024/1/3 15:02
 * @packageName com.coral.test.spring.graphql.config.graphql
 * @className DateFormatDirectiveWiring
 */
@Slf4j
public class DateFormatDirectiveWiring implements DefSchemaDirectiveWiring {

    public final static String DATE_FORMAT = "dateFormat";
    public final static String PATTERN = "pattern";
    public final static SchemaDirectiveWiring INSTANCE = new DateFormatDirectiveWiring();

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition field = environment.getElement();
        String pattern = getArgByFieldDefinition(field, DATE_FORMAT, PATTERN);
        DataFetcher<?> dateFormatFetcher = DataFetcherFactories.wrapDataFetcher(environment.getFieldDataFetcher(), (dataFetchingEnvironment, value) -> {
            String currPattern = pattern;
            Optional<Value> stv = getArgByField(dataFetchingEnvironment, DATE_FORMAT, PATTERN);
            if (stv.isPresent() && stv.get() instanceof StringValue) {
                currPattern = ((StringValue) stv.get()).getValue();
            }
            log.info(">>>>>[@{}] pattern:{}", DATE_FORMAT, currPattern);
            if (StringUtils.isBlank(currPattern)) {
                return value;
            }
            try {
                return DateTimeFormatter.ofPattern(currPattern).format((LocalDateTime) value);
            } catch (Exception e) {
                log.error("##### format date error!", e);
            }
            return value;
        });
        environment.setFieldDataFetcher(dateFormatFetcher);

        return field;
    }

}
