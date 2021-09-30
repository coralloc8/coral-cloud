package com.coral.base.common.convert;

import com.coral.base.common.BeanCopierUtil;

/**
 * @description: pojo数据转换
 * @author: huss
 * @time: 2020/7/14 16:34
 */
public interface PojoConvert<T, R extends PojoConvert> extends IConvert<T, R> {

    /**
     * 默认方法 属性的数据类型必须要一模一样值才会被设置进去
     *
     * @param t 原始数据
     * @return
     */
    @Override
    default R convert(T t) {
        return (R) BeanCopierUtil.copy(t, this);
    }
}
