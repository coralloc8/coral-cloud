package com.coral.database.test.mybatis.primary.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coral.base.common.StringUtils;
import com.coral.base.common.mybatis.mapper.MybatisMapper;
import com.coral.database.test.mybatis.primary.entity.Test;

import java.util.List;
import java.util.Objects;

/**
 * @author huss
 */
public interface TestMapper extends MybatisMapper<Test> {
    /**
     * 查询所有
     *
     * @param name
     * @param age
     * @return
     */
    default List<Test> findAll(String name, Integer age) {
        Wrapper<Test> query = new QueryWrapper<Test>().lambda()
                .eq(StringUtils.isNotBlank(name), Test::getName, name)
                .eq(Objects.nonNull(age), Test::getAge, age);
        return selectList(query);

    }
}
