package com.coral.test.opendoc.config.openapi;

import com.coral.base.common.EnumUtil;
import com.coral.base.common.enums.IEnum;
import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.stereotype.Component;

import java.time.temporal.TemporalAccessor;

/**
 * @author huss
 * @version 1.0
 * @className MyPropertyCustomizer
 * @description 自定义schema
 * @date 2021/9/13 10:58
 */
@Component
@Slf4j
public class MyPropertyCustomizer implements PropertyCustomizer {

    /**
     * 参考 {@link org.springdoc.core.converters.ResponseSupportConverter}
     *
     * @param property
     * @param type
     * @return
     */
    @Override
    public Schema customize(Schema property, AnnotatedType type) {
        JavaType javaType = Json.mapper().constructType(type.getType());
        property = buildTimeSchema(property, type, javaType);
        property = buildEnumSchema(property, type, javaType);
        return property;
    }
    /**
     * 时间转化
     *
     * @param property
     * @param type
     * @return
     */
    private Schema buildTimeSchema(Schema property, AnnotatedType type, JavaType javaType) {
        boolean isTemporalAccessor = TemporalAccessor.class.isAssignableFrom(javaType.getRawClass());
        if (!isTemporalAccessor) {
            return property;
        }
        log.info(">>>>>time重写...type:{}", type.getType().getTypeName());
        Schema newSchema = new DateTimeSchema();
        SchemaUtil.copySchema(property, newSchema);
        return newSchema;
    }



    /**
     * 创建枚举schema
     *
     * @param property
     * @param type
     * @return
     */
    private Schema buildEnumSchema(Schema property, AnnotatedType type,JavaType javaType) {
        boolean isIEnum = IEnum.class.isAssignableFrom(javaType.getRawClass());
        if (!isIEnum) {
            return property;
        }
        log.info(">>>>>enum重写...type:{}", type.getType().getTypeName());
        String desc = EnumUtil.description((Class<? extends IEnum>) javaType.getRawClass());

        //返参碰到枚举类型的重写数据结构
        property = SchemaUtil.getSchema(EnumSchema.class, javaType.getRawClass().getSimpleName(), property.getDescription() + "  " + desc);

        return property;
    }

}
