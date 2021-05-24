package com.coral.database.test.mybatis.secondary.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coral.base.common.StringUtils;
import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.database.test.mybatis.secondary.entity.SecTest;

import java.util.List;
import java.util.Objects;

/**
 * @author huss
 */
public interface SecTestMapper extends MybatisMapper<SecTest> {
    /**
     * 查询所有
     *
     * @param name
     * @param age
     * @return
     */
    default List<SecTest> findAll(String name, Integer age) {
        Wrapper<SecTest> query = new QueryWrapper<SecTest>().lambda()
                .eq(StringUtils.isNotBlank(name), SecTest::getName, name)
                .eq(Objects.nonNull(age), SecTest::getAge, age);
        return selectList(query);
    }
}
