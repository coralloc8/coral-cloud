package com.coral.simple.web2.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coral.base.common.exception.BaseErrorMessageEnum;
import com.coral.base.common.exception.SystemRuntimeException;
import com.coral.base.common.mybatis.service.impl.MybatisServiceImpl;
import com.coral.database.test.mybatis.config.datasource.DbTypeEnum;
import com.coral.database.test.mybatis.primary.entity.Test;
import com.coral.database.test.mybatis.primary.mapper.TestMapper;
import com.coral.database.test.mybatis.secondary.entity.SecTest;
import com.coral.database.test.mybatis.secondary.mapper.SecTestMapper;
import com.coral.database.test.mybatis.tertiary.entity.TerTest;
import com.coral.database.test.mybatis.tertiary.mapper.TerTestMapper;
import com.coral.simple.web2.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private SecTestMapper secTestMapper;


    @Autowired
    private TerTestMapper terTestMapper;


    @Override
    public Map<String, Object> findAll4(String name, Integer age) {
        Map<String, Object> map = new HashMap<>(4);
        map.put(DbTypeEnum.PRIMARY.getCode(), findAll(name, age));
        map.put(DbTypeEnum.TERTIARY.getCode(), findAll3(name, age));
        map.put(DbTypeEnum.SECONDARY.getCode(), findAll2(name, age));
        return map;
    }


    @Override
    public List<TerTest> findAll3(String name, Integer age) {
        return terTestMapper.findAll(name, age);
    }


    @Override
    public IPage<SecTest> findAll2(String name, Integer age) {
        return secTestMapper.page(5, 1, name, age);
    }

    @Override
    public List<Test> findAll(String name, Integer age) {
        return testMapper.findAll(name, age);
    }

    @Override
    public void save(String name, Integer age) {
        Test test = new Test();
        test.setName(name);
        test.setAge(age);
        test.setMoney(crateMoney());
        test.setCreateTime(LocalDateTime.now());
        save(test);
    }

    @Override
    public void save2(String name, Integer age) {
        SecTest test = new SecTest();
        test.setName(name);
        test.setAge(age);
        test.setMoney(crateMoney());
        test.setCreateTime(LocalDateTime.now());
        secTestMapper.insert(test);
    }

    @Override
    public void save3(String name, Integer age) {
        TerTest test = new TerTest();
        test.setName(name);
        test.setAge(age);
        test.setMoney(crateMoney());
        test.setCreateTime(LocalDateTime.now());
        terTestMapper.insert(test);
    }


    @Override
    public void save4(String name, Integer age) {
        save2(name, age);
        save(name, age);
        save3(name, age);

        if (age < 10) {
            throw new SystemRuntimeException(BaseErrorMessageEnum.ILLEGAL_PARAMETER);
        }
    }

    private Double crateMoney() {
        double money = Math.random() * 100000000;
        BigDecimal bg = new BigDecimal(money);
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

}
