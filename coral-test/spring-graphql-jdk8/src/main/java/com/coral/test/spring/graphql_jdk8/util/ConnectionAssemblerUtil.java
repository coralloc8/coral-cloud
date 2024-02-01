package com.coral.test.spring.graphql_jdk8.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import graphql.relay.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * ConnectionAssemblerUtil
 *
 * @author huss
 * @date 2024/1/2 18:53
 * @packageName com.coral.test.spring.graphql.util
 * @className ss
 */
public class ConnectionAssemblerUtil {

    public static <T> Connection<T> convert(IPage<T> page) {
        AtomicInteger index = new AtomicInteger();
        List<Edge<T>> defaultEdges = Stream.of(page.getRecords())
                .flatMap(Collection::stream)
                .map(e -> (Edge<T>) new DefaultEdge<T>(e, new DefaultConnectionCursor(String.valueOf(index.incrementAndGet()))))
                .collect(Collectors.toList());
        return new DefaultConnection<>(defaultEdges,
                new DefaultPageInfo(new DefaultConnectionCursor(String.valueOf(page.getCurrent())),
                        new DefaultConnectionCursor(String.valueOf(page.getPages())),
                        page.getCurrent() > 1,
                        page.getCurrent() < page.getPages()));
    }
}
