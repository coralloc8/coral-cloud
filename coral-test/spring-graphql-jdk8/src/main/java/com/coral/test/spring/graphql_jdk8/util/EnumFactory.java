package com.coral.test.spring.graphql_jdk8.util;

import com.coral.base.common.StringUtils;
import com.coral.test.spring.graphql_jdk8.eums.BaseEnum;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 枚举工厂
 *
 * @author huss
 * @date 2023/12/29 14:55
 * @packageName com.coral.test.spring.graphql.util
 * @className EnumFactory
 */
public class EnumFactory {

    /**
     * 获取enum描述
     *
     * @param clazz
     * @return
     */
    public static String description(Class<? extends BaseEnum<?>> clazz) {
        List<String> remarks = Stream.of(clazz.getEnumConstants()).map(e -> {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isNotBlank(e.getName())) {
                sb.append(e.getName()).append(":");
            }
            if (Objects.nonNull(e.getCode())) {
                sb.append(e.getCode());
            }
            return sb.toString();
        }).collect(Collectors.toList());
        return String.join("    ", remarks);
    }

    /**
     * 获取枚举信息
     *
     * @param clazz
     * @param <R>
     * @param <T>
     * @return
     */
    public static <R, T extends BaseEnum<R>> List<Map<String, Object>> infos(@NonNull Class<T> clazz) {
        boolean isEnum = clazz.isEnum() && BaseEnum.class.isAssignableFrom(clazz);
        if (!isEnum) {
            return Collections.emptyList();
        }
        return Arrays.stream(clazz.getEnumConstants()).map(BaseEnum::info).collect(Collectors.toList());
    }

    /**
     * 根据 code/name/className 查询枚举值
     *
     * @param val
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends BaseEnum<?>> Optional<T> findBy(String val, @NonNull Class<T> clazz) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.isEqual(val)).findFirst();
    }
}
