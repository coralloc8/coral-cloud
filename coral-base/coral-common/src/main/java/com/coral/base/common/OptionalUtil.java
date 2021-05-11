package com.coral.base.common;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * @author huss
 * @version 1.0
 * @className OptionalUtil
 * @description Optional转换
 * @date 2021/4/22 16:50
 */
public class OptionalUtil {


    public static <T> Optional<T> of(T t) {
        return Objects.isNull(t) ? Optional.empty() : Optional.of(t);

    }

    /**
     * @param flag false:返回empty true:继续判断 t
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Optional<T> of(boolean flag, T t) {
        return flag ? of(t) : Optional.empty();
    }

    public static <T> Optional<T> of() {
        return Optional.empty();
    }

    public static <T> Optional<T> of(Collection<T> list) {
        return Objects.isNull(list) || list.isEmpty() ? Optional.empty() : Optional.of(list.iterator().next());
    }


}
