package com.coral.base.common.convert;

import com.coral.base.common.BeanCopierUtil;
import com.coral.base.common.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className PojoConvertUtil
 * @description pojo数据转换
 * @date 2022/3/4 11:30
 */
@Slf4j
public class PojoConvertUtil {

    /**
     * dto转换为domain  转化层要在dto中实现
     *
     * @param t
     * @param clazz
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R dtoToDomain(T t, Class<R> clazz) {
        if (Objects.isNull(t)) {
            return null;
        }
        if (PojoConvert.class.isAssignableFrom(t.getClass())) {
            try {
                PojoConvert po = (PojoConvert) t;
                return (R) po.convert(t, clazz);
            } catch (Exception e) {
                log.error(">>>>>Error", e);
            }
        }
        return BeanCopierUtil.copy(t, clazz);
    }

    /**
     * dto转换为domain  转化层要在dto中实现
     *
     * @param list
     * @param clazz
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> batchDtoToDomain(Collection<T> list, Class<R> clazz) {
        if (CollectionUtil.isBlank(list)) {
            return Collections.emptyList();
        }
        return list.stream().map(e -> dtoToDomain(e, clazz)).collect(Collectors.toList());
    }

    /**
     * 数据转换 转化层在clazz层实现
     *
     * @param t
     * @param <R>
     * @return
     */
    public static <T, R> R convert(T t, Class<R> clazz) {
        if (Objects.isNull(t)) {
            return null;
        }
        if (PojoConvert.class.isAssignableFrom(clazz)) {
            try {
                PojoConvert po = (PojoConvert) clazz.newInstance();
                return (R) po.convert(t, clazz);
            } catch (Exception e) {
                log.error(">>>>>Error", e);
            }
        }
        return BeanCopierUtil.copy(t, clazz);
    }

    /**
     * 数据批量转换  转化层在clazz层实现
     * 目标类如果自身实现了PojoConvert接口的话，优先使用该接口进行数据转换
     *
     * @param list
     * @return
     */
    public static <T, R> List<R> batchConvert(Collection<T> list, Class<R> clazz) {
        if (CollectionUtil.isBlank(list)) {
            return Collections.emptyList();
        }
        Function<T, R> mapper = p -> convert(p, clazz);
        if (PojoConvert.class.isAssignableFrom(clazz)) {
            try {
                PojoConvert po = (PojoConvert) clazz.newInstance();
                mapper = p -> (R) po.convert(p, clazz);
            } catch (Exception e) {
                log.error(">>>>>Error", e);
            }
        }
        return list.stream().map(mapper).collect(Collectors.toList());
    }

}
