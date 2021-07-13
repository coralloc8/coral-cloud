package com.coral.database.test.mybatis.tertiary.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coral.base.common.StringUtils;
import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.database.test.mybatis.secondary.entity.SecTest;
import com.coral.database.test.mybatis.tertiary.entity.TerTest;

import java.util.List;
import java.util.Objects;

/**
 * @author huss
 */
public interface TerTestMapper extends MybatisMapper<TerTest> {
    /**
     * 查询所有
     *
     * @param name
     * @param age
     * @return
     */
    default List<TerTest> findAll(String name, Integer age) {
        Wrapper<TerTest> query = new QueryWrapper<TerTest>().lambda()
                .eq(StringUtils.isNotBlank(name), TerTest::getName, name)
                .eq(Objects.nonNull(age), TerTest::getAge, age);
        return selectList(query);
    }
}
