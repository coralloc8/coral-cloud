package com.coral.test.spring.graphql.eums;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举
 *
 * @author huss
 * @date 2023/12/29 14:45
 * @packageName com.coral.test.spring.graphql.eums
 * @className GraphqlEnum
 */
public interface BaseEnum<T> {

    T getCode();

    String getName();


    default boolean isShow() {
        return true;
    }

    default String getDesc() {
        return "";
    }

    default Map<String, Object> info() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", getCode());
        map.put("name", getName());
        map.put("isShow", isShow());
        map.put("desc", getDesc());
        return map;
    }

    default boolean isEqual(String val) {
        return !val.isBlank() && (val.equals(getCode().toString()) || val.equals(getName()) || val.equals(this.toString()));
    }

}
