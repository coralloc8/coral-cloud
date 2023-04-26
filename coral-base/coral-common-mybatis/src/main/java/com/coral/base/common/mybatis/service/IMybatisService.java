package com.coral.base.common.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;

/**
 * @author huss
 * @version 1.0
 * @className IMybatisService
 * @description mybatis base service
 * @date 2021/5/11 17:31
 */
public interface IMybatisService<T extends Serializable> extends IService<T> {
    @Override
    default boolean removeById(Serializable id) {
        return IService.super.removeById(id);
    }

}
