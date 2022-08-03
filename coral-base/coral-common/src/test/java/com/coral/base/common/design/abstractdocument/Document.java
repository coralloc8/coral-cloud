package com.coral.base.common.design.abstractdocument;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author huss
 * @version 1.0
 * @className Document
 * @description 文档
 * @date 2022/7/28 14:24
 */
public interface Document<T> {

    void put(String key, T value);

    T get(String key);

    Stream<T> children(String key, Function<Map<String, T>, T> constructor);
}
