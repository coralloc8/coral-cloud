package com.coral.web.core.support.converter;

import com.coral.base.common.EnumUtil;
import com.coral.base.common.StringUtils;
import com.coral.base.common.enums.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author huss
 * @version 1.0
 * @className StringToEnumGenericConverter
 * @description 字符串转换为枚举 也可使用{@link StringToEnumConverterFactory} 进行转换 二选一
 * @date 2023/2/2 17:11
 */
@Slf4j
public class StringToEnumGenericConverter implements GenericConverter {
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return new HashSet<>(Arrays.asList(new ConvertiblePair(String.class, Enum.class)));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        log.info(">>>>>[StringToEnumGenericConverter] start. source:{}", source);
        if (Objects.isNull(source)) {
            // It's an empty enum identifier: reset the enum value to null.
            return null;
        }
        String sourceStr = source.toString().trim();
        if (StringUtils.isBlank(sourceStr)) {
            return null;
        }
        Class clazz = targetType.getType();
        Object t;
        if (IEnum.class.isAssignableFrom(clazz)) {
            try {
                t = EnumUtil.codeOf(clazz, sourceStr);
                return t;
            } catch (Exception e) {
                log.info(">>>>>[StringToEnumGenericConverter] codeOf error:", e);
                log.info(">>>>>[StringToEnumGenericConverter] try convert nameOf start...");
                try {
                    t = EnumUtil.nameOf(clazz, sourceStr);
                } catch (Exception e1) {
                    log.info(">>>>>[StringToEnumGenericConverter] nameOf error:", e1);
                    log.info(">>>>>[StringToEnumGenericConverter] try convert classNameOf start...");
                    t = EnumUtil.classNameOf(clazz, sourceStr);
                }
                return t;
            }

        }
        return Enum.valueOf(clazz, sourceStr);
    }
}