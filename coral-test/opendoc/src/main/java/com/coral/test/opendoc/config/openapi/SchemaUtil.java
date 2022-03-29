package com.coral.test.opendoc.config.openapi;

import com.coral.base.common.BeanCopierUtil;
import com.coral.base.common.EnumUtil;
import com.coral.base.common.enums.IEnum;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.Schema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author huss
 * @version 1.0
 * @className SchemaUtil
 * @description todo
 * @date 2021/9/14 19:30
 */
public class SchemaUtil {

    public static Schema copySchema(Schema oldSchema, Schema newSchema) {
        String type = newSchema.getType();
        String format = newSchema.getFormat();
        BeanCopierUtil.copy(oldSchema, newSchema);

        newSchema.type(type)
                .format(format)
        ;

        return newSchema;
    }

    public static Schema getSchema(Class className, String schemaName, String description) {
        ResolvedSchema resolvedSchema = ModelConverters.getInstance()
                .resolveAsResolvedSchema(
                        new AnnotatedType(className).resolveAsRef(false));
        return resolvedSchema.schema.description(description).name(schemaName);
    }

}
