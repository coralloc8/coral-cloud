package com.coral.base.common;

import com.coral.base.common.enums.IEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className EnumFactory
 * @description 枚举
 * @date 2021/3/22 14:49
 */
@Slf4j
public final class EnumFactory {

    /**
     * 获取枚举值
     *
     * @param code
     * @return
     */
    public static <K extends Serializable, V extends IEnum<? extends Enum, K>> Optional<V> codeOf(K code, Class<V> clazz) {
        if (Objects.isNull(code) || StringUtils.isBlank(code.toString())) {
            return Optional.empty();
        }
        return Arrays.asList(clazz.getEnumConstants()).stream()
                .filter(e -> e.getCode().toString().equals(code.toString())).findFirst();
    }

    /**
     * 获取枚举值
     *
     * @param name
     * @return
     */
    public static <V extends IEnum> Optional<V> nameOf(String name, Class<V> clazz) {
        if (StringUtils.isBlank(name)) {
            return Optional.empty();
        }
        return Arrays.asList(clazz.getEnumConstants()).stream().filter(e -> e.getName().equals(name)).findFirst();
    }
}
