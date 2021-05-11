package com.coral.base.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.cglib.beans.BeanCopier;

/**
 * @description: 对象复制
 * @author: huss
 * @time: 2020/7/15 17:16
 */
public class BeanCopierUtil {

    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * BeanCopier的copy
     * 
     * @param source
     *            源文件的
     * @param target
     *            目标文件
     */
    public static <T, R> R copy(T source, R target) {
        final String key = genKey(source, target);
        BeanCopier beanCopier =
            BEAN_COPIER_CACHE.computeIfAbsent(key, k -> BeanCopier.create(source.getClass(), target.getClass(), false));
        beanCopier.copy(source, target, null);
        return target;
    }

    private static String genKey(Object source, Object target) {
        return source.getClass() + "_" + target.getClass();
    }

}
