package com.coral.simple.web2.service.impl;


import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.coral.base.common.mybatis.service.impl.MybatisServiceImpl;
import com.coral.database.test.mybatis.config.DbType;
import com.coral.database.test.mybatis.primary.entity.Test;
import com.coral.database.test.mybatis.primary.mapper.TestMapper;
import com.coral.database.test.mybatis.secondary.entity.SecTest;
import com.coral.database.test.mybatis.secondary.mapper.SecTestMapper;
import com.coral.simple.web2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className TestServiceImpl
 * @description test
 * @date 2021/5/13 10:35
 */
@Service
public class TestServiceImpl extends MybatisServiceImpl<TestMapper, Test> implements TestService {

    /**
     * 测试查看源码用
     */
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Autowired
    private SecTestMapper secTestMapper;


    @Override
    public Map<String, Object> findAll3(String name, Integer age) {
        Map<String, Object> map = new HashMap<>(4);
        map.put(DbType.PRIMARY, findAll(name, age));
        map.put(DbType.SECONDARY, findAll2(name, age));
        return map;
    }

    @Override
    public List<SecTest> findAll2(String name, Integer age) {
        return secTestMapper.findAll(name, age);
    }

    @Override
    public List<Test> findAll(String name, Integer age) {
        return getBaseMapper().findAll(name, age);
    }

}
