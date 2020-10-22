package com.example.spring.web.core.json;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.example.spring.common.EnumUtil;
import com.example.spring.common.StringUtils;
import com.example.spring.common.enums.IEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huss
 */
@Slf4j
public class EnumModule extends SimpleModule {

    public EnumModule() {
        super(PackageVersion.VERSION);
        super.addSerializer(IEnum.class, new EnumModule.EnumJsonSerializer());
        // 此处添加Enum序列化才有效
        super.addDeserializer(Enum.class, new EnumModule.EnumJsonDeserializer());

    }

    static class EnumJsonSerializer extends JsonSerializer<IEnum> {

        @Override
        public void serialize(IEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            log.info(">>>>>EnumJsonSerializer serialize value:{}", value);
            Map<String, Object> params = value.getParams();

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();

                gen.writeFieldName(key);
                if (val instanceof Number) {
                    gen.writeNumber(val.toString());
                } else {
                    gen.writeString(val.toString());
                }

            }

            gen.writeEndObject();
        }
    }

    static class EnumJsonDeserializer extends JsonDeserializer<Enum> {
        @Override
        public Enum deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {

            JsonNode node = p.getCodec().readTree(p);
            Object currentValue = p.getCurrentValue();

            String currentName = p.currentName();
            Class findPropertyType = null;
            if (currentValue instanceof Collection) {
                JsonStreamContext parsingContext = p.getParsingContext();

                JsonStreamContext parent = parsingContext.getParent();
                Object currentValue3 = parent.getCurrentValue();
                String currentName3 = parent.getCurrentName();
                try {
                    Field listField = currentValue3.getClass().getDeclaredField(currentName3);
                    ParameterizedType listGenericType = (ParameterizedType)listField.getGenericType();
                    Type listActualTypeArguments = listGenericType.getActualTypeArguments()[0];
                    findPropertyType = (Class)listActualTypeArguments;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
            }

            String asText = null;
            if (node.getNodeType().equals(JsonNodeType.OBJECT)) {
                JsonNode codeNode = node.get(IEnum.CODE_KEY);
                if (codeNode != null) {
                    asText = codeNode.asText();
                }
            } else {
                asText = node.asText();
            }

            if (StringUtils.isBlank(asText)) {
                return null;
            }
            log.info(">>>>>EnumJsonDeserializer deserialize asText:{}, findPropertyType:{}", asText, findPropertyType);
            if (findPropertyType.isEnum() && IEnum.class.isAssignableFrom(findPropertyType)) {
                return (Enum)EnumUtil.codeOf(findPropertyType, asText);
            }
            throw new IllegalArgumentException("value is not enum");

        }
    }
}
