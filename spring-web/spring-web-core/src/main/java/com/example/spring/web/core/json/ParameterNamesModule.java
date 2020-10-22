package com.example.spring.web.core.json;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * double 默认保留两位小数，四舍五入
 * 
 * @author huss
 */
public class ParameterNamesModule extends SimpleModule {

    public ParameterNamesModule() {
        super(PackageVersion.VERSION);
        super.addSerializer(Double.class, new DoubleJsonSerializer());
        super.addSerializer(double.class, new DoubleJsonSerializer());
    }

    static class DoubleJsonSerializer extends JsonSerializer<Double> {
        @Override
        public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            BigDecimal bigDecimal = BigDecimal.valueOf(value);
            bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
            gen.writeObject(bigDecimal.toPlainString());
        }
    }
}
