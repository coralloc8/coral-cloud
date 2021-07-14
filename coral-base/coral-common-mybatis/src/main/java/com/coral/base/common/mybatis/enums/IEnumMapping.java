package com.coral.base.common.mybatis.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

import java.io.Serializable;

/**
 * @author huss
 * @version 1.0
 * @className IEnumMapping
 * @description 枚举映射
 * @date 2021/5/17 14:12
 */
public interface IEnumMapping<T extends Serializable> extends IEnum<T> {
}
