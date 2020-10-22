package com.example.spring.common.convert;

/**
 * @description: 数据转换
 * @author: huss
 * @time: 2020/7/14 16:34
 */
public interface IConvert<T, R> {

    /**
     * 数据转换
     * 
     * @param t
     *            原始数据
     * @return R
     */
    R convert(T t);

}
