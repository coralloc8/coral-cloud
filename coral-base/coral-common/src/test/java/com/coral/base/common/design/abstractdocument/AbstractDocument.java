package com.coral.base.common.design.abstractdocument;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author huss
 * @version 1.0
 * @className AbstractDocument
 * @description 抽象文档
 * @date 2022/7/28 14:27
 */
public class AbstractDocument<T> implements Document<T> {
    @Getter
    private final Map<String, T> properties;

    public AbstractDocument(Map<String, T> properties) {
        Objects.requireNonNull(properties, "properties map is required");
        this.properties = properties;
    }

    @Override
    public void put(String key, T value) {
        this.getProperties().put(key, value);
    }

    @Override
    public T get(String key) {
        return this.getProperties().get(key);
    }

    @Override
    public Stream<T> children(String key, Function<Map<String, T>, T> constructor) {
        T t = getProperties().get(key);
        return Stream.of(getProperties().get(key))
                .filter(Objects::nonNull)
                ;
    }

}
