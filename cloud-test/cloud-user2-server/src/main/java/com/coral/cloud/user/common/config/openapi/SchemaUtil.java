package com.coral.cloud.user.common.config.openapi;

import com.coral.base.common.BeanCopierUtil;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.oas.models.media.Schema;

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
