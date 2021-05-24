package com.coral.base.common.mybatis.dynamic;

import com.coral.base.common.enums.IEnum;

import java.io.Serializable;

/**
 * @author huss
 * @version 1.0
 * @className DbType
 * @description 数据源类型
 * @date 2021/5/18 10:20
 */
public interface DbType<T extends Enum<T>, R extends Serializable> extends IEnum<T, R> {


}
