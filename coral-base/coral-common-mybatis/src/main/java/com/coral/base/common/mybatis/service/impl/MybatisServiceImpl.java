package com.coral.base.common.mybatis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.base.common.mybatis.service.IMybatisService;

import java.io.Serializable;

/**
 * @author huss
 * @version 1.0
 * @className MybatisServiceImpl
 * @description mybatis service 基础实现
 * @date 2021/5/11 17:33
 */
public class MybatisServiceImpl<M extends MybatisMapper<T>, T extends Serializable> extends ServiceImpl<M, T> implements IMybatisService<T> {
}
