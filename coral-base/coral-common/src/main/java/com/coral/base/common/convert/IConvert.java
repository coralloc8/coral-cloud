package com.coral.base.common.convert;

import com.coral.base.common.BeanCopierUtil;

/**
 * @description: 数据转换
 * @author: huss
 * @time: 2020/7/14 16:34
 */
public interface IConvert<T, R> {

    /**
     * 数据转换
     *
     * @param t 原始数据
     * @return R
     */
    R convert(T t);


    /**
     * 数据转换
     *
     * @param t
     * @param clazz
     * @return
     */
    default R convert(T t, Class<R> clazz) {
        return BeanCopierUtil.copy(t, clazz);
    }

}
