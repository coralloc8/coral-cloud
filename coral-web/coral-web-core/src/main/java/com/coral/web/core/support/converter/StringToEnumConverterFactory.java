package com.coral.web.core.support.converter;

import com.coral.base.common.EnumUtil;
import com.coral.base.common.enums.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.Assert;

/**
 * 替换spring默认的字符串转enum 也可使用{@link StringToEnumGenericConverter} 进行转换 二选一
 *
 * @author huss
 */
@Slf4j
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverterFactory.StringToEnum(this.getEnumType(targetType));
    }

    private static class StringToEnum<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            log.info(">>>>>convert start. source:{}", source);
            if (source.isEmpty()) {
                // It's an empty enum identifier: reset the enum value to null.
                return null;
            }
            T t;
            if (IEnum.class.isAssignableFrom(this.enumType)) {
                Class clazz = this.enumType;
                try {
                    t = (T) EnumUtil.codeOf(clazz, source);
                    return t;
                } catch (Exception e) {
                    log.info(">>>>>convert codeOf error:", e);
                    log.info(">>>>>try convert nameOf start...");
                    try {
                        t = (T) EnumUtil.nameOf(clazz, source);
                    } catch (Exception e1) {
                        log.info(">>>>>convert nameOf error:", e1);
                        log.info(">>>>>try convert classNameOf start...");
                        t = (T) EnumUtil.classNameOf(clazz, source);
                    }
                    return t;
                }

            }

            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }

    private Class<?> getEnumType(Class<?> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        Assert.notNull(enumType, () -> "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }

}
