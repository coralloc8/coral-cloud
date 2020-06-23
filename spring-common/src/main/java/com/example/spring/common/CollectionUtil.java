package com.example.spring.common;

import java.util.*;

/**
 * @author huss
 */
public class CollectionUtil {

    public static <T> List<T> convert(List<T> list) {
        return list == null || list.isEmpty() ? Collections.emptyList() : list;
    }

    public static boolean isBlank(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> Set<T> newHashSet(T... args) {
        return new HashSet<>(Arrays.asList(args));
    }

    public static <T> Set<T> newHashSet(Collection<T> collection) {
        return new HashSet<>(collection);
    }
}
